package json;

import Domain.WorkType;
import org.json.simple.JSONArray;

import javax.servlet.ServletContext;
import java.util.List;

public interface WorkTypeJSONConvertor {
    void setServletContext(ServletContext servletContext);

    JSONArray toJSON(List<WorkType> workTypes);
}
