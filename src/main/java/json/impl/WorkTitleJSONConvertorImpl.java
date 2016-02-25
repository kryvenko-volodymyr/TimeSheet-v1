package json.impl;

import Domain.WorkTitle;
import Domain.WorkType;
import json.WorkTitleJSONConvertor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import java.util.List;


public class WorkTitleJSONConvertorImpl implements WorkTitleJSONConvertor {
    private ServletContext servletContext;

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public JSONArray toJSON(List<WorkTitle> workTitles) {
        JSONArray workTitlesJSON = new JSONArray();
        for (WorkTitle workTitle : workTitles) {
            JSONObject workTitleJSON = toJSON(workTitle);
            workTitlesJSON.add(workTitleJSON);
        }
        return workTitlesJSON;

    }

    @Override
    public JSONObject toJSON (WorkTitle workTitle) {
        JSONObject workTitleJSON = new JSONObject();
        workTitleJSON.put("id", workTitle.getId());
        workTitleJSON.put("title", workTitle.getTitle());
        //TODO: other WorkTitle fields can be put here if necessary
        return workTitleJSON;
    }
}
