package ua.pravex.listener;

import Dao.*;
import Dao.Impl.*;
import Service.DomainObjectsManager;
import Service.Impl.DomainObjectsManagerImpl;
import json.*;
import json.impl.*;
import Service.EmployeesExchangeService;
import Service.Impl.EmplysExchSrvImpl;
import ua.pravex.objectPools.WorkFormPool;
import ua.pravex.objectPools.WorkInstancePool;
import ua.pravex.objectPools.WorkTitlePool;
import ua.pravex.objectPools.WorkTypePool;
import Service.ReportsExchangeService;
import Service.Impl.RprtsExchSrvImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitializerListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();

        DBProceduresManager dbProceduresManager = new DBProceduresManagerImpl();
        dbProceduresManager.setServletContext(servletContext);
        servletContext.setAttribute("dbProceduresManager", dbProceduresManager);

        EmployeeDao employeeDao = new EmplyDaoImlpHOME();
        employeeDao.setServletContext(servletContext);
        servletContext.setAttribute("employeeDao", employeeDao);

        ReportRecordDao reportRecordDao = new RprtRcrdDaoImlpHOME();
        reportRecordDao.setServletContext(servletContext);
        servletContext.setAttribute("reportRecordDao", reportRecordDao);

        WorkTypeDao workTypeDao = new WorkTypeDaoImpl();
        workTypeDao.setServletContext(servletContext);
        servletContext.setAttribute("workTypeDao", workTypeDao);

        WorkTitleDao workTitleDao = new WorkTitleDaoImpl();
        workTitleDao.setServletContext(servletContext);
        servletContext.setAttribute("workTitleDao", workTitleDao);

        WorkFormDao workFormDao = new WorkFormDaoImpl();
        workFormDao.setServletContext(servletContext);
        servletContext.setAttribute("workFormDao", workFormDao);

        WorkInstanceDao workInstanceDao = new WorkInstanceDaoImpl();
        workInstanceDao.setServletContext(servletContext);
        servletContext.setAttribute("workInstanceDao", workInstanceDao);


        ReportsExchangeService reportsExchangeService = new RprtsExchSrvImpl();
        reportsExchangeService.setServletContext(servletContext);
        servletContext.setAttribute("reportsExchangeService", reportsExchangeService);

        EmployeesExchangeService employeesExchangeService = new EmplysExchSrvImpl();
        employeesExchangeService.setServletContext(servletContext);
        servletContext.setAttribute("employeesExchangeService", employeesExchangeService);

        DomainObjectsManager domainObjectsManager = new DomainObjectsManagerImpl();
        domainObjectsManager.setServletContext(servletContext);
        servletContext.setAttribute("domainObjectsManager", domainObjectsManager);


        servletContext.setAttribute("workTypePool", new WorkTypePool());

        servletContext.setAttribute("workTitlePool", new WorkTitlePool());

        servletContext.setAttribute("workFormPool", new WorkFormPool());

        servletContext.setAttribute("workInstancePool", new WorkInstancePool());


        UserReportJSONConvertor userReportJSONConvertor = new UserReportJSONConvertorImpl();
        userReportJSONConvertor.setServletContext(servletContext);
        servletContext.setAttribute("userReportJSONConvertor", userReportJSONConvertor);

        WorkInstanceJSONConvertor workInstanceJSONConvertor = new WorkInstanceJSONConvertorImlp();
        workInstanceJSONConvertor.setServletContext(servletContext);
        servletContext.setAttribute("workInstanceJSONConvertor", workInstanceJSONConvertor);

        UserReportRecordsLineJSONConvertor userReportRecordsLineJSONConvertor = new UserReportRecordsLineJSONConvertorImpl();
        userReportRecordsLineJSONConvertor.setServletContext(servletContext);
        servletContext.setAttribute("userReportRecordsLineJSONConvertor", userReportRecordsLineJSONConvertor);

        ReportRecordJSONConvertor reportRecordJSONConvertor = new ReportRecordJSONConvertorImlp();
        reportRecordJSONConvertor.setServletContext(servletContext);
        servletContext.setAttribute("reportRecordJSONConvertor", reportRecordJSONConvertor);

        WorkTypeJSONConvertor workTypeJSONConvertor = new WorkTypeJSONConvertorImpl();
        workTypeJSONConvertor.setServletContext(servletContext);
        servletContext.setAttribute("workTypeJSONConvertor", workTypeJSONConvertor);

        WorkTitleJSONConvertor workTitleJSONConvertor = new WorkTitleJSONConvertorImpl();
        workTitleJSONConvertor.setServletContext(servletContext);
        servletContext.setAttribute("workTitleJSONConvertor", workTitleJSONConvertor);

        WorkFormJSONConvertor workFormJSONConvertor = new WorkFormJSONConvertorImpl();
        workFormJSONConvertor.setServletContext(servletContext);
        servletContext.setAttribute("workFormJSONConvertor", workFormJSONConvertor);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}