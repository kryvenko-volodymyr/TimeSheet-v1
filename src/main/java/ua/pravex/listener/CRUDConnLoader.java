package ua.pravex.listener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class CRUDConnLoader implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:/comp/env");

            DataSource dataSource = (DataSource) envCtx.lookup("/jdbc/rootConn");
            ServletContext servletContext = servletContextEvent.getServletContext();
            servletContext.setAttribute("rootConnDataSource", dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}