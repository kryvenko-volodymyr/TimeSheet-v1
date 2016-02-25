package json;

import Domain.UserReport;
import Domain.WorkInstance;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;

public interface UserReportRecordsLineJSONConvertor  {
    void setServletContext(ServletContext servletContext);
    JSONObject toJSON(UserReport userReport, WorkInstance workInstance);
    Object fromJSON(String json);
}
