
package soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface UserService {
    @WebMethod
    public String addEmployee(Employee employee);

    @WebMethod
    public Employees getEmployees();

    @WebMethod
    public String updateEmployee(Employee updatedEmployee);

    @WebMethod
    public String deleteEmployee(int id);

    @WebMethod
    public Employee getEmployee(int id);
    
}
