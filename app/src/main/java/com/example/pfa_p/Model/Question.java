package com.example.pfa_p.Model;

import android.content.ContentValues;

import com.example.pfa_p.Database.SurveyContract;

public class Question extends RightPane{

    private String questionName;
    private AnswerOptions options;
    private SubModule subModule;
    private Domain domain;
    private int _idInDb;
    private int answerIndex;
    private boolean isAssessment = false ;
    private int serialNumber;
    private String answer = null;
    private String questionIdInDb ;
    private int viewType;
    private long answerIdInDb;

    public int get_idInDb() {
        return _idInDb;
    }

    public void set_idInDb(int _idInDb) {
        this._idInDb = _idInDb;
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
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public void setAnswer(String response, boolean isAssessment){
        this.answer = response;
        this.isAssessment = isAssessment;
        if(getOptions().getOptions().size()>0){
        answerIndex =   getOptions().getOptions().indexOf(response);
        }

    }

    public void setAnswer(int index, String response, boolean isAssessment){

        this.answerIndex = index;
        setAnswer(response,isAssessment);
    }

    public String getAnswer(){
        return answer;
    }

    public ContentValues getQuestionContentValues(){

        ContentValues questionValues = new ContentValues();

        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_NAME, questionName);
        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_SURVEY_ID, getSurveyId());
        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_SECTION_ID, getSubModule().getId());
        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_DOMAIN_ID, getDomain().getId());
        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_TYPE, getOptions().getAnswerType());
        questionValues.put(SurveyContract.SurveyEntry.QUESTIONS_COLUMN_FLAG, "dirty");

        return questionValues;
    }

    private String getUserId(){

        return "Dummy_User_Id" ;
    }

    private int getSurveyId(){

        return 0 ;
    }

    private int getVisitNumber(){

        return 1;
    }

    public ContentValues getAnswerContentValues(/*boolean isAssessment*/) {
        ContentValues answerValues = new ContentValues();
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_QUESTION_ID, questionIdInDb);
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_USER_ID, getUserId());
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_RESPONSE, getAnswer());
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_SURVEY_ID, getSurveyId());
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_FLAG, "dirty");
        if(isAssessment){
            answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_VISIT_NUMBER, getVisitNumber());
        }

        return answerValues ;


    }

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
