package com.example.byblosapplication;

import java.util.ArrayList;

public class Branch {
    public String address;
    public ArrayList<String> workingHours;
    public ArrayList<Service> providedServices;
    public String phoneNumber;
    public String id;

    public Branch(String address, ArrayList<String> workingHours, String phoneNumber, String id){
        this.address = address;
        this.workingHours = workingHours;
        providedServices = new ArrayList<Service>();
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public void addService(Service service){
        providedServices.add(service);
    }

    public void setWorkingHours(String startTime, String endTime){
        workingHours.set(0,startTime);
        workingHours.set(1,endTime);
    }
}
