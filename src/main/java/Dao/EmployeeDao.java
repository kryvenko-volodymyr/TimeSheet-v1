package Dao;

import Domain.Employee;

import javax.servlet.ServletContext;

/**
 * Created by Kryvenko on 02.01.2016.
 */
public interface EmployeeDao {
    public Employee getEmployeeByCode (String emplCode);

    void setServletContext(ServletContext servletContext);
}
