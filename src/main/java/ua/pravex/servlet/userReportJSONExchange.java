package ua.pravex.servlet;

import Domain.Employee;
import Domain.UserReport;
import Service.ReportsExchangeService;
import json.UserReportJSONConvertor;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class userReportJSONExchange extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserReportJSONConvertor userReportJSONConvertor =
                (UserReportJSONConvertor) getServletContext().getAttribute("userReportJSONConvertor");
        ReportsExchangeService reportsExchangeService =
                (ReportsExchangeService) getServletContext().getAttribute("reportsExchangeService");
        Employee employee = (Employee)request.getSession().getAttribute("employee");
        String employeeAccont = employee.getAccount();

        String json = request.getParameter("user_report");
        UserReport userReport = userReportJSONConvertor.fromJSON(json);
        reportsExchangeService.saveReport(userReport, employeeAccont);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserReportJSONConvertor userReportJSONConvertor =
                (UserReportJSONConvertor) getServletContext().getAttribute("userReportJSONConvertor");
        ReportsExchangeService reportsExchangeService =
                (ReportsExchangeService) getServletContext().getAttribute("reportsExchangeService");

        Employee employee = (Employee)request.getSession().getAttribute("employee");

        UserReport userReport = reportsExchangeService.getUserReport(employee);
        JSONObject userReportJSON = userReportJSONConvertor.toJSON(userReport);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        userReportJSON.writeJSONString(out);
        out.flush();
    }
}
