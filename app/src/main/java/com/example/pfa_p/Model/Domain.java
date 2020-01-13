package com.example.pfa_p.Model;

import android.content.ContentValues;

import com.example.pfa_p.Database.SurveyContract;

import java.util.List;

/**
 * Domain is the subsection of a given submodule. It is only present in Assessment Module.
 * It functions with a submodule and questions just like a submodule functions with module and questions
 * Each domain has its own set of rules on how the results of questions must be calculated, which is handled by @link Result instance present
 */
public class Domain extends LeftPane {
    /**
     * The list of questions belonging to the domain
     */
    private List<Question> questions;
    /**
     * The PRIMARY KEY of domain in TABLE_DOMAINS
     */
    private long domainIdInDb;
    /**
     * The submodule this domain belongs to
     */
    private SubModule subModule;
    /**
     * the index of domain within a SUBMODULE
     */
    private int index = 0;
    /**
     * name of domain
     */
    private String name;
    /**
     * One domain has despondency as a variable, and needs a different view representation than the rest of the domains
     */
    private boolean hasDespondency = false; //TODO:
    /**
     * The result instance acts as a mediator. It receives the result from the Result class and keeps it stored here for future access
     */
    private Result result;
    /**
     * flag if the result has been already calculated
     */
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

    public boolean getDespondency() {

        if(getSubModule().getName().equals("Questionnaire F")){
            return true;
        }
        return false;
    }

    public long getId() {
        return domainIdInDb;
    }

    public void setId(long _id) {
        this.domainIdInDb = _id;
    }


    public SubModule getSubModule() {
        return subModule;
    }

    public void setSubModule(SubModule subModule) {
        this.subModule = subModule;
    }

    /**
     * Returns a contentvalue object to be written to TABLE_DOMAINS
     * @return
     */
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(SurveyContract.SurveyEntry.DOMAINS_COLUMN_NAME, name);
        values.put(SurveyContract.SurveyEntry.DOMAINS_COLUMN_SECTION_ID, getSubModule().getId());

        //  values.put(SurveyContract.SurveyEntry.DOMAINS_COLUMN_SURVEY_ID, getSurveyId());
        return values;

    }

    /**
     * Sends an instance of domain object to @see Result class and gets calculated result back as an instance
     * @return
     */
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
            if (question.getAnswer() == null || question.getAnswer().length() == 0 || (question.hasDespondency() && question.getDespondency()== -1)) {
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