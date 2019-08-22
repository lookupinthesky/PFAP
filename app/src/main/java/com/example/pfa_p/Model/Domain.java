package com.example.pfa_p.Model;

import com.example.pfa_p.Utils.RatingSystem;

import java.util.List;

public class Domain extends LeftPane {
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private int index = 0;

    public Domain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getRatingForResponse(int answerIndex) {

        if (getName().equals("Checking Physical State") || getName().equals("Checking Anxiousness & Irritability")) {
            return 4 - answerIndex;
        } else
            return answerIndex;
    }


    public int getDespondancy() {
        return 0;
    }

    public long getId() {
        return domainIdInDb;
    }

    String name;

    public SubModule getSubModule() {
        return subModule;
    }

    public void setSubModule(SubModule subModule) {
        this.subModule = subModule;
    }

    List<Question> questions;
    long domainIdInDb;
    SubModule subModule;


    public String getResults(float meanSectionScore) {

        switch (getSubModule().getName()) {

            case "Questionnaire A": {
                getSubModule().getResultForSectionWiseLimits(5, 10, 15, meanSectionScore);
            }
            case "Questionnaire B": {
                getSubModule().getResultForSectionWiseLimits(5, 10, 13, meanSectionScore);
            }
            case "Questionnaire C": {
                getSubModule().getResultForSectionWiseLimits(0, 3, 6, meanSectionScore);
            }
            case "Questionnaire D": {
                if (meanSectionScore > 1) {
                    return RatingSystem.RESULT_NEED_REFERRAL;
                }
                else
                    return RatingSystem.RESULT_NORMAL;
            }
            case "Questionnaire E": {
                if (meanSectionScore > 0 && meanSectionScore <= 6) {
                    return RatingSystem.RESULT_NORMAL;
                } else if (meanSectionScore > 6 && meanSectionScore <= 9) {
                    return RatingSystem.RESULT_PERSONALITY_SLIGHTLY_ANTISOCIAL;
                } else {
                    return RatingSystem.RESULT_PERSONALITY_PROMINENT_ANTISOCIAL;
                }
            }

            case "Questionnaire F": {

                if (getName().equals("Checking Responsiveness")) {
                    if (meanSectionScore > 0 && meanSectionScore <= 8) {
                        return RatingSystem.RESULT_NORMAL;
                    } else if (meanSectionScore > 6 && meanSectionScore <= 9) {
                        return RatingSystem.RESULT_PERSONALITY_SLIGHTLY_ANTISOCIAL;
                    } else {
                        return RatingSystem.RESULT_PERSONALITY_PROMINENT_ANTISOCIAL;
                    }
                } else if (getName().equals("Checking despondency")) {
                    if (meanSectionScore > 0 && meanSectionScore <= 5) {
                        return RatingSystem.RESULT_SATISFACTORY;
                    } else if (meanSectionScore > 5 && meanSectionScore <= 13) {
                        return RatingSystem.RESULT_MILD_DESPONDENCY;
                    } else {
                        return RatingSystem.RESULT_NEED_REFERRAL;
                    }
                }
            }
            default:
                return "";
        }

    }

    @Override
    public int hashCode() {
        return 12;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Domain){
            return ((Domain) obj).name.equals(this.name);
        }
        return false;
    }


    @Override
    public int getFilledValue() {
        return 0;
    }

    @Override
    public int getMaxValue() {
        return 0;
    }

    @Override
    public boolean isEveryQuestionAnswered() {


        return false;
    }
}
