package com.example.pfa_p.Model;

import android.text.InputType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A model class that keeps all the information related to the available options for a question, type of  user input required
 */
public class AnswerOptions {

    /**
     * A yes/no question will have value 2, an editable answer will be 0; as specified in the source file
     */
    private int numberOfOptions;
    /**
     * If number of options is zero list is currently filled with dummy data from surveydata file such as "option1", "option2" etc.
     */
    private List<String> options;
    /**
     * As specified in the surveydata file
     */
    private String answerType;


    public AnswerOptions(String answerType, int numberOfOptions, String... options1) {
        this.answerType = answerType;
        this.options = new ArrayList<>();
        this.numberOfOptions = numberOfOptions;
        options = Arrays.asList(options1);
    }

    public String getAnswerType() {
        return answerType;
    }

    public List<String> getOptions() {
        return options;
    }

    /**
     * NOTE: use getNumberOfOptions instead getOptions().size() because number of options are manually entered while size is automatically generated and can have null fields
     * @return
     */
    public int getNumberOfOptions() {
        if (numberOfOptions > 8)
            return options.size(); //TODO: fix this

        return numberOfOptions;
    }

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

   /* public static final int OPTION_YESNO = 100;  //exclusively for yes or no type questions
    public static final int OPTION_MCQ = 101;   // for options with 2 or more than 2 choices; if more than 2 choices with yes/no
    public static final int OPTION_TEXT = 102;  // if the user has to enter the answer as a text
    public static final int OPTION_RATING = 103; // when filling questionnaires if the answers are from 1- 4 or 4- 1 etc.
    public static final int OPTION_QUNATITY = 104; // when filling quantity for drugs*/
  /* public void setNumberOfOptions(int numberOfOptions) {
       this.numberOfOptions = numberOfOptions;
   }*/

    /*public int getViewType() {

        return answerType;


    }*/
}




