package com.example.pfa_p.Model;

import java.util.ArrayList;
import java.util.List;

public class Module {

    private String name;
    private int numberOfSections;
    private List<SubModule> sections;
    private int index;

    public Module(String moduleName) {
        this.name = moduleName;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public List<SubModule> getSections() {
        return sections;
    }

    public void setSections(List<SubModule> sections) {
        this.sections = sections;
    }
}
