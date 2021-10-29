package com.example.byblosapplication;

public class Employee extends Person{
    public int employeeID;
    String role = "Employee";
    public Employee(String firstName,String lastName,String dateOfBirth,String homeAddress,String emailAddress, int age, String username, String password,int employeeID){
        super(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password);
        this.employeeID = employeeID;
    }
}
