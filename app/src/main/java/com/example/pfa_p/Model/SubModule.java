package com.example.pfa_p.Model;

import android.content.ContentValues;
import android.content.Context;

import com.example.pfa_p.Database.SurveyContract;
import com.example.pfa_p.SurveyDataSingleton;

import java.util.ArrayList;
import java.util.List;

import static com.example.pfa_p.Utils.JSONHelper.getSurveyId;

public class SubModule extends LeftPane {

    private String name;
    //   private boolean hasDomains;
    private Module module;
    private int numberOfDomains;
    private int index; // index in module list
    private List<Domain> domains;
    private int numberOfQuestions;
    private long sectionIdInDb; // PRIMARY KEY in TABLE_SECTIONS
    private List<Question> questions;
    private boolean isPresent = true; //a flag which takes false value only in assessment module
    private Result result;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasDomains() {
        return domains.size()>0 ;
    }

    public int getNumberOfDomains() {
        return numberOfDomains;
    }

    public void setNumberOfDomains(int numberOfDomains) {
        this.numberOfDomains = numberOfDomains;
    }

    public List<Domain> getDomains() {
        return domains == null ? new ArrayList<>() : domains;
    }

    public void setDomains(List<Domain> domains) {

        this.domains = domains;

    }

    public void setIsPresent(boolean flag) {
        this.isPresent = flag;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public long getId() {
        return sectionIdInDb;
    }

    public void setId(long _id){
        this.sectionIdInDb = _id ;
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

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public SubModule() {
    }

    public SubModule(String name) {

        this.name = name;
    }

    public SubModule(String name, boolean hasDomains, int numberOfDomains) {

        this.name = name;
        //createDomainsArray(hasDomains, numberOfDomains);

    }

    public ContentValues getContentValues() {

        ContentValues values = new ContentValues();
        values.put(SurveyContract.SurveyEntry.SECTIONS_COLUMN_NAME, name);
        values.put(SurveyContract.SurveyEntry.SECTIONS_COLUMN_SURVEY_ID, getSurveyId());
        return values;

    }

    public void setModule(Module module) {

        this.module = module;
    }

    public Module getModule() {
        return module;
    }


    public static SubModule getSubModule(Context context, String questionName) {

        List<Question> questions = SurveyDataSingleton.getInstance(context).getQuestions();

        SubModule subModule;

        for (int i = 0; i < questions.size(); i++) {

            if (questions.get(i).getQuestionName().equals(questionName)) {
                return questions.get(i).getSubModule();
            }
        }

        throw new IllegalArgumentException("Question value from Db doesn't match with Survey Value");

    }


    public Result getResult() {
        this.result = new Result(this);
        return result;
    }


    @Override
    public int hashCode() {
        return 11;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SubModule) {
            return ((SubModule) obj).name.equals(this.name);
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
/* public void setHasDomains(boolean hasDomains) {
        this.hasDomains = hasDomains;
    }*/

 /* public String getResultForSectionWiseLimits(int firstLimit, int secondLimit, int thirdLimit, float meanSectionScore) {


        if (meanSectionScore > 0 && meanSectionScore <= firstLimit) {

            return RatingSystem.RESULT_NO_INTERVENTION_REQUIRED;
        } else if (meanSectionScore > firstLimit && meanSectionScore <= secondLimit) {
            return RatingSystem.RESULT_INTERVENTION_REQUIRED;
        } else if (meanSectionScore > secondLimit && meanSectionScore <= thirdLimit) {
            return RatingSystem.RESULT_INTERVETION_REQUIRED_WITH_FOLLOW_UP;
        } else {
            return RatingSystem.RESULT_NEED_REFERRAL;
        }

    }*/

    /*@Override
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


    }*/