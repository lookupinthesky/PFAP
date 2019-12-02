package com.example.pfa_p;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.pfa_p.Database.SurveyContract.SurveyEntry;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.Model.User;
import com.example.pfa_p.Utils.JSONHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class SurveyDataSingleton {

    private static volatile SurveyDataSingleton sInstance;
    private List<Module> modules;

    public List<User> getUsers() {
        return users;
    }

    private List<Question> questions;
    List<User> users;

    private int totalSurveysTaken;
    private int totalUserSurveyed;
    private int totalUnSyncedEntries = 0;
    private static long mCurrentPrisonerId;

    public static long getCurrentPrisonerId() {
        return mCurrentPrisonerId;
    }

    public static void setCurrentPrisonerId(long mPrisonerId) {
        mCurrentPrisonerId = mPrisonerId;
    }

    public int getTotalSurveysTaken() {
        return totalSurveysTaken;
    }

    public int getTotalUserSurveyed() {
        return totalUserSurveyed;
    }

    public int getTotalUnSyncedEntries() {
        return totalUnSyncedEntries;
    }

    // private Context mContext;
    String[] projection_users = new String[]{SurveyEntry.USERS_ID, SurveyEntry.USERS_COLUMN_INMATE_ID, SurveyEntry.USERS_COLUMN_NAME, SurveyEntry.USERS_COLUMN_FLAG};


    //private constructor.
    private SurveyDataSingleton(Context context) {
        if (sInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        } else
            createSurveyData(context);
        getUsersDataFromDb(context);
    }

   /* public void setContext(Context mContext) {
        this.mContext = mContext;
    }*/

    public List<Module> getModules() {
        return modules;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public static SurveyDataSingleton getInstance(Context context) {
        //Double check locking pattern
        if (sInstance == null) { //Check for the first time
            synchronized (SurveyDataSingleton.class) {   //Check for the second time.
                //if there is no instance available... create new one
                if (sInstance == null) sInstance = new SurveyDataSingleton(context);
            }
        }
        return sInstance;
    }


    List<Domain> domains;
    List<SubModule> sections;

    private void createSurveyData(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.surveydatav2);
        String jsonString = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONHelper helper = new JSONHelper(jsonString);
 //       CSVHelper helper = new CSVHelper(inputStream);
        modules = helper.getModules();
        sections = helper.getSections();
        domains = helper.getDomains();
        questions = helper.getQuestions();
        writeToDb(context);

    }


    private void writeToDb(Context context) {
        //TODO: write questions, sections, domains, survey tables data to db and get IDs

        insertSurvey(context);
        /*if (!isSurveyPresent) {*/
            insertSections(context);
            insertDomains(context);
            insertQuestions(context);
        //}

    }

    boolean isSurveyPresent = false;

    private void insertSurvey(Context context) {

        String[] survey_projections = new String[]{SurveyEntry.SURVEYS_COLUMN_PK, SurveyEntry.SURVEY_COLUMN_SURVEY_ID};

        String surveyId = JSONHelper.getSurveyId();

        Cursor cursor = context.getContentResolver().query(SurveyEntry.TABLE_SURVEYS_CONTENT_URI, survey_projections, SurveyEntry.SURVEY_COLUMN_SURVEY_ID + " = ?", new String[]{surveyId}, null);

        if (cursor.moveToFirst()) {
            isSurveyPresent = true;

        } else {
            ContentValues cv = new ContentValues();
            cv.put(SurveyEntry.SURVEY_COLUMN_SURVEY_ID, surveyId);
            context.getContentResolver().insert(SurveyEntry.TABLE_SURVEYS_CONTENT_URI, cv);
        }


    }

    private void insertSections(Context context) {
        String[] section_projection = new String[]{SurveyEntry.SECTIONS_ID, SurveyEntry.SECTIONS_COLUMN_NAME};

        ContentValues sectionContentValues = new ContentValues();
        if (!isSurveyPresent) {
            for (SubModule subModule : sections) {
                sectionContentValues = subModule.getContentValues();
                Uri uri = context.getContentResolver().insert(SurveyEntry.TABLE_SECTIONS_CONTENT_URI, sectionContentValues); //TODO: assign section ids to corresponding sections
                long _id = ContentUris.parseId(uri);
                subModule.setId(_id);

            }
        } else {
            Cursor cursor = context.getContentResolver().query(SurveyEntry.TABLE_SECTIONS_CONTENT_URI, section_projection, null, null, SurveyEntry.SECTIONS_ID + " ASC");
            String name;
            long _id;
            if (cursor.moveToFirst()) {
                for (SubModule subModule : sections) {
                    name = cursor.getString(cursor.getColumnIndex(SurveyEntry.SECTIONS_COLUMN_NAME));
                    _id = cursor.getLong(cursor.getColumnIndex(SurveyEntry.SECTIONS_ID));
                    if (subModule.getName().equals(name)) {
                        subModule.setId(_id);
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }

    }


    private void insertDomains(Context context) {

        String[] domain_projection = new String[]{SurveyEntry.DOMAINS_COLUMN_ID, SurveyEntry.DOMAINS_COLUMN_NAME};
        if (!isSurveyPresent) {
            for (Domain domain : domains) {
                ContentValues domainContentValues = domain.getContentValues();
                Uri uri = context.getContentResolver().insert(SurveyEntry.TABLE_DOMAINS_CONTENT_URI, domainContentValues);
                long _id = ContentUris.parseId(uri);
                domain.setId(_id);

            }
        } else {
            Cursor cursor = context.getContentResolver().query(SurveyEntry.TABLE_DOMAINS_CONTENT_URI, domain_projection, null, null, SurveyEntry.DOMAINS_COLUMN_ID + " ASC");
            String name;
            long _id;
            if (cursor.moveToFirst()) {
                for (Domain domain : domains) {
                    name = cursor.getString(cursor.getColumnIndex(SurveyEntry.DOMAINS_COLUMN_NAME));
                    _id = cursor.getLong(cursor.getColumnIndex(SurveyEntry.DOMAINS_COLUMN_ID));
                    if (domain.getName().equals(name)) {
                        domain.setId(_id);
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }

    }

    private void insertQuestions(Context context) {

        String[] question_projection = new String[]{SurveyEntry.QUESTIONS_ID, SurveyEntry.QUESTIONS_COLUMN_NAME};
        if (!isSurveyPresent)
            for (Question question : questions) {
                ContentValues questionContentValues = question.getQuestionContentValues();
                Uri uri = context.getContentResolver().insert(SurveyEntry.TABLE_QUESTIONS_CONTENT_URI, questionContentValues);
                long _id = ContentUris.parseId(uri);
                question.setId(_id);
            }

        else {
            Cursor cursor = context.getContentResolver().query(SurveyEntry.TABLE_QUESTIONS_CONTENT_URI, question_projection, null, null, SurveyEntry.QUESTIONS_ID + " ASC");
            String name;
            long _id;
            if (cursor.moveToFirst()) {
                for (Question question : questions) {
                    name = cursor.getString(cursor.getColumnIndex(SurveyEntry.QUESTIONS_COLUMN_NAME));
                    _id = cursor.getLong(cursor.getColumnIndex(SurveyEntry.QUESTIONS_ID));
                    if (question.getQuestionName().equals(name)) {
                        question.setId(_id);
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }
    }

    private void getUsersDataFromDb(Context context) {
        Cursor cursor = context.getContentResolver().query(SurveyEntry.TABLE_USERS_CONTENT_URI, projection_users, null, null, null);
        users = new ArrayList<>();
        User user = new User();
        try {
            if (cursor.moveToFirst()) {

                do {
                    user.setPrisonerId(cursor.getString(cursor.getColumnIndex(SurveyEntry.USERS_COLUMN_INMATE_ID)));
                    user.setName(cursor.getString(cursor.getColumnIndex(SurveyEntry.USERS_COLUMN_NAME)));
                    user.setIdInDb(cursor.getLong(cursor.getColumnIndex(SurveyEntry.USERS_ID)));
                    String flag = cursor.getString(cursor.getColumnIndex(SurveyEntry.USERS_COLUMN_FLAG));
                    user.setSynced(flag);
                    if (flag.equals("dirty")) {
                        totalUnSyncedEntries++;
                    }
                    users.add(user);
                }
                while (cursor.moveToNext());
                totalSurveysTaken = users.size();
                totalUserSurveyed = new HashSet<>(users).size();

            } else {

                user.setPrisonerId("");
                user.setName("");
                user.setIdInDb(0);
                user.setSynced("dirty");
                users.add(user);
            }
        } finally {
            cursor.close();
        }


    }



   /* public String getUserId(){

        return "Dummy_Id";
    }

    public int getSurveyId(){

        return 0;
    }

    public */






















    /*private void createSurveyData()  {

        //parse json or create data from objects in strings.xml

        //for now, create dummy data
        Question question1 = new Question("If yes, the last station from where you migrated?");
        question1.setDomainName("");
        AnswerOptions answer1 = new AnswerOptions();
        answer1.setAnswerType(102);
        answer1.setAnswerOptions();
        question1.setOptions(answer1);

        Question question2 = new Question("Occupation outside prison");
        question2.setDomainName("");
        AnswerOptions answer2 = new AnswerOptions();
        answer2.setAnswerType(101);
        answer2.setAnswerOptions("Public Servant", "Private Job", "Business", "Skilled Worker", "Unskilled Worker", "Student", "Unemployed");
        question2.setOptions(answer2);

        Question question3 = new Question("Do you constantly feel depressed or in low mood since last 2 weeks?");
        question3.setDomainName("Checking Emotional State");
        AnswerOptions answer3 = new AnswerOptions();
        answer3.setAnswerType(100);
        answer3.setAnswerOptions("Yes", "No");
        question3.setOptions(answer3);

        Question question5 = question3;
        question5.setDomainName("Checking Physical State");
        Question question6 = question3;
        question6.setDomainName("Checking Social State");

        Question question4 = new Question("Are you satisfied with your health?");
        AnswerOptions answer4 = new AnswerOptions();
        answer4.setAnswerType(100);
        answer4.setAnswerOptions("Yes", "No");
        question4.setOptions(answer4);
        question4.setDomainName("Checking Orientation");

        Question question7 = question4;
        question5.setDomainName("Checking Anxiousness and Irritability");

        Question question9 = question4;
        question9.setDomainName("Assessing Suicidal Feelings");

        Question question10 = question3;
        question3.setDomainName("Assessing Possible Severe Psychiatric Illness");

        Question question11 = question3;
        question11.setDomainName("Assessing Anti-Social Personality Traits");

        Question question12 = question3;
        question3.setDomainName("Assessing Responsiveness");

        ArrayList<Question> questions_1 = new ArrayList<>();
        questions_1.add(question1);
        questions_1.add(question2);
        questions_1.add(question1);
        questions_1.add(question2);
        questions_1.add(question1);

        ArrayList<Question> questions_2 = new ArrayList<>();

        questions_2.add(question3);
        questions_2.add(question4);
        questions_2.add(question3);
        questions_2.add(question4);
        questions_2.add(question3);


        Module module1 = new Module("Socio Demographics");
        module1.setNumberOfSections(4);
        Module module2 = new Module ("Assessing need for PFA-P" );
        module2.setNumberOfSections(6);

        SubModule subModule1 = new SubModule("Personal Details", false, 0);
        SubModule subModule2 = new SubModule("Prison Details", false, 0);
        SubModule subModule3 = new SubModule("Medical Details", false, 0);
        SubModule subModule4 = new SubModule("Drug Details", false, 0);

        SubModule subModule5 = new SubModule("Questionnaire A", true, 3);
        SubModule subModule6 = new SubModule("Questionnaire B", true, 2);
        SubModule subModule7 = new SubModule("Questionnaire C", true, 0);
        SubModule subModule8 = new SubModule("Questionnaire D", true, 1);
        SubModule subModule9 = new SubModule("Questionnaire E", true, 1);
        SubModule subModule10 = new SubModule("Questionnaire F", true, 1);

        subModule1.setNumberOfQuestions(5);
        subModule2.setNumberOfQuestions(5);
        subModule3.setNumberOfQuestions(5);
        subModule4.setNumberOfQuestions(5);

        subModule1.setQuestions(questions_1);
        subModule2.setQuestions(questions_1);
        subModule3.setQuestions(questions_1);
        subModule4.setQuestions(questions_1);

        ArrayList<SubModule> subModules = new ArrayList<>();
        subModules.add(subModule1);
        subModules.add(subModule2);
        subModules.add(subModule3);
        subModules.add(subModule4);

        module1.setSections(subModules);

        subModule5.setNumberOfQuestions(9);
        subModule6.setNumberOfQuestions(7);
        subModule7.setNumberOfQuestions(3);
        subModule8.setNumberOfQuestions(3);
        subModule9.setNumberOfQuestions(3);
        subModule10.setNumberOfQuestions(3);


        ArrayList<Question> quetionnaireA = new ArrayList<>();
        quetionnaireA.add(question3);
        quetionnaireA.add(question3);
        quetionnaireA.add(question6);
        quetionnaireA.add(question3);
        quetionnaireA.add(question6);
        quetionnaireA.add(question3);
        quetionnaireA.add(question5);
        quetionnaireA.add(question5);
        quetionnaireA.add(question5);
        subModule5.setQuestions(quetionnaireA);

        ArrayList<Question> quetionnaireB = new ArrayList<>();
        quetionnaireB.add(question7);
        quetionnaireB.add(question7);
        quetionnaireB.add(question4);
        quetionnaireB.add(question7);
        quetionnaireB.add(question4);
        quetionnaireB.add(question4);
        subModule6.setQuestions(quetionnaireB);

        ArrayList<Question> quetionnaireC = new ArrayList<>();
        quetionnaireC.add(question9);
        quetionnaireC.add(question9);
        quetionnaireC.add(question9);
        subModule7.setQuestions(quetionnaireC);

        ArrayList<Question> quetionnaireD = new ArrayList<>();
        quetionnaireC.add(question10);
        quetionnaireC.add(question10);
        quetionnaireC.add(question10);
        subModule8.setQuestions(quetionnaireD);

        ArrayList<Question> quetionnaireE = new ArrayList<>();
        quetionnaireE.add(question11);
        quetionnaireE.add(question11);
        quetionnaireE.add(question11);
        subModule9.setQuestions(quetionnaireE);

        ArrayList<Question> quetionnaireF = new ArrayList<>();
        quetionnaireE.add(question12);
        quetionnaireE.add(question12);
        quetionnaireE.add(question12);
        subModule10.setQuestions(quetionnaireF);

        ArrayList<SubModule> subModules2 = new ArrayList<>();
        subModules2.add(subModule5);
        subModules2.add(subModule6);
        subModules2.add(subModule7);
        subModules2.add(subModule8);
        subModules2.add(subModule9);
        subModules2.add(subModule10);

        module2.setSections(subModules2);

        ArrayList<Module> modulesNew = new ArrayList<>();
        modules.add(module1);
        modules.add(module2);

        this.modules = modulesNew;
    }*/

    public List<Module> getSurveyData() {
        return this.modules;
    }


}
