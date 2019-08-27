package com.example.pfa_p.Model;

import android.text.InputType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AnswerOptions {


    public static final int OPTION_YESNO = 100;  //exclusively for yes or no type questions
    public static final int OPTION_MCQ = 101;   // for options with 2 or more than 2 choices; if more than 2 choices with yes/no
    public static final int OPTION_TEXT = 102;  // if the user has to enter the answer as a text
    public static final int OPTION_RATING = 103; // when filling questionnaires if the answers are from 1- 4 or 4- 1 etc.
    public static final int OPTION_QUNATITY = 104; // when filling quantity for drugs


    private int optionTypes;
    private int numberOfOptions;
    private int viewType;

    public int getNumberOfOptions() {
        if (numberOfOptions > 8)
            return options.size();

        return numberOfOptions;
    }

    public void setNumberOfOptions(int numberOfOptions) {
        this.numberOfOptions = numberOfOptions;
    }

    /*public int getViewType() {

        return answerType;


    }*/

    public AnswerOptions(String answerType, int numberOfOptions, String... options1) {
        this.answerType = answerType;
        this.options = new ArrayList<>();
        this.numberOfOptions = numberOfOptions;
        //  this.answerType = answerType;
        options = Arrays.asList(options1);
    }

    public String getAnswerType(){
        return  answerType;
    }

    public List<String> getOptions() {
        return options;
    }

    private List<String> options;
    private String answerType;

    public int getInputType() {

        switch (answerType) {


            case "TEXT": {
                return InputType.TYPE_CLASS_TEXT;
            }
            case "NUMBER": {
                return InputType.TYPE_CLASS_NUMBER;
            }
            case "DATE": {
                return InputType.TYPE_CLASS_DATETIME;
            }

            default:
                return InputType.TYPE_CLASS_TEXT;
        }
    }

    /*public void createOptionsArray(int options_type) throws IllegalAccessException {

        switch (options_type) {
            case OPTION_YESNO:
                numberOfOptions = 2;
            case OPTION_TEXT:
                numberOfOptions = 1;
            case OPTION_RATING:
                numberOfOptions = 1;
            case OPTION_QUNATITY:
                numberOfOptions = 1;
            case OPTION_MCQ:
                throw new IllegalAccessException();
        }
        options = new String[numberOfOptions];
    }*/

    /*public void setAnswerType(int answerTypeCode) {

        this.answerType = answerTypeCode;
    }*/

   /* public void setAnswerOptions(String... option) {
        if (answerType != 102) {
            options.addAll(Arrays.asList(option));
        }
    }*/


}




