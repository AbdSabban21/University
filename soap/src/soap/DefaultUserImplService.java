/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soap;


import java.util.ArrayList;
import javax.jws.WebService;
import java.util.Collections;

@WebService(endpointInterface = "soap.UserService")
public class DefaultUserImplService implements UserService {

    ArrayList<Employee> employeeList = new ArrayList<>();

    public DefaultUserImplService() {
    
    }


    public String addEmployee(Employee employee) {
        if (employee == null)
            return "Failure";

        for (Employee e : employeeList) {
            if (e.getId() == employee.getId()) {
                return "Failure";
            }
        }

        employeeList.add(employee);
        return "Success";
    }


    public Employees getEmployees() {
        Employees employees = new Employees();
        employees.setEmployees(employeeList);
        return employees;
    }

    public Employee getEmployee(int id) {
        for (Employee e : employeeList) {
            if (e.getId() == id) {
                return e;
            }
        }

        Employee notFound = new Employee();
        notFound.setId(-1);
        notFound.setName("NOT_FOUND");
        notFound.setSalary(0);
        return notFound;
    }
    public String updateEmployee(Employee updatedEmployee) {

        if (updatedEmployee == null) return "Failure";

        Employee existing = getEmployee(updatedEmployee.getId());

        if (existing == null) {
            return "Failure";
        }
        if (updatedEmployee.getName() == null || updatedEmployee.getName().isEmpty()) return "Failure";
        if (updatedEmployee.getSalary() < 0) return "Failure";

        existing.setName(updatedEmployee.getName());
        existing.setSalary(updatedEmployee.getSalary());

        return "Success";
    }

    public String deleteEmployee(int id) {

        Employee existingEmployee = getEmployee(id);

        if (existingEmployee == null) {
            return "Failure";
        }

        employeeList.remove(existingEmployee);
        return "Success";
    }


}
