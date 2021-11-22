package com.example.byblosapplication;

public class Employee extends Person{
    public int employeeID;
    public int branchNumber;
    public Employee(String firstName,String lastName,String dateOfBirth,String homeAddress,String emailAddress, int age, String username, String password,String id,String role ,int employeeID, int branchNumber){
        super(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password,id,role);
        this.employeeID = employeeID;
        this.branchNumber = branchNumber;
    }
}
