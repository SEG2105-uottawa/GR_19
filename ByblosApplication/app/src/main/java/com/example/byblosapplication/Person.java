package com.example.byblosapplication;

public class Person {
    public String firstName;
    public String lastName;
    public String dateOfBirth;
    public String homeAddress;
    public String emailAddress;
    public int age;
    public String username;
    public String password;
    public Person(String firstName,String lastName,String dateOfBirth,String homeAddress,String emailAddress, int age, String username, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.homeAddress = homeAddress;
        this.emailAddress = emailAddress;
        this.age = age;
        this.username = username;
        this.password = password;
    }
}
