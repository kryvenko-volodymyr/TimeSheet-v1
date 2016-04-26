<%@ page import="Domain.Employee" %>
<%@ page import="Service.EmployeesExchangeService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>TimeSheet</title>
    <link rel="stylesheet" type="text/css" href="css\userReport.css">
    <script src="js\jquery-1.12.1.min.js"></script>
    <script src="js\jquery-migrate-1.4.0.min.js"></script>
    <script src="js\JSONProcessor.js"></script>
    <script src="js\userReportProcessor.js"></script>
    <script>
        var userReport;
        var tempList;

        $(document).ready(function () {
            $.getJSON("user-report", JSONToUserReport)
                    .done(function () {
                                var options = {/*weekday: 'long', */year: 'numeric', month: 'long', day: 'numeric'};
                                $("#user_report_timeframe").text(
                                        userReport.date_from.toLocaleDateString("uk-ua", options) + "  -  " +
                                        userReport.date_to.toLocaleDateString("uk-ua", options));
                                populateUserReportTalbe();
                            }
                    ).done(function () {
                prepareDataInput();
                prepareAddingUserReportRow();
            });
        });

        /*$(window).bind('beforeunload', function(e) {
            // Your code and validation
            if (confirm) {
                return "Are you sure?";
            }
        });*/
    </script>
</head>

<body>

<% EmployeesExchangeService employeesExchangeService =
        (EmployeesExchangeService) application.getAttribute("employeesExchangeService");

    String emplAccount;
    Employee employee;

    if (session.getAttribute("employee") == null) {
        emplAccount = request.getRemoteUser();
        employee = employeesExchangeService.getEmployeeByCode(emplAccount);
        session.setAttribute("employee", employee);
    }
%>

<H2><%= ((Employee) session.getAttribute("employee")).getName() %>
</H2>

<hr>

<h4 id="user_report_timeframe"></h4>

<button type="button" id="post_user_report" onclick="postUserReport()" disabled>Зберегти на сервері</button>
<button type="button" id="undo_unposted_changes" onclick="undoUnsavedChanges()" disabled>Скасувати щойно внесені зміни</button>

<hr>

<table id="user_report_table"></table>
<br>

<hr>

<select name="work_type_select" id="work_type_select" required onchange="getWorkTitles()">
</select>
<br>

<select name="work_title_selec" id="work_title_select" required onchange="getWorkForms()">
</select>
<br>

<select name="work_form_select" id="work_form_select" required onchange="getWorkInstanceDetails()">
</select>
<br>

<input list="work_instance_datalist" id="work_instance_details_input" onsubmit="addUserReportRow()">
<datalist id="work_instance_datalist">
</datalist>
<br>
<button id="add_user_report_row_button" onclick="addUserReportRow()">Додати рядок</button>


</body>
</html>