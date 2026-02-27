/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package soap;

import java.util.ArrayList;


public class Employees {
    
   private  ArrayList<Employee> employees= new ArrayList<>();
   
   public Employees(){
   }
   
   public void setEmployees(ArrayList<Employee> employees){
       this.employees.addAll(employees);
   }
   
   public ArrayList<Employee> getEmployees(){
       return employees;
   }

    
}
