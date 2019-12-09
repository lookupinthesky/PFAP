package com.example.pfa_p.Model;

public class QuestionRule {

    boolean disables;
    int[] dependentQuestions;

    public boolean isDisables() {
        return disables;
    }

    public void setDisables(boolean disables) {
        this.disables = disables;
    }

    String necessaryAnswer;

    public int[] getDependentQuestions() {
        return dependentQuestions;
    }

    public String getNecessaryAnswer() {
        return necessaryAnswer;
    }

    public void setNecessaryAnswer(String necessaryAnswer) {
        this.necessaryAnswer = necessaryAnswer;
    }

    public String getAnswerIfNotApplicable() {
        return answerIfNotApplicable;
    }

    public void setAnswerIfNotApplicable(String answerIfNotApplicable) {
        this.answerIfNotApplicable = answerIfNotApplicable;
    }

    public void setDependentQuestions(int[] dependentQuestions) {
        this.dependentQuestions = dependentQuestions;
    }

    String answerIfNotApplicable;


}
