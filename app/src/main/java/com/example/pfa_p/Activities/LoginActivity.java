package com.example.pfa_p.Activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.pfa_p.Database.SurveyContract.SurveyEntry;
import com.example.pfa_p.Database.SurveyTaskLoader;
import com.example.pfa_p.Fragments.SurveySchemaFragment;
import com.example.pfa_p.Fragments.UserEntryDialogFragment;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.User;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

import java.util.List;

public class LoginActivity extends FragmentActivity {
    FragmentManager fm;
    FragmentTransaction ft;
    SurveySchemaFragment schemaFragment;
    //   UserEntryDialogFragment userEntryFragment;
    CoordinatorLayout parent;
    private static final int LOADER_ID = 100;
    LoaderManager.LoaderCallbacks<String> mCallbacks;
    private static final String LOG_TAG = LoginActivity.class.getName();
    DialogFragment dialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parent = findViewById(R.id.fragment_container);
        //    parent.setAlpha(0.2f);
        mCallbacks = new LoaderManager.LoaderCallbacks<String>() {
            @NonNull
            @Override
            public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                return new SurveyTaskLoader<String>(LoginActivity.this/*, prisonerId*/) {

                    @Nullable
                    @Override
                    public String loadInBackground() {
                        startSurvey(prisonerIdText);
                        return null;
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                    }
                };
            }

            @Override
            public void onLoadFinished(@NonNull Loader<String> loader, String data) {

                schemaFragment.receiveProgressUpdate(10000);

            }

            @Override
            public void onLoaderReset(@NonNull Loader<String> loader) {

            }
        };


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        //   ft.addToBackStack(null);
        //  DialogFragment dialogFragment = new UserEntryDialogFragment();


        schemaFragment = SurveySchemaFragment.getInstance(new SurveySchemaFragment.StartSurveyListener() {
            @Override
            public void onStartClick() {
                Intent intent = new Intent(LoginActivity.this, SurveyActivity.class);
                intent.putExtra("current_module_index", mCurrentModuleIndex);
                intent.putExtra("current_section_index", mCurrentSectionIndex);
                intent.putExtra("current_domain_index", mCurrentDomainIndex);
                LoginActivity.this.startActivity(intent);
            }
        });
        dialogFragment = UserEntryDialogFragment.getInstance(new UserEntryDialogFragment.NextButtonListener() {
            @Override
            public void onNextButtonClick(String prisonerId, String volunteerId) {

                prisonerIdText = prisonerId;


                LoginActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, schemaFragment).addToBackStack(null).commit();
                LoaderManager.getInstance(LoginActivity.this).initLoader(LOADER_ID, null, mCallbacks);

                //     ft.replace(R.id.fragment_container, schemaFragment);
                //    parent.setAlpha(1);
                //    ft.commit();

            }
        });
        dialogFragment.setCancelable(false);
//        dialogFragment.*/

        dialogFragment.show(ft, "dialog");
        /*ft.add(R.id.fragment_container, userEntryFragment);
        ft.commit();
*/
    }

    int mCurrentSectionIndex;
    int mCurrentDomainIndex;
    int mCurrentModuleIndex;
    int visitNumber = 0;

    private void setCurrentState(int currentModuleIndex, int currentSectionIndex, int currentDomainIndex) {

        this.mCurrentModuleIndex = currentModuleIndex;
        this.mCurrentSectionIndex = currentSectionIndex;
        this.mCurrentDomainIndex = currentDomainIndex;
    }


    //   public void setOnBackPressedListener


    @Override
    public void onBackPressed() {

        /* if(dialogFragment.isVisible()){


         *//*dialogFragment.setCancelable(true);
            dialogFragment.dismiss();*//*
            finish();
        }*/


       /* if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            finish();
        } else {
            finish();

        }*/
        //    super.onBackPressed();
    }


    private void insertNewUser(String prisonerId) {
        ContentValues cv = new ContentValues();
        if (prisonerId.equals("")) {
            prisonerId = "prisonerId";
        }
        cv.put(SurveyEntry.USERS_COLUMN_INMATE_ID, prisonerId);
        cv.put(SurveyEntry.USERS_COLUMN_NAME, "prisonerName");
        cv.put(SurveyEntry.USERS_COLUMN_TOTAL_VISITS, 0);
        cv.put(SurveyEntry.USERS_COLUMN_FLAG, "dirty");
        cv.put(SurveyEntry.USERS_COLUMN_HISTORY_FLAG, "INCOMPLETE");
        cv.put(SurveyEntry.USERS_COLUMN_ASSESSMENT_FLAG, "INCOMPLETE");
        Uri uri = getContentResolver().insert(SurveyEntry.TABLE_USERS_CONTENT_URI, cv);
        long _id = ContentUris.parseId(uri);
        setUserToModules(prisonerId,_id);
       /* User user = new User();
        user.setPrisonerId(prisonerId);
        user.setIdInDb(_id);
        List<Module> modules = SurveyDataSingleton.getInstance(this).getModules();
        for (Module module : modules) {
            module.setUser(user);
        }*/
    }

    private void setUserToModules(String prisonerId, long idInDb){
        User user = new User();
        user.setPrisonerId(prisonerId);
        user.setIdInDb(idInDb);
        List<Module> modules = SurveyDataSingleton.getInstance(this).getModules();
        for (Module module : modules) {
            module.setUser(user);
        }

    }

    private void deleteUser(long userId) {


        getContentResolver().delete(SurveyEntry.TABLE_USERS_CONTENT_URI, SurveyEntry.USERS_ID, new String[]{String.valueOf(userId)});
    }

    private void incrementVisitCounter() {
        visitNumber++;
        ContentValues cv = new ContentValues();
        cv.put(SurveyEntry.USERS_COLUMN_TOTAL_VISITS, visitNumber);
        getContentResolver().insert(SurveyEntry.TABLE_USERS_CONTENT_URI, cv);
    }

    /**
     * @param prisonerId
     */
    private void startSurvey(String prisonerId) {
        LoginActivity.SurveyHelperForUser helper = new LoginActivity.SurveyHelperForUser();
        long user_Id = helper.fetchUserData(prisonerId, this);
        if (user_Id == -1) {
            insertNewUser(prisonerId);
            setCurrentState(0, 0, -1);
            //     schemaFragment.receiveProgressUpdate(30);
            Log.d(LOG_TAG, "Method: StartSurvey, user not found, inserting a new user");
        } else {
            setUserToModules(prisonerId,user_Id);
            if (helper.isHistoryCompleted) {
                if (helper.isAssessmentCompleted) {
                    incrementVisitCounter();
                    setCurrentState(0, 0, 0);
                } else {
                    helper.fetchAssessmentTableDataForUser(user_Id, helper.totalVisits, this);
                }
            } else {
                helper.fetchHistoryTableDataForUser(user_Id, this);
            }
        }
    }

    String prisonerIdText = "prisonerId";


    /**
     *
     */
    class SurveyHelperForUser {

        boolean isHistoryCompleted = false;
        boolean isAssessmentCompleted = false;
        int totalVisits = 0;
        int currentModuleIndex;
        int currentSectionIndex;
        int currentDomainIndex = -1;

        String[] projection_history = new String[]{SurveyEntry.ANSWERS_COLUMN_QUESTION_ID,
                /*SurveyEntry.ANSWERS_COLUMN_SURVEY_ID,*/
                SurveyEntry.ANSWERS_COLUMN_USER_ID,
                SurveyEntry.ANSWERS_COLUMN_RESPONSE};
        String[] projection_assessment = new String[]{SurveyEntry.ANSWERS_COLUMN_QUESTION_ID,
                /*SurveyEntry.ANSWERS_COLUMN_SURVEY_ID,*/
                SurveyEntry.ANSWERS_COLUMN_USER_ID};

        SurveyHelperForUser() {
        }

        /**
         * @param prisonerId
         * @param context
         * @return
         */
        long fetchUserData(String prisonerId, Context context) {
            String selection_users = SurveyEntry.USERS_COLUMN_INMATE_ID + " = ?";
            String[] projection_users = new String[]{SurveyEntry.USERS_ID, SurveyEntry.USERS_COLUMN_INMATE_ID, SurveyEntry.USERS_COLUMN_TOTAL_VISITS, SurveyEntry.USERS_COLUMN_HISTORY_FLAG, SurveyEntry.USERS_COLUMN_ASSESSMENT_FLAG};
            String[] selectionArgs_users = new String[]{prisonerId};
            long _id = -1;
            Cursor cursor = context.getContentResolver().query(SurveyEntry.TABLE_USERS_CONTENT_URI, projection_users, selection_users, selectionArgs_users, null);

            if (cursor.moveToFirst()) {
                _id = cursor.getLong(cursor.getColumnIndex(SurveyEntry.USERS_ID));
                isHistoryCompleted = cursor.getString(cursor.getColumnIndex(SurveyEntry.USERS_COLUMN_HISTORY_FLAG)).equals("COMPLETED");
                isAssessmentCompleted = cursor.getString(cursor.getColumnIndex(SurveyEntry.USERS_COLUMN_ASSESSMENT_FLAG)).equals("COMPLETED");
                totalVisits = cursor.getInt(cursor.getColumnIndex(SurveyEntry.USERS_COLUMN_TOTAL_VISITS));

                cursor.close();
                return _id;
            }
            return _id;
        }

        /**
         * @param userId
         * @param context
         */
        void fetchHistoryTableDataForUser(long userId, Context context) {

            String selection_history = SurveyEntry.ANSWERS_COLUMN_USER_ID + " = ?";

            String[] selectionArgs_history = new String[]{String.valueOf(userId)};

            List<Question> questions = SurveyDataSingleton.getInstance(context).getQuestions();
            Cursor cursor = context.getContentResolver().query(SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, projection_history, selection_history, selectionArgs_history, SurveyEntry.ANSWERS_COLUMN_QUESTION_ID + " ASC");
            long questionId = -1;
            String response = "";
            int count = 0;
            int loopcounter = 0 ;
            if (cursor.moveToFirst()) {

                loopcounter = cursor.getCount();

                for(int i = 0; i<loopcounter; i++) {
                    // do {
                    count++;
                    questionId = cursor.getLong(cursor.getColumnIndex(SurveyEntry.ANSWERS_COLUMN_QUESTION_ID));
                    response = cursor.getString(cursor.getColumnIndex(SurveyEntry.ANSWERS_COLUMN_RESPONSE));

                    Question question = questions.get(i);
                    question.setAnswer(response,false);


              //      for (Question question : questions) {
            //            if (question.getId() == questionId) {// this is unnecessary as of now since multiple surveys are not supported //TODO: implement a Database interface to POJO classes
                            //setting answers because recreating run time data
                            question.setAnswer(response, false); // TODO: modify questions pojo to receive string answers for radiobuttons
                            //determine state from last question in database
                            if (count == cursor.getCount()) {
                                currentSectionIndex = question.getSubModule().getIndex(); //TODO: set as mCurrentSubmodule
                                currentModuleIndex = question.getSubModule().getModule().getIndex();
                                currentDomainIndex = -1;
                                Log.d(LOG_TAG, "In loginActivity when user exists: mCurrentSectionIndex = " + mCurrentSectionIndex);
                                setCurrentState(currentModuleIndex, currentSectionIndex, currentDomainIndex);
                                break;
                            }

                           if(!cursor.isAfterLast()) cursor.moveToNext();
                       /* } else {
                            throw new IllegalStateException("Survey Questions do not match those in database");
                        }*/
                  //  }
                }
               /* } while (cursor.moveToNext());
                cursor.close();*/
                //    return false;
            } else {

                //    deleteUser(userId);
                setCurrentState(0, 0, -1);
            }

        }

        /**
         * @param userId
         * @param visitNumber
         * @param context
         */
        void fetchAssessmentTableDataForUser(long userId, int visitNumber, Context context) {

            String selection_assessment = SurveyEntry.ANSWERS_COLUMN_USER_ID + " = ? AND " + SurveyEntry.ANSWERS_COLUMN_VISIT_NUMBER + " = ?";
            String[] selectionArgs_assessment = new String[]{String.valueOf(userId), String.valueOf(visitNumber)};
            Cursor cursor = context.getContentResolver().query(SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, projection_assessment, selection_assessment, selectionArgs_assessment, null);
            List<Question> questions = SurveyDataSingleton.getInstance(context).getQuestions();
            long questionId;
            String response;
            int count = 0;
            int tempVisitNumber = -1;
            if (cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    count++;
                    questionId = cursor.getLong(cursor.getColumnIndex(SurveyEntry.ANSWERS_COLUMN_QUESTION_ID));
                    response = cursor.getString(cursor.getColumnIndex(SurveyEntry.ANSWERS_COLUMN_RESPONSE));
                    //     tempVisitNumber = cursor.getInt(cursor.getColumnIndex(SurveyEntry.ANSWERS_COLUMN_VISIT_NUMBER));
                    //get assessment table data for the given visit number
                    for (Question question : questions) {
                        if (question.getId() == questionId) { //TODO: implement a Database interface to POJO classes
                            question.setAnswer(response, false);
                            if (count == cursor.getCount()) {
                                currentSectionIndex = question.getSubModule().getIndex(); //TODO: set as mCurrentSubmodule
                                currentModuleIndex = question.getSubModule().getModule().getIndex();
                                currentDomainIndex = question.getDomain().getIndex();
                                setCurrentState(currentModuleIndex, currentSectionIndex, currentDomainIndex);
                            }
                        }
                    }
                    cursor.close();
                }
            }
        }

    }
}
