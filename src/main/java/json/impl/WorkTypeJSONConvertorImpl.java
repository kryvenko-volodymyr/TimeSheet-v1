package json.impl;

import Domain.WorkType;
import json.WorkTypeJSONConvertor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import java.util.List;

public class WorkTypeJSONConvertorImpl implements WorkTypeJSONConvertor {
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public JSONArray toJSON(List<WorkType> workTypes) {
        JSONArray workTypesJSON = new JSONArray();
        for (WorkType workType : workTypes) {
            JSONObject workTypeJSON = toJSON(workType);
            workTypesJSON.add(workTypeJSON);
        }
        return workTypesJSON;

    }

    public JSONObject toJSON (WorkType workType) {
        JSONObject workTypeJSON = new JSONObject();
        workTypeJSON.put("id", workType.getId());
        workTypeJSON.put("title", workType.getTitle());
        return workTypeJSON;
    }
}
