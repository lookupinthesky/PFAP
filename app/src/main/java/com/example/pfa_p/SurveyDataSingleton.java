package com.example.pfa_p;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.pfa_p.Activities.MainActivity;
import com.example.pfa_p.Activities.SurveyActivity;
import com.example.pfa_p.Database.SurveyContract;
import com.example.pfa_p.Database.SurveyContract.SurveyEntry;
import com.example.pfa_p.Database.SurveyProvider;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.DomainResultModel;
import com.example.pfa_p.Model.FinalResult;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.ResultAbstractModel;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.Model.User;
import com.example.pfa_p.Utils.JSONHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import timber.log.Timber;

import static com.example.pfa_p.Database.SurveyContract.SurveyEntry.TABLE_DOMAINS_CONTENT_URI;
import static com.example.pfa_p.Database.SurveyContract.SurveyEntry.TABLE_SECTIONS_CONTENT_URI;
import static com.example.pfa_p.Database.SurveyContract.SurveyEntry.TABLE_SURVEYS_CONTENT_URI;

public class SurveyDataSingleton {

    private static volatile SurveyDataSingleton sInstance;
    private List<Module> modules;
    private List<CurrentSessionData> currentSessionUsers;
    private List<CurrentSessionData> currentSessionData = new ArrayList<>();
    public static final String ANSWERS_FLAG_COMPLETE = "COMPLETE";
    public static final String ANSWERS_FLAG_INCOMPLETE = "INCOMPLETE";


    private List<Question> questions;
    private List<User> users;
    private int totalSurveysTaken;
    private int totalUserSurveyed;
    private int totalUnSyncedEntries = 0;
    private static long mCurrentPrisonerId;

    public List<User> getUsers() {
        return users;
    }

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
    String[] projection_users = new String[]{SurveyEntry.USERS_ID, SurveyEntry.USERS_COLUMN_INMATE_ID,/* SurveyEntry.USERS_COLUMN_NAME,*/ SurveyEntry.USERS_COLUMN_FLAG};


    //private constructor.
    private SurveyDataSingleton(Context context) {
        if (sInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        } else {
            createSurveyDataAndAddToCurrentSession(context);
            //     getUsersDataFromDb(context);
        }
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
        InputStream inputStream = context.getResources().openRawResource(R.raw.surveydata4);
        String jsonString = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONHelper helper = new JSONHelper(jsonString);
        //       CSVHelper helper = new CSVHelper(inputStream);
        modules = helper.getModules();
        sections = helper.getSections();
        domains = helper.getDomains();
        questions = helper.getQuestions();
        writeToDb(context);
       /* try {
            printDatabase(context);
        } catch (JSONException e) {
            throw new IllegalStateException("Failed to print test database");
        }*/

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

        Cursor cursor = context.getContentResolver().query(TABLE_SURVEYS_CONTENT_URI, survey_projections, SurveyEntry.SURVEY_COLUMN_SURVEY_ID + " = ?", new String[]{surveyId}, null);

        if (cursor.moveToFirst()) {
            isSurveyPresent = true;

        } else {
            ContentValues cv = new ContentValues();
            cv.put(SurveyEntry.SURVEY_COLUMN_SURVEY_ID, surveyId);
            cv.put(SurveyEntry.SURVEY_COLUMN_FLAG, "dirty");
            Uri uri = context.getContentResolver().insert(TABLE_SURVEYS_CONTENT_URI, cv);
            long _id = ContentUris.parseId(uri);
            JSONHelper.setSurveyId(_id);
        }

    }

    private void insertSections(Context context) {
        String[] section_projection = new String[]{SurveyEntry.SECTIONS_ID, SurveyEntry.SECTIONS_COLUMN_NAME};

        ContentValues sectionContentValues = new ContentValues();
        if (!isSurveyPresent) {
            for (SubModule subModule : sections) {
                sectionContentValues = subModule.getContentValues();
                Uri uri = context.getContentResolver().insert(TABLE_SECTIONS_CONTENT_URI, sectionContentValues); //TODO: assign section ids to corresponding sections
                long _id = ContentUris.parseId(uri);
                subModule.setId(_id);
            }
        } else {
            Cursor cursor = context.getContentResolver().query(TABLE_SECTIONS_CONTENT_URI, section_projection, null, null, SurveyEntry.SECTIONS_ID + " ASC");
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
                Uri uri = context.getContentResolver().insert(TABLE_DOMAINS_CONTENT_URI, domainContentValues);
                long _id = ContentUris.parseId(uri);
                domain.setId(_id);

            }
        } else {
            Cursor cursor = context.getContentResolver().query(TABLE_DOMAINS_CONTENT_URI, domain_projection, null, null, SurveyEntry.DOMAINS_COLUMN_ID + " ASC");
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


    public void createSurveyDataAndAddToCurrentSession(Context context) {

        createSurveyData(context);
        addCurrentSurveyToSessionDataList();
    }

    private void addCurrentSurveyToSessionDataList() {

        CurrentSessionData data = new CurrentSessionData();
        data.setSerialNumber(userCount);
        data.setSurveyData(modules);
        currentSessionData.add(data);
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public List<CurrentSessionData> getCurrentSessionData() {
        return currentSessionData;
    }

    public void setCurrentSessionData(List<CurrentSessionData> currentSessionData) {
        this.currentSessionData = currentSessionData;
    }

    CurrentSessionData surveyData;
    //  List<CurrentSessionData> currentSessionData = new ArrayList<>();

    int userCount;
    /*public  void addToCurrentSessionDataAndCreateNew(CurrentSessionData data, Context context){



        currentSessionUsers.add(data);

        surveyData = new CurrentSessionData();

        //clean up old identifiers, make all modules, submodule, domain objects again - repeat startup procedure after collecting all the data to this object


        createSurveyData(context);
        userCount ++;

        surveyData.setSurveyData(modules);
        surveyData.setSerialNumber(userCount);


    }
*/

    public class CurrentSessionData {


        CurrentSessionData() {
        }

        int serialNumber;
        List<Module> surveyData;

        String result;
        List<Uri> tablesUpdated;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public List<Uri> getTablesUpdated() {
            return tablesUpdated;
        }

        public void setTablesUpdated(List<Uri> tablesUpdated) {
            this.tablesUpdated = tablesUpdated;
        }

        public int getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(int serialNumber) {
            this.serialNumber = serialNumber;
        }

        public List<Module> getSurveyData() {
            return surveyData;
        }

        public void setSurveyData(List<Module> surveyData) {
            this.surveyData = surveyData;
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

    public List<User> getUsersDataFromDb(Context context) {

        users = new ArrayList<>();

        getDataFromUsersTable(users, context);

        getDataFromResultsTable(users, context);

        Log.d(SurveyActivity.class.getName(), "Users from db = " + users.toString());

        return users;
    }

    private void getDataFromUsersTable(List<User> users, Context context) {
        Cursor cursor = context.getContentResolver().query(SurveyEntry.TABLE_USERS_CONTENT_URI, projection_users, null, null, null);

        try {
            if (cursor.moveToFirst()) {

                do {
                    User user = new User();
                    user.setPrisonerId(cursor.getString(cursor.getColumnIndex(SurveyEntry.USERS_COLUMN_INMATE_ID)));
                    //             user.setName(cursor.getString(cursor.getColumnIndex(SurveyEntry.USERS_COLUMN_NAME)));
                    long idInDb = (cursor.getLong(cursor.getColumnIndex(SurveyEntry.USERS_ID)));
                    user.setIdInDb(idInDb);
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
                /*User user = new User();
                user.setPrisonerId("");
                user.setName("");
                user.setIdInDb(0);
                user.setSynced("dirty");
                users.add(user);*/
                return;
            }
        } finally {
            cursor.close();
        }
    }

    String resultsCurrent;

    public void setResultsForCurrentSession(String results) {

        this.resultsCurrent = results;
    }


    private void getDataFromResultsTable(List<User> users, Context context) {
        for (User user : users) {

            long idInDb = user.getIdInDb();
            String selection_users = SurveyEntry.RESULTS_ID + " = ?";
            String[] selectionArgs_users = new String[]{String.valueOf(idInDb)};
            Cursor cursor1 = context.getContentResolver().query(SurveyEntry.TABLE_RESULTS_CONTENT_URI, null, selection_users, selectionArgs_users, null);

            try {
                if (cursor1.moveToFirst()) {
                    user.setTimeStamp(cursor1.getString(cursor1.getColumnIndex(SurveyEntry.RESULTS_TIME_STAMP)));
                    boolean historyFlag = cursor1.getString(cursor1.getColumnIndex(SurveyEntry.RESULTS_COLUMN_HISTORY_FLAG)).equalsIgnoreCase("COMPLETED");
                    boolean assessmentFlag = cursor1.getString(cursor1.getColumnIndex(SurveyEntry.RESULTS_COLUMN_ASSESSMENT_FLAG)).equalsIgnoreCase("COMPLETED");
                    String status = historyFlag & assessmentFlag ? "COMPLETE" : "INCOMPLETE";
                    user.setStatus(status);
                    String action = status.equalsIgnoreCase("COMPLETE") ? "VIEW RESULTS" : "RESUME";
                    user.setAction(action);
                    String volunteerId = cursor1.getString(cursor1.getColumnIndex(SurveyEntry.RESULTS_VOLUNTEER_ID));
                    user.setVolunteerId(volunteerId);
                    cursor1.close();
                } else {
                    user.setTimeStamp("");
                    user.setStatus("");
                    user.setAction("");
                }
            } finally {
                cursor1.close();
            }
        }

    }


    // must only be called after survey completion
    public String getSurveyResultForInmateInJSON() {

        List<FinalResult> finalResults = new ArrayList<>();
        List<SubModule> subModules = modules.get(2).getSections();
        for (SubModule subModule : subModules) {
            FinalResult result = new FinalResult();
            ResultAbstractModel sectionResult = result.getSectionResult();
            sectionResult.setName(subModule.getName());
            sectionResult.setScore(subModule.getResult().getMeanScore());
            sectionResult.setResult(subModule.getResult().getResultText());
            List<Domain> domains = subModule.getDomains();
            for (Domain domain : domains) {
                DomainResultModel domainResult = new DomainResultModel();
                domainResult.setName(domain.getName());
                domainResult.setScore(domain.getResult().getResultValueActual());
                domainResult.setResult(domain.getResult().getResultText());
                if (domain.getDespondency()) {
                    domainResult.setDespondencyScore(domain.getResult().getDespondencyValue());
                    domainResult.setDespondencyResult(domain.getResult().getDespondencyResultText());
                }
                result.getDomainResult().add(domainResult);
            }
            finalResults.add(result);
        }
        Gson gson = new Gson();
        return gson.toJson(finalResults);
    }

    public ArrayList<FinalResult> parseResultsJSON(String json) {

        Gson gson = new Gson();
        TypeToken<List<FinalResult>> token = new TypeToken<List<FinalResult>>() {
        };
        return gson.fromJson(json, token.getType());


    }

    ArrayList<FinalResult> finalResults;

    public ArrayList<FinalResult> getFinalResults() {
        return finalResults;
    }

    public void setFinalResults(String results) {

        finalResults = parseResultsJSON(results);
    }


    private void printDatabase(Context context) throws JSONException {
        JSONArray array = getExportableDatabaseInJSON(context);
        String database = array.toString();
        int maxLogSize = 1000;
        for (int i = 0; i <= database.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > database.length() ? database.length() : end;
            Timber.tag("database logs").v(database.substring(start, end));
        }

        //    writeToFile(database);

    }

    private void writeToFile(String content) {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/sample_data.txt");

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.d("Error Database", e.toString());
        }
    }

    public JSONArray getExportableDatabaseInJSON(Context context) throws JSONException {
        Log.d(MainActivity.class.getName(), "method: getExportableDatabaseInJson = ");
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        JSONObject userData = getCursorFromTable(SurveyContract.SurveyEntry.TABLE_USERS_CONTENT_URI, context, data);
        if (userData != null) {
            arr.put(userData);
        }
        JSONObject surveyData = getCursorFromTable(TABLE_SURVEYS_CONTENT_URI, context, data);
        if (surveyData != null) {
            arr.put(surveyData);
        }
        JSONObject sectionsData = getCursorFromTable(TABLE_SECTIONS_CONTENT_URI, context, data);
        if (sectionsData != null) {
            arr.put(sectionsData);
        }
        JSONObject domainsData = getCursorFromTable(TABLE_DOMAINS_CONTENT_URI, context, data);
        if (domainsData != null) {
            arr.put(domainsData);
        }
        JSONObject questionsData = getCursorFromTable(SurveyContract.SurveyEntry.TABLE_QUESTIONS_CONTENT_URI, context, data);
        if (questionsData != null) {
            arr.put(questionsData);
        }
        JSONObject historyData = getCursorFromTable(SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, context, data);
        if (historyData != null) {
            arr.put(historyData);
        }
        JSONObject assessmentData = getCursorFromTable(SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, context, data);
        if (assessmentData != null) {
            arr.put(assessmentData);
        }
        JSONObject resultsData = getCursorFromTable(SurveyContract.SurveyEntry.TABLE_RESULTS_CONTENT_URI, context, data);
        if (resultsData != null) {
            arr.put(resultsData);
        }
        Log.d(MainActivity.class.getName(), "method: getExportableDatabaseInJson = " + arr.toString());
        return arr;
    }

    public JSONObject getCursorFromTable(Uri tableuri, Context context, JSONObject obj) throws JSONException {

        Log.d("Singleton", "method: getCursorFromTable" + "Uri = " + tableuri.toString());
      //  String selection = getSelectionForTableUri(tableuri);
        String selection = "flag = ?";

        //  String[] selectionArgsBefore = getSelectionArgsForTableUri(tableuri);

        String[] selectionArgsBefore = new String[]{"dirty"};

        //    String[] selectionArgsAfter = getSelectionArgsAfterForTableUri(tableuri);

        String[] selectionArgsAfter = new String[]{"syncing"};

        ContentValues cv = new ContentValues();
        cv.put("flag", "syncing");


        String tableName = getTableName(SurveyProvider.sUriMatcher.match(tableuri));

//         List<Uri> tablesBeingUpdated = currentSessionData.get(userCount).getTablesUpdated();

        //       tablesBeingUpdated.add(tableuri);
       // JSONObject obj;
        //    if(!(tableuri == TABLE_SURVEYS_CONTENT_URI || tableuri == TABLE_DOMAINS_CONTENT_URI || tableuri == TABLE_SECTIONS_CONTENT_URI)){
        context.getContentResolver().update(tableuri, cv, selection, selectionArgsBefore);
        Cursor cursor = context.getContentResolver().query(tableuri, null, selection, selectionArgsAfter, null);

        //    context.getContentResolver().update(tableuri, cv,  selection, selectionArgs);

        if (cursor.moveToFirst()) {
            obj = cursorToJSON(cursor, tableName);
      //      obj.put(tableName, cursorToJSON(cursor, tableName));
        } else {
            obj = null;
            //         tablesBeingUpdated.remove(tableuri);
        }
        if(obj!=null)
        Log.d("Singleton", "method: getCursorFromTable" + "json = " + obj.toString());
        cursor.close();
        return obj;
    }

    String getSelectionForTableUri(Uri uri) {

        if (uri == TABLE_SURVEYS_CONTENT_URI || uri == TABLE_DOMAINS_CONTENT_URI || uri == TABLE_SECTIONS_CONTENT_URI) {

            return null;
        } else {
            return "flag = ?";
        }
    }

    String[] getSelectionArgsForTableUri(Uri uri) {

        if (uri == TABLE_SURVEYS_CONTENT_URI || uri == TABLE_DOMAINS_CONTENT_URI || uri == TABLE_SECTIONS_CONTENT_URI) {

            return null;
        } else {
            return new String[]{"dirty"};
        }

    }

    String[] getSelectionArgsAfterForTableUri(Uri uri) {
        if (uri == TABLE_SURVEYS_CONTENT_URI || uri == TABLE_DOMAINS_CONTENT_URI || uri == TABLE_SECTIONS_CONTENT_URI) {

            return null;
        } else {
            return new String[]{"syncing"};
        }
    }

    /* ContentValues getCVForTableUri(Uri tableUri){


     }
 */
    List<Uri> tablesBeingUpdated = new ArrayList<>();


    public JSONObject cursorToJSON(Cursor cursor, String tableName) throws JSONException {

        JSONObject finalJ = new JSONObject();

        JSONArray rows = new JSONArray();

        if (cursor.moveToFirst()) {
            do {
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String columnName = cursor.getColumnName(i);
                    try {
                        switch (cursor.getType(i)) {
                            case Cursor.FIELD_TYPE_INTEGER:
                                jsonObject.put(columnName, cursor.getInt(i));
                                break;
                            case Cursor.FIELD_TYPE_FLOAT:
                                jsonObject.put(columnName, cursor.getFloat(i));
                                break;
                            case Cursor.FIELD_TYPE_STRING:
                                jsonObject.put(columnName, cursor.getString(i));
                                break;

                        }
                    } catch (Exception ex) {
                        Log.e(MainActivity.class.getName(), "Exception converting cursor column to json field: " + columnName);
                    }


                }
                rows.put(jsonObject);
                Log.d(MainActivity.class.getName(), "row = " + jsonObject.toString());
            } while (cursor.moveToNext());

            finalJ.put(tableName, rows); // TODO: don't return object, return array and create array of arrays.
        }
        return finalJ;
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


    private String getTableName(int match) {
        switch (match) {
            case SurveyProvider.SECTIONS:
                return SurveyEntry.TABLE_SECTIONS;
            case SurveyProvider.DOMAINS:
                return SurveyEntry.TABLE_DOMAINS;
            case SurveyProvider.RESULTS:
                return SurveyEntry.TABLE_RESULTS;
            case SurveyProvider.ANSWERS_ASSESSMENT:
                return SurveyEntry.TABLE_ASSESSMENT_ANSWERS;
            case SurveyProvider.ANSWERS_HISTORY:
                return SurveyEntry.TABLE_HISTORY_ANSWERS;
            case SurveyProvider.SURVEYS:
                return SurveyEntry.TABLE_SURVEYS;
            case SurveyProvider.QUESTIONS:
                return SurveyEntry.TABLE_QUESTIONS;
            case SurveyProvider.USERS:
                return SurveyEntry.TABLE_USERS;
            default:
                throw new IllegalStateException("Unknown Request for table name");
        }
    }


    public List<Module> getSurveyData() {
        return this.modules;
    }


}
