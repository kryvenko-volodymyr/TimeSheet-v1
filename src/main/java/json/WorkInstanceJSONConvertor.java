package json;

import Domain.WorkInstance;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import java.util.List;

public interface WorkInstanceJSONConvertor {
    void setServletContext(ServletContext servletContext);

    JSONObject toJSON(WorkInstance workInstance);
    WorkInstance fromJSON(String json);

    JSONArray toJSON(List<WorkInstance> workInstances);
}
