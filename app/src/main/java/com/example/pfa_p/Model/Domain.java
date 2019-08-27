package com.example.pfa_p.Model;

import android.content.ContentValues;

import com.example.pfa_p.Database.SurveyContract;

import java.util.List;

public class Domain extends LeftPane {

    private List<Question> questions;
    private long domainIdInDb;
    private SubModule subModule;
    private int index = 0;
    private String name;
    private boolean hasDespondency = false;
    private Result result;
    boolean isDomainResultCalculated = false;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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

    public int getDespondency() {
        return 0;
    }

    public long getId() {
        return domainIdInDb;
    }

    public void setId(long _id){
        this.domainIdInDb = _id ;
    }


    public SubModule getSubModule() {
        return subModule;
    }

    public void setSubModule(SubModule subModule) {
        this.subModule = subModule;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(SurveyContract.SurveyEntry.DOMAINS_COLUMN_NAME, name);
        values.put(SurveyContract.SurveyEntry.DOMAINS_COLUMN_SECTION_ID, getSubModule().getId());
      //  values.put(SurveyContract.SurveyEntry.DOMAINS_COLUMN_SURVEY_ID, getSurveyId());
        return values;

    }


    public Result getResult() {

        this.result = new Result(this);
        isDomainResultCalculated = true;
        return result;
    }


    @Override
    public int hashCode() {
        return 12;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Domain) {
            return ((Domain) obj).name.equals(this.name);
        }
        return false;
    }

    @Override
    public int getFilledValue() {
        return 0;
    }

    @Override
    public boolean isEveryQuestionAnswered() {
        for (Question question : questions) {
            if (question.getAnswer() == null || question.getAnswer().length() == 0) {
                return false;
            }
        }
        return true;
    }
}
















  /*public String getResults(float meanSectionScore) {

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
                } else
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

    }*/