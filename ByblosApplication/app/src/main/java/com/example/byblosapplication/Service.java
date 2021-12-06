package com.example.byblosapplication;

import java.util.ArrayList;

public class Service {
    public String name;
    public String id;
    public int servicePrice;
    public ArrayList<String> listOfRequirements;
    public Service(String name, String id, int servicePrice,ArrayList<String>listOfRequirements){
        this.id = id;
        this.name = name;
        this.servicePrice = servicePrice;
        this.listOfRequirements = listOfRequirements;
    }

    public void addRequirement(String requirement){
        listOfRequirements.add(requirement);
    }
}
