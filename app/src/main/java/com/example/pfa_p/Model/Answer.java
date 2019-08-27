package com.example.pfa_p.Model;

public class Answer {


    String answer;
    int answerIndex;
    Question question;
    User user;
    boolean isAssessment;


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer, Question question) {
        this.answer = answer;
        this.question = question ;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAssessment() {
        return isAssessment;
    }

    public void setAssessment(boolean assessment) {
        isAssessment = assessment;
    }
}
