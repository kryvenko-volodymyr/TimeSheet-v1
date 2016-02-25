package json;

import Domain.UserReport;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;

public interface UserReportJSONConvertor {
    public void setServletContext(ServletContext servletContext);
    public JSONObject toJSON (UserReport userReport);
    public UserReport fromJSON (String json);
}
