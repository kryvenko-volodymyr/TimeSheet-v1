package json;


import Domain.WorkTitle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import java.util.List;

public interface WorkTitleJSONConvertor {
    void setServletContext(ServletContext servletContext);

    JSONArray toJSON(List<WorkTitle> workTitles);

    public JSONObject toJSON (WorkTitle workTitle);
}
