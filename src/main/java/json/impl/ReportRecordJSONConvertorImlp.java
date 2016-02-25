package json.impl;

import Domain.ReportRecord;
import json.ReportRecordJSONConvertor;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;

public class ReportRecordJSONConvertorImlp implements ReportRecordJSONConvertor {
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public JSONObject toJSON(ReportRecord reportRecord) {
        JSONObject reportRecordJSON = new JSONObject();
        reportRecordJSON.put("date_reported", reportRecord.getDateReported().getTime());
        reportRecordJSON.put("hours_num", reportRecord.getHoursNum());
        reportRecordJSON.put("work_instance_id", reportRecord.getWorkInstance().getId());

        return reportRecordJSON;
    }

    @Override
    public ReportRecord fromJSON(String json) {
        return null;
    }
}
