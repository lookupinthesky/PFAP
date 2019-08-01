package com.example.pfa_p.Model;

import android.content.res.Resources;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SubModule {

    private String name;
    private  boolean hasDomains;
    private int numberOfDomains;
    private  String[] domains;
    private  int numberOfQuestions;
    private List<Question> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasDomains() {
        return hasDomains;
    }

    public void setHasDomains(boolean hasDomains) {
        this.hasDomains = hasDomains;
    }

    public int getNumberOfDomains() {
        return numberOfDomains;
    }

    public void setNumberOfDomains(int numberOfDomains) {
        this.numberOfDomains = numberOfDomains;
    }

    public String[] getDomains() {
        return domains;
    }

    public void setDomains(String[] domains) {
        this.domains = domains;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public SubModule(){};

    public SubModule(String name, boolean hasDomains, int numberOfDomains){

        this.name = name;
        createDomainsArray(hasDomains, numberOfDomains);

    }



    // this is redundant
    private void createDomainsArray(boolean has_domains, int number_of_domains){

        if(has_domains){
            hasDomains = has_domains;
            numberOfDomains = number_of_domains ;
            domains = new String[numberOfDomains];
        }
        else
            domains = new String[1];
    }

    public void setDomainNames(int num, String name){
        if(num<numberOfDomains){
            domains[num] = name;
        }
    }

  /*  public void setDomainNames(int stringArrayResource){
        Resources res = getResources();
        domains = res.getStringArray(stringArrayResource);
    }*/

    //getters and setters






}
