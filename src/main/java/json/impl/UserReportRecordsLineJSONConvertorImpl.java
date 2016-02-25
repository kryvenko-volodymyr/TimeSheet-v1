package json.impl;

import Domain.ReportRecord;
import Domain.UserReport;
import Domain.WorkInstance;
import json.ReportRecordJSONConvertor;
import json.UserReportRecordsLineJSONConvertor;
import json.WorkInstanceJSONConvertor;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import java.util.*;

public class UserReportRecordsLineJSONConvertorImpl implements UserReportRecordsLineJSONConvertor {
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public JSONObject toJSON(UserReport userReport, WorkInstance workInstance) {
        ReportRecordJSONConvertor reportRecordJSONConvertor = (ReportRecordJSONConvertor)servletContext
                .getAttribute("reportRecordJSONConvertor");
        WorkInstanceJSONConvertor workInstanceJSONConvertor = (WorkInstanceJSONConvertor)servletContext
                .getAttribute("workInstanceJSONConvertor");

        Map<Date, ReportRecord> userReportRecordsLine = userReport.getUserReportRecordsLine(workInstance);

        JSONObject userReportRowJSON = new JSONObject();
        JSONObject workInstanceJSON = workInstanceJSONConvertor.toJSON(workInstance);
        userReportRowJSON.put("work_instance", workInstanceJSON);

        for (Date date : userReportRecordsLine.keySet()) {
            ReportRecord reportRecord = userReportRecordsLine.get(date);
            JSONObject reportRecordJSON =  reportRecordJSONConvertor.toJSON(reportRecord);
            userReportRowJSON.put("rr" + date.getTime(), reportRecordJSON);
        }

        return userReportRowJSON;
    }

    @Override
    public Object fromJSON(String json) {
        return null;
    }
}
