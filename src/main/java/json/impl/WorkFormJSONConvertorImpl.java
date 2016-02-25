package json.impl;

import Domain.WorkForm;
import json.WorkFormJSONConvertor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * Created by Kryvenko on 17.01.2016.
 */
public class WorkFormJSONConvertorImpl implements WorkFormJSONConvertor {
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {

        this.servletContext = servletContext;
    }

    @Override
    public JSONArray toJSON(List<WorkForm> workForms) {
        JSONArray workFormsJSON = new JSONArray();
        for (WorkForm workForm : workForms) {
            JSONObject workFormJSON = toJSON(workForm);
            workFormsJSON.add(workFormJSON);
        }
        return workFormsJSON;
    }

    @Override
    public JSONObject toJSON(WorkForm workForm) {
        JSONObject workFormJSON = new JSONObject();
        workFormJSON.put("id", workForm.getId());
        workFormJSON.put("title", workForm.getTitle());

        return workFormJSON;
    }
}
