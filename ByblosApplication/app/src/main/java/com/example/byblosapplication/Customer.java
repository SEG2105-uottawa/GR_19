package com.example.byblosapplication;

public class Customer extends Person{
    public String role = "Customer";
    public Customer(String firstName,String lastName,String dateOfBirth,String homeAddress,String emailAddress, int age, String username, String password){
        super(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password);
    }
}
