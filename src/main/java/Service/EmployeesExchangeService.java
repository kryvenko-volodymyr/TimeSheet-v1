package Service;

import Domain.Employee;

import javax.servlet.ServletContext;

/**
 * Created by Kryvenko on 02.01.2016.
 */
public interface EmployeesExchangeService {
    void setServletContext(ServletContext servletContext);

    public Employee getEmployeeByCode(String emplCode);

}
