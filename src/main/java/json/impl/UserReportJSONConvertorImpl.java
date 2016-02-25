package json.impl;

import Domain.ReportRecord;
import Domain.UserReport;
import Domain.WorkInstance;
import json.UserReportJSONConvertor;
import json.UserReportRecordsLineJSONConvertor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ua.pravex.objectPools.WorkInstancePool;
import ua.pravex.util.DatesProcessor;

import javax.servlet.ServletContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserReportJSONConvertorImpl implements UserReportJSONConvertor {
    private ServletContext servletContext;

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public JSONObject toJSON(UserReport userReport) {
        UserReportRecordsLineJSONConvertor userReportRecordsLineJSONConvertor = (UserReportRecordsLineJSONConvertor)servletContext
                .getAttribute("userReportRecordsLineJSONConvertor");

        JSONObject userReportJSON = new JSONObject();

        userReportJSON.put("date_from", userReport.DATE_FROM.getTime());
        userReportJSON.put("date_to", userReport.DATE_TO.getTime());

        Map userReportRow = userReport.getUserReportRows();
        for (Object object : userReportRow.keySet()) {
            WorkInstance workInstance = (WorkInstance) object;
            JSONObject userReportRecordsLineJSON = userReportRecordsLineJSONConvertor.toJSON(userReport, workInstance);
            userReportJSON.put("wi" + workInstance.getId(),userReportRecordsLineJSON);
        }
        return userReportJSON;
    }

    @Override
    public UserReport fromJSON(String json) {
        WorkInstancePool workInstancePool = (WorkInstancePool) servletContext.getAttribute("workInstancePool");
        DatesProcessor datesProcessor = DatesProcessor.getInstance();

        JSONParser parser = new JSONParser();
        Date dateFrom = null;
        Date dateTo = null;
        Set<ReportRecord> reportRecords = new HashSet<>();


        // todo: the below shit-code block should be distributed among corresponding JSONConvertors
        try {
            JSONObject userReportJSON = ((JSONObject)parser.parse(json));
            Set<Map.Entry> reportJSONEntries = userReportJSON.entrySet();

            for (Map.Entry<String, Object> reportJSONEntry : reportJSONEntries){
                if (reportJSONEntry.getKey().startsWith("wi")){

                    JSONObject userReportRowJSON = (JSONObject) reportJSONEntry.getValue();
                    Set<Map.Entry> rowJSONEntries = userReportRowJSON.entrySet();

                    for (Map.Entry<String, JSONObject> rowJSONEntry : rowJSONEntries) {
                        if (rowJSONEntry.getKey().startsWith("rr")) {

                            JSONObject reportRecordJSON = rowJSONEntry.getValue();
                            ReportRecord reportRecord = new ReportRecord();

                            // shit-code below. TODO: understand why frontend returns different formats
                            Date dateReported = null;
                            try {
                                long dateReportedJSON = (long)reportRecordJSON.get("date_reported");
                                dateReported = new Date(dateReportedJSON);
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                                String dateReportedJSON = (String)reportRecordJSON.get("date_reported");
                                dateReported  = datesProcessor.getLocalDate(dateReportedJSON);
                            }
                            reportRecord.setDateReported(dateReported);

                            reportRecord.setHoursNum((int)(long)reportRecordJSON.get("hours_num"));

                            int workInstanceReferredId = (int)(long)reportRecordJSON.get("work_instance_id");
                            WorkInstance workInstanceReferred = workInstancePool.get(workInstanceReferredId);
                            reportRecord.setWorkInstance(workInstanceReferred);

                            reportRecords.add(reportRecord);
                        }
                    }

                } else if (reportJSONEntry.getKey().equals("date_from")) {

                        String dateFromJSON = (String)reportJSONEntry.getValue();
                        dateFrom = datesProcessor.getLocalDate(dateFromJSON);

                } else if (reportJSONEntry.getKey().equals("date_to")) {

                        String dateToJSON = (String)reportJSONEntry.getValue();
                        dateTo = datesProcessor.getLocalDate(dateToJSON);

                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new UserReport(dateFrom, dateTo, reportRecords);
    }
}
