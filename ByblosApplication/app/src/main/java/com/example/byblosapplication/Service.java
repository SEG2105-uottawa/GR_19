package com.example.byblosapplication;

public class Service {
    public String name;
    public String id;
    public int servicePrice;
    public int requiredAge;
    public String requiredLicense;
    public String serviceDescription;
    public Service(String name,String serviceID, int servicePrice, int requiredAge, String requiredLicense, String serviceDescription){
        this.id = id;
        this.name = name;
        this.servicePrice = servicePrice;
        this.requiredAge = requiredAge;
        this.requiredLicense = requiredLicense;
        this.serviceDescription = serviceDescription;
    }
}
