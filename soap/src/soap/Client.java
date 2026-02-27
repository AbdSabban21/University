
package soap;


import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;


public class Client {

    public static void main(String[] args) throws MalformedURLException {
        try {

            URL url = new URL("http://localhost:18020/ws/users?wsdl");
           //1st argument service URI, refer to wsdl document above
            //2nd argument is service name, refer to wsdl document above
            QName qname = new QName("http://soap/", "DefaultUserImplServiceService");
            Service service = Service.create(url, qname);
            UserService servicePort = service.getPort(UserService.class);


            Employees employeesList = servicePort.getEmployees();
            System.out.println(servicePort.addEmployee(new Employee("John", 5000)));
            System.out.println(servicePort.addEmployee(new Employee("Sarah", 7500)));
            System.out.println(servicePort.addEmployee(new Employee("Michael", 12000)));
            System.out.println(servicePort.addEmployee(new Employee("Emma", 6800)));
            System.out.println(servicePort.addEmployee(new Employee("David", 15000)));
            System.out.println(servicePort.addEmployee(new Employee("Olivia", 9200)));
            System.out.println(servicePort.addEmployee(new Employee("Daniel", 8300)));
            System.out.println(servicePort.addEmployee(new Employee("Sophia", 11000)));
            System.out.println(servicePort.addEmployee(new Employee("James", 9700)));
            System.out.println(servicePort.addEmployee(new Employee("Isabella", 14000)));
            System.out.println("===============================================");
            employeesList = servicePort.getEmployees();

            for (int i = 0; i < employeesList.getEmployees().size(); i++) {
                Employee employee = (Employee) employeesList.getEmployees().get(i);
                System.out.println("name: " + employee.getName());
                System.out.println("id: " + employee.getId());
                System.out.println("Salary: " + employee.getSalary());
                System.out.println("=====================");
            }
            System.out.println("===============================================");
            System.out.println("===============================================");

            System.out.println("adding Employee : ");

            System.out.println(servicePort.addEmployee(new Employee("Abd", 10000)));
            employeesList = servicePort.getEmployees();
            System.out.println("===============================================");
            System.out.println("===============================================");

            Employee emp = servicePort.getEmployee(2);
            if (emp.getId() == -1) {
                System.out.println("Employee not found");
            } else {
                System.out.println("Name:" + emp.getName()
                        + "\nId:" + emp.getId()
                        + "\nSalary:" + emp.getSalary());
            }
            employeesList = servicePort.getEmployees();
            System.out.println("===============================================");
            System.out.println("===============================================");



            Employee updateEmp=new Employee();
            updateEmp.setId(1);
            updateEmp.setName("Hamza");
            updateEmp.setSalary(20000);
            String msg=new String();
            msg=servicePort.updateEmployee(updateEmp);
            System.out.println(msg);
            employeesList = servicePort.getEmployees();
            System.out.println("===============================================");
            System.out.println("===============================================");
            System.out.println("deleting Employee:");

            System.out.println(servicePort.deleteEmployee(2));
            employeesList = servicePort.getEmployees();
            System.out.println("===============================================");
            System.out.println("===============================================");

            System.out.println("modified list:");

            for (int i = 0; i < employeesList.getEmployees().size(); i++) {
                Employee employee = (Employee) employeesList.getEmployees().get(i);
                System.out.println("name: " + employee.getName());
                System.out.println("id: " + employee.getId());
                System.out.println("Salary: " + employee.getSalary());
                System.out.println("=====================");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
