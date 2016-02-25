package ua.pravex.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LandingServ extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = (Connection) this.getServletContext().getAttribute("CRUDConn");
        try {
            java.sql.Statement st = conn.createStatement();
            ResultSet result = st.executeQuery("SELECT * FROM EMPLOYEE");
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            while (result.next()) {
                result.getString("name");
            }
            out.println("THIS IS THE LANDING PAGE");
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
