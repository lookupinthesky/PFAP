package com.example.pfa_p.Model;

import android.content.ContentValues;
import android.util.Log;

import com.example.pfa_p.Adapter.QuestionsAdapter;
import com.example.pfa_p.Database.SurveyContract;
import com.example.pfa_p.Utils.JavaUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question extends RightPane {

    /**
     * The text of the question
     */
    private String questionName;
    /**
     * Information about the available choices, input types to answer a question
     */
    private AnswerOptions options;
    /**
     * The submodule this question belongs to, cannot be null
     */
    private SubModule subModule;
    /**
     * The domain this question belongs to, by default null as domains are not present everytime
     */
    private Domain domain = null;
    /**
     * The PRIMARY KEY of the question in TABLE_QUESTIONS
     */
    private long questionIdInDb;
    /**
     * The index of the answer within the OPTIONS of a question in an order specified by the surveydata.json or csv
     */
    private int answerIndex = -1; //TODO: check if -1 doesn't disturb code
    /**
     * If Question belongs to Socio-Demographics then false, else true
     */
    private boolean isAssessment = false;
    /**
     * The GLOBAL serial number of question, same as index of the question in the questions array but starts from 1
     */
    private int serialNumber;
    /**
     * Submitted answer
     */
    private String answer = null;
    /**
     * Prisoner Information
     */
    private User user;
    /**
     * The PRIMARY KEY of stored answer in either of TABLE_ASSESSMENT_ANSWERS or TABLE_HISTORY_ANSWERS
     */
    private long answerIdInDb;

    private String rules;

    /*public void setQuestionView(View questionView) {
        this.questionView = questionView;
    }*/

    //  private View questionView;


    private int despondency = -1;

    private QuestionsAdapter adapter;

    public void setAdapter(QuestionsAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean hasDespondency() {
        if (getDomain() != null)
            return getDomain().getDespondency();
        return false;
    }

    public void setDespondency(String despondency) {
        if (despondency.equalsIgnoreCase("Positive")) {
            this.despondency = 0;
        } else if (despondency.equalsIgnoreCase("Neutral")) {
            this.despondency = 1;
        } else if (despondency.equalsIgnoreCase("Negative"))
            this.despondency = 2;
        else {
            throw new IllegalArgumentException("wrong text for despondency");
        }
    }

    public void setDespondency(int despondency) {
        if (despondency == 0 || despondency == 1 || despondency == 2 || despondency == -1)
            this.despondency = despondency;
        else throw new IllegalArgumentException("illegal value for despondency");
    }

    public int getDespondency() {

        return despondency;

    }

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
        Log.d("Questions", " getAnswerIndex Called: " + answerIndex);
        return answerIndex;
    }

    private boolean isEnabled = true;

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
        /*if (!enabled) {
            if(questionView!=null)
            questionView.setEnabled(false);
        }*//**/
    }

    /**
     * Returns the answer index of the question based on answer text, not applicable to input type text.
     *
     * @param answer
     * @return returns a value from 0 to 7 if a matching option is found
     * returns -2 if input type is text
     * throws exception if no matching value is found for the text. Can happen due to mismatch in surveydata resource file and stored value in db
     */
    public int getAnswerIndex(String answer) {
        if (getOptions().getNumberOfOptions() > 0) {
            answerIndex = getOptions().getOptions().indexOf(answer);
            if (answerIndex == -1 && !answer.equals("N/A")) {
                Log.d("Questions", "Set answer is " + answer + " stored answers = " + getOptions().getOptions().toString());
                throw new IllegalArgumentException("set answer doesn't match stored answer");
            }
            return answerIndex;
        } else
            return -2;

    }

    public QuestionRule getQuestionRule() {
        return questionRule;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public boolean setAnswer(String response, boolean isAssessment) { //TODO: why doesn't question already know it is assessment?

        Log.d("Questions", "Set Answer called inside questions class for question " + questionName + " answer is " + response);

        this.answer = response;
        this.isAssessment = isAssessment;
        this.answerIndex = getAnswerIndex(response);

        if (!rules.equals("")) {
            parseRule();
            return true;
        }
        return false;

    }

   /* public void setAnswer(int index, String response, boolean isAssessment) {

        this.answerIndex = index;
        setAnswer(response, isAssessment);
    }*/

    public String getAnswer() {
        return answer;
    }


    /**
     * Returns a contentvalue object to be written to the TABLE_QUESTIONS
     *
     * @return
     */
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


    public boolean isEnabled() {
        return isEnabled;
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

    /**
     * Returns a contentvalue object to be written to the TABLE_ASSESSMENT_ANSWERS or TABLE_HISTORY_ANSWERS depending upon the flag
     *
     * @return
     */
    public ContentValues getAnswerContentValues(/*boolean isAssessment*/) {
        ContentValues answerValues = new ContentValues();
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_VOLUNTEER_ID, getSubModule().getModule().getUser().getVolunteerId());
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_TIME_STAMP, JavaUtils.getCurrentTimeStamp());
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_QUESTION_ID, questionIdInDb);
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_USER_ID, /*user.getIdInDb()*/getSubModule().getModule().getUser().getIdInDb());
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_RESPONSE, getAnswer());
        //       answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_SURVEY_ID, getSurveyId());
        answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_FLAG, "dirty");
        if (isAssessment) {
            answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_VISIT_NUMBER, getVisitNumber());
            answerValues.put(SurveyContract.SurveyEntry.ANSWERS_COLUMN_DESPONDENCY, getDespondency()); //TODO: by default "nil"
        }
        return answerValues;


    }

    public static int[] strArrayToIntArray(String[] a) {
        int[] b = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = Integer.parseInt(a[i]);
        }

        return b;
    }

    QuestionRule questionRule;

    private void setQuestionRule(QuestionRule rule) {
        this.questionRule = rule;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    private void parseRule() {

        QuestionRule questionRule = new QuestionRule();

        boolean disables = false;
        int[] dependentQuestions;
        String necessaryAnswer;
        String temp;
        List<Integer> depQues = new ArrayList<>();


        Matcher matcher = Pattern.compile("\\d+").matcher(rules);

        while (matcher.find()) {
            depQues.add(Integer.valueOf(matcher.group()));
        }
        dependentQuestions = (new int[depQues.size()]);
        for (int i = 0; i < depQues.size(); i++) {
            dependentQuestions[i] = depQues.get(i);
        }
        /*temp = (rules.replaceAll("[^0-9]", " "));
        dependentQuestions = strArrayToIntArray(temp.trim().split(" "));*/
        String isDependentOnAnswer;
        String answerToBeSetIfNotApplicable;

        String[] splitString = rules.split(" ");
        int size = splitString.length;
        necessaryAnswer = splitString[size - 1];
        disables = splitString[0].trim().equals("Disable");
        Log.d("Questions", "Is question disabled " + disables);
        answerToBeSetIfNotApplicable = "N/A";

        questionRule.setDependentQuestions(dependentQuestions);
        questionRule.setNecessaryAnswer(necessaryAnswer);
        questionRule.setAnswerIfNotApplicable("N/A");
        questionRule.setDisables(disables);
        setQuestionRule(questionRule);


    }

    /*public void applyRule(QuestionRule rule){

        setEnabled(rule.getEnabled());


    }*/



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
