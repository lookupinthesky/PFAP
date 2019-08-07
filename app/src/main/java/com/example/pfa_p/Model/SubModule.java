package com.example.pfa_p.Model;

import android.content.res.Resources;

import com.example.pfa_p.Utils.RatingSystem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SubModule extends LeftPane implements RatingSystem {

    private String name;

    private boolean hasDomains;

    private int numberOfDomains;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private List<Domain> domains;

    private int numberOfQuestions;

    private int index;

    private long sectionIdInDb;

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

    public List<Domain> getDomains() {
        return domains;
    }

    public void setDomains(List<Domain> domains) {

        this.domains = domains;

    }

    public long getId() {
        return sectionIdInDb;
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

    public SubModule() {
    }

    public SubModule(String name, boolean hasDomains, int numberOfDomains) {

        this.name = name;
        //createDomainsArray(hasDomains, numberOfDomains);

    }

    @Override
    public float getMeanScore() {


        //     List<Question> questionsListSectionWise = submodule.getQuestions();

        int rating = 0;
        if (getModule().getIndex() > 0) {


            for (int i = 0; i < getQuestions().size(); i++) {

                Question question = getQuestions().get(i);

                rating += question.getDomain().getRatingForResponse(question.getAnswerIndex());
            }
        }
            return (float) rating / (getNumberOfDomains());


        }


    private Module module;

    public void setModule(Module module) {

        this.module = module;
    }

    public Module getModule() {
        return module;
    }




    public String getResultForSectionWiseLimits(int firstLimit, int secondLimit, int thirdLimit, float meanSectionScore) {


        if (meanSectionScore > 0 && meanSectionScore <= firstLimit) {

            return RatingSystem.RESULT_NO_INTERVENTION_REQUIRED;
        } else if (meanSectionScore > firstLimit && meanSectionScore <= secondLimit) {
            return RatingSystem.RESULT_INTERVENTION_REQUIRED;
        } else if (meanSectionScore > secondLimit && meanSectionScore <= thirdLimit) {
            return RatingSystem.RESULT_INTERVETION_REQUIRED_WITH_FOLLOW_UP;
        } else {
            return RatingSystem.RESULT_NEED_REFERRAL;
        }

    }

    // this is redundant
   /* private void createDomainsArray(boolean has_domains, int number_of_domains){

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
    }*/

  /*  public void setDomainNames(int stringArrayResource){
        Resources res = getResources();
        domains = res.getStringArray(stringArrayResource);
    }*/

    //getters and setters


}
