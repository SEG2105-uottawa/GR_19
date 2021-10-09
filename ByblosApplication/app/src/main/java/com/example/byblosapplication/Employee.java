package com.example.byblosapplication;

public class Employee extends Person{
    String username;
    String password;
    int employeeID;
    String role = "Employee";
    public Employee(String firstName,String lastName,String dateOfBirth,String homeAddress,String emailAddress, String age, String username, String password,int employeeID){
        super(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age);
        this.username = username;
        this.password = password;
        this.employeeID = employeeID;
    }
}
