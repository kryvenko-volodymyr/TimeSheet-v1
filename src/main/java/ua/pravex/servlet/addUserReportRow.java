package ua.pravex.servlet;

import Domain.*;
import Service.DomainObjectsManager;
import json.WorkFormJSONConvertor;
import json.WorkInstanceJSONConvertor;
import json.WorkTitleJSONConvertor;
import json.WorkTypeJSONConvertor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class addUserReportRow extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject workInstanceJSON = new JSONObject();
        String workTitleId = request.getParameter("work_title_id");
        String workFormId = request.getParameter("work_form_id");
        String workInstanceDetails = request.getParameter("details");
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        String employeeAccount = employee.getAccount();

        workInstanceJSON = createNewWorkInstanceJSON(workTitleId, workFormId, employeeAccount, workInstanceDetails);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        workInstanceJSON.writeJSONString(out);
        out.flush();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONArray JSONresponse = new JSONArray();
        String workTypeId;
        String workTitleId;
        String workFormId;

        switch (request.getServletPath()) {
            case "/work-types":
                JSONresponse = getWorkTypesJSON();
                break;
            case "/work-titles":
                workTypeId = request.getParameter("work-type-id");
                JSONresponse = getWorkTitlesJSON(workTypeId);
                break;
            case "/work-forms":
                workTypeId = request.getParameter("work-type-id");
                JSONresponse = getWorkFormsJSON(workTypeId);
                break;
            case "/work-instances":
                workTitleId = request.getParameter("work-title-id");
                workFormId = request.getParameter("work-form-id");
                JSONresponse = getWorkInstancesDetailsJSON(workTitleId, workFormId);
                break;
        }

        String servletPath = request.getServletPath();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONresponse.writeJSONString(out);
        out.flush();
    }

    public JSONArray getWorkTypesJSON() {
        DomainObjectsManager domainObjectsManager =
                (DomainObjectsManager) getServletContext().getAttribute("domainObjectsManager");
        WorkTypeJSONConvertor workTypeJSONConvertor =
                (WorkTypeJSONConvertor) getServletContext().getAttribute("workTypeJSONConvertor");

        List<WorkType> workTypes = domainObjectsManager.getWorkTypes();
        return workTypeJSONConvertor.toJSON(workTypes);
    }

    private JSONArray getWorkTitlesJSON(String workTypeId) {

        DomainObjectsManager domainObjectsManager =
                (DomainObjectsManager) getServletContext().getAttribute("domainObjectsManager");
        WorkTitleJSONConvertor workTitleJSONConvertor =
                (WorkTitleJSONConvertor) getServletContext().getAttribute("workTitleJSONConvertor");

        List<WorkTitle> workTitles = domainObjectsManager.getWorkTitles(workTypeId);
        return workTitleJSONConvertor.toJSON(workTitles);
    }

    private JSONArray getWorkFormsJSON(String workTypeId) {
        DomainObjectsManager domainObjectsManager =
                (DomainObjectsManager) getServletContext().getAttribute("domainObjectsManager");
        WorkFormJSONConvertor workFormJSONConvertor =
                (WorkFormJSONConvertor) getServletContext().getAttribute("workFormJSONConvertor");

        List<WorkForm> workForms = domainObjectsManager.getWorkForms(workTypeId);
        return workFormJSONConvertor.toJSON(workForms);
    }

    public JSONArray getWorkInstancesDetailsJSON(String workTitleId, String workFormId) {
        DomainObjectsManager domainObjectsManager =
                (DomainObjectsManager) getServletContext().getAttribute("domainObjectsManager");
        WorkInstanceJSONConvertor workInstanceJSONConvertor =
                (WorkInstanceJSONConvertor) getServletContext().getAttribute("workInstanceJSONConvertor");

        List<WorkInstance> workInstances = domainObjectsManager.getWorkInstances(workTitleId, workFormId);
        return workInstanceJSONConvertor.toJSON(workInstances);
    }

    private JSONObject createNewWorkInstanceJSON(String workTitleId, String workFormId, String employeeAccount, String workInstanceDetails) {
        DomainObjectsManager domainObjectsManager =
                (DomainObjectsManager) getServletContext().getAttribute("domainObjectsManager");
        WorkInstanceJSONConvertor workInstanceJSONConvertor =
                (WorkInstanceJSONConvertor) getServletContext().getAttribute("workInstanceJSONConvertor");

        WorkInstance workInstance = domainObjectsManager.createWorkInstance(workTitleId, workFormId, employeeAccount, workInstanceDetails);
        return workInstanceJSONConvertor.toJSON(workInstance); //TODO this one is ok
    }
}
