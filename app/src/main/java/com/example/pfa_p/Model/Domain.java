package com.example.pfa_p.Model;

import java.util.List;

public class Domain extends LeftPane {

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
        return answerIndex;
    }

    public int getDespondancy() {

    }

    String name;
    List<Question> questions;
}
