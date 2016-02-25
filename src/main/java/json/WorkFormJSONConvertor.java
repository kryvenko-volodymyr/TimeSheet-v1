package json;


import Domain.WorkForm;
import Domain.WorkTitle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import java.util.List;

public interface WorkFormJSONConvertor {
    void setServletContext(ServletContext servletContext);

    JSONArray toJSON(List<WorkForm> workForms);
    JSONObject toJSON (WorkForm workForm);
}
