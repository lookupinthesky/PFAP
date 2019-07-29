package com.example.pfa_p.Model;

public class Question {

    private String questionName;
    private AnswerOptions options;
    private String subModuleName;
    private String domainName;
    private int serialNumber;

    public Question(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getSubModuleName() {
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

   /* public Question(int stringResourceId, AnswerOptions options, SubModule section) {

        Resources res = getResources();
        questionName = res.getString(stringResourceId);
        options = options;
        section = section;

    }*/
}
