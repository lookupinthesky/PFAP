package com.example.pfa_p.Model;

import java.util.ArrayList;
import java.util.List;

public class Module {

    private String name;
    private int numberOfSections;
    private List<SubModule> sections;
    private int index;
    private boolean isResultBased = false;

    public Module(String moduleName) {
        this.name = moduleName;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
    public void setResultBased(boolean flag){
        this.isResultBased = flag;
    }

    public boolean isResultBased(){
        return isResultBased;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSections() {
        return numberOfSections;
    }

    public void setNumberOfSections(int numberOfSections) {
        this.numberOfSections = numberOfSections;
    }

    public List<SubModule> getSections() {
        return sections;
    }



    public List<SubModule> getSectionst() {

        if (isResultBased) {
            List<SubModule> sectionsTemp = sections;
            for (int i : indicesToRemove) {
                sections.remove(i);
            }

        } /*else {
            return getSections();
        }*/
        return sections;
    }

  /*  private Module calculate(Module module){


        for(SubModule subModule: module.getSections()){



        }




    }*/


    public Module getFilteredModule(int... indicesToRemove) {

        List<SubModule> sections = getSections();

        for (int i : indicesToRemove) {
            sections.remove(i);
        }
        return this;


    }

    public Module calculate() {

        boolean isQuestionnaireA = false;
        boolean isQuestionnaireB = false;
        boolean isQuestionnaireC = false;
        boolean isQuestionnaireD = false;
        boolean isQuestionnaireE = false;
        boolean isQuestionnaireF = true;
        int response = 0;


        List<Question> questions = getSections().get(0).getQuestions();
        for (int i = 0; i < 3; i++) {
            Question question = questions.get(i);
            response += question.getAnswerIndex();
            if (response < 2) {
                isQuestionnaireA = true;

                break;
            }
        }
        if (questions.get(3).getAnswerIndex() == 0) {
            isQuestionnaireB = true;
        }
        if (questions.get(2).getAnswerIndex() == 0 && questions.get(6).getAnswerIndex() == 0) {
            isQuestionnaireC = true;
        }
        for (int i = 4; i < 10; i++) {
            if (questions.get(i).getAnswerIndex() == 0) {
                isQuestionnaireD = true;
                break;
            }
        }
        if (questions.get(10).getAnswerIndex() != 0) {
            isQuestionnaireE = true;
        }
    }

    public void setSections(List<SubModule> sections) {
        this.sections = sections;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Module) {
            return ((Module) obj).name.equals(this.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 10;
    }
}
