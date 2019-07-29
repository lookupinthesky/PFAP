package com.example.pfa_p.Model;

import java.util.ArrayList;

public class Module {

    private String name;
    private int numberOfSections;
    private ArrayList<SubModule> sections;

    public Module(String moduleName) {
        this.name = moduleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSections() {
        return numberOfSections;
    }

    public void setNumberOfSections(int numberOfSections) {
        this.numberOfSections = numberOfSections;
    }

    public ArrayList<SubModule> getSections() {
        return sections;
    }

    public void setSections(ArrayList<SubModule> sections) {
        this.sections = sections;
    }
}
