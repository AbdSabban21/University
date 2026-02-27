
package soap;


public class Employee {
    
    private int salary;

    private static int currentId = 1;
    private int id;
    
    private String name;
    
    public Employee(){
        
    }

    public Employee(String name, int salary) {
        this.name = name;
        this.id =  currentId++;
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }
    
    public void setName(String name) {
        this.name= name;
    }

}
