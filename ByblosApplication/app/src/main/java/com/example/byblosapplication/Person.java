package com.example.byblosapplication;

public class Person {
    String firstName;
    String lastName;
    String dateOfBirth;
    String homeAddress;
    String emailAddress;
    int age;
    public Person(String firstName,String lastName,String dateOfBirth,String homeAddress,String emailAddress, int age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.homeAddress = homeAddress;
        this.emailAddress = emailAddress;
        this.age = age;
    }
}
