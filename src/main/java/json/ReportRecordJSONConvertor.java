package json;

import Domain.ReportRecord;
import org.json.simple.JSONObject;

import javax.servlet.ServletContext;

public interface ReportRecordJSONConvertor {
    void setServletContext(ServletContext servletContext);

    JSONObject toJSON(ReportRecord reportRecord);
    ReportRecord fromJSON(String json);
}
