package com.example.pfa_p.Model;

import android.content.ContentValues;
import android.util.Log;

import com.example.pfa_p.Database.SurveyContract;

public class Question extends RightPane {

    private String questionName;
    private AnswerOptions options;
    private SubModule subModule;
    private Domain domain = null;
    private long questionIdInDb;
    private int answerIndex;
    private boolean isAssessment = false;
    private int serialNumber;
    private String answer = null;
    private User user;
    private int viewType;
    private long answerIdInDb;

    public long getId() {
        return questionIdInDb;
    }

    public void setId(long _idInDb) {
        this.questionIdInDb = _idInDb;
    }

    public long getAnswerIdInDb() {
        return answerIdInDb;
    }

    public void setAnswerIdInDb(long answerIdInDb) {
        this.answerIdInDb = answerIdInDb;
    }

    public SubModule getSubModule() {
        return subModule;
    }

    public void setSubModule(SubModule subModule) {
        this.subModule = subModule;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Question(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public AnswerOptions getOptions() {
        return options;
    }

    public void setOptions(AnswerOptions options) {
        this.options = options;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getAnswerIndex() {
        /*if(answerIndex == -1){
            throw new IllegalArgumentException("set answer doesn't match stored answer");
        }*/
        return answerIndex;
    }

    public int getAnswerIndex(String answer){
        if (getOptions().getNumberOfOptions() > 0) {
            answerIndex = getOptions().getOptions().indexOf(answer);
            if(answerIndex == -1){
                Log.d("Questions", "Set answer is " + answer +  " stored answers = " + getOptions().getOptions().toString()  );
                throw new IllegalArgumentException("set answer doesn't match stored answer");
            }
            return answerIndex;
        }else
            return - 2;

    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public void setAnswer(String response, boolean isAssessment) {

        Log.d("Questions", "Set Answer called inside questions class for question " + questionName + " answer is " + response);

        this.answer = response;
        this.isAssessment = isAssessment;
        /*if (getOptions().getOptions().size() > 0) {
            answerIndex = getOptions().getOptions().indexOf(response);
        }*/
        this.answerIndex = getAnswerIndex(response);

    }

    public void setAnswer(int index, String response, boolean isAssessment) {

        this.answerIndex = index;
        setAnswer(response, isAssessment);
    }

    public String getAnswer() {
        return answer;
    }

    public ContentValues getQuestionContentValues() {

        ContentValues questionValues = new ContentValues();

        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_NAME, questionName);
        //   questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_SURVEY_ID, getSurveyId());
        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_SECTION_ID, getSubModule().getId());
        if (getDomain() != null)
            questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_DOMAIN_ID, getDomain().getId());
        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_TYPE, getOptions().getAnswerType());
        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_FLAG, "dirty");

        return questionValues;
    }

    private String getUserId() {

        return "Dummy_User_Id";
    }

    /*private int getSurveyId(){

        return 0 ;
    }*/

    public int getVisitNumber() {

        return 1;
    }

    public ContentValues getAnswerContentValues(/*boolean isAssessment*/) {
        ContentValues answerValues = new ContentValues();
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_QUESTION_ID, questionIdInDb);
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_USER_ID, /*user.getIdInDb()*/getSubModule().getModule().getUser().getIdInDb());
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_RESPONSE, getAnswer());
        //       answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_SURVEY_ID, getSurveyId());
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_FLAG, "dirty");
        if (isAssessment) {
            answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_VISIT_NUMBER, getVisitNumber());
        }
        return answerValues;


    }

    /*private byte[] getSurveyId() {
    }*/

}
 /*public String getSubModuleName() {
        return subModuleName;
    }

    public void setSubModuleName(String subModuleName) {
        this.subModuleName = subModuleName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }*/
