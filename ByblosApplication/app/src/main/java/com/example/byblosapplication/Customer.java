package com.example.byblosapplication;

public class Customer extends Person{
    String username;
    String password;
    String role = "Customer";
    public Customer(String firstName,String lastName,String dateOfBirth,String homeAddress,String emailAddress, String age, String username, String password){
        super(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age);
        this.username = username;
        this.password = password;
    }
}
