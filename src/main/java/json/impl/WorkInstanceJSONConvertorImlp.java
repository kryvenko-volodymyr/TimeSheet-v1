package json.impl;

import Domain.WorkInstance;
import Domain.WorkTitle;
import json.WorkInstanceJSONConvertor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import java.util.List;


public class WorkInstanceJSONConvertorImlp implements WorkInstanceJSONConvertor {
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public JSONObject toJSON(WorkInstance workInstance) {
        JSONObject workInstanceJSON = new JSONObject();
        workInstanceJSON.put("id", workInstance.getId());
        workInstanceJSON.put("work_type_title",  workInstance.getWorkTitle().getWorkType().getTitle());
        workInstanceJSON.put("work_title_title", workInstance.getWorkTitle().getTitle());
        workInstanceJSON.put("work_form_title", workInstance.getWorkForm().getTitle());
        workInstanceJSON.put("details", workInstance.getDetails());

        return workInstanceJSON;
    }

    @Override
    public WorkInstance fromJSON(String json) {
        return null;
    }

    @Override
    public JSONArray toJSON(List<WorkInstance> workInstances) {
        JSONArray workInstancesJSON = new JSONArray();
        for (WorkInstance workInstance : workInstances) {
            JSONObject workInstanceJSON = toJSON(workInstance);
            workInstancesJSON.add(workInstanceJSON);
        }
        return workInstancesJSON;
    }
}
