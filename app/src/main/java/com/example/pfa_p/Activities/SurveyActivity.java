package com.example.pfa_p.Activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.pfa_p.Database.ResultTaskLoader;
import com.example.pfa_p.Database.SurveyContract.SurveyEntry;
import com.example.pfa_p.Database.SurveyTaskLoader;
import com.example.pfa_p.Fragments.SectionDetailsFragment;
import com.example.pfa_p.Fragments.SectionsListFragment;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.LeftPane;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.Result;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SurveyActivity extends FragmentActivity implements SectionsListFragment.OnListItemClickListener, LoaderManager.LoaderCallbacks<String>
        /*SectionDetailsFragment.OnNextClickListener*/ {


    FrameLayout sectionDetailsParent;
    FrameLayout sectionsListParent;
    SectionDetailsFragment sectionDetailsFragment;
    SectionsListFragment sectionsListFragment;
    SurveyDataSingleton surveyData;
    List<Module> modules;
    FragmentManager fm;
    FragmentTransaction ft;
    int mCurrentSectionIndex;
    int mCurrentDomainIndex;
    int mCurrentModuleIndex;
    boolean[] isSectionIPresent;
    private boolean isModuleChanged = false;
    LoaderManager.LoaderCallbacks<String> mCallbacks;
    Bundle loaderArgs;
    private static final int LOADER_ANSWERS = 100;
    private static final int LOADER_RESULTS = 101;
    TextView surveyHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        loaderArgs = new Bundle();

        android.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        Button button = findViewById(R.id.next_button);
        surveyHeading = findViewById(R.id.survey_heading);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextClick();
            }
        });


        if (getActionBar() != null)
            getActionBar().show();
        surveyData = SurveyDataSingleton.getInstance(this);
        modules = surveyData.getModules();
        Intent intent = getIntent();
        mCurrentModuleIndex = intent.getExtras().getInt("current_module_index");
        mCurrentSectionIndex = intent.getExtras().getInt("current_section_index");
        mCurrentDomainIndex = intent.getExtras().getInt("current_domain_index");
        isSectionIPresent = intent.getExtras().getBooleanArray("is_section_present");

        if (mCurrentModuleIndex < 3) {
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            sectionDetailsParent = findViewById(R.id.fragment_section_details_parent);
            sectionsListParent = findViewById(R.id.fragment_sections_list_parent);
            sectionDetailsFragment = SectionDetailsFragment.newInstance(/*DEFAULT_SECTIONS_EMPTY*/);
            sectionsListFragment = SectionsListFragment.newInstance(0/*DEFAULT_SECTIONS_EMPTY*/);
            sectionsListFragment.setData(modules.get(mCurrentModuleIndex));
            sectionsListFragment.setCurrentState(mCurrentSectionIndex, mCurrentDomainIndex, isSectionIPresent);
            sectionsListFragment.setOnListItemClickListener(this);
            ft.add(R.id.fragment_section_details_parent, sectionDetailsFragment);
            ft.add(R.id.fragment_sections_list_parent, sectionsListFragment);
            ft.commit();
        } else {
            Intent intent1 = new Intent(SurveyActivity.this, ResultsActivity.class);
            intent1.putExtra("is_section_present", isSectionIPresent);
            startActivity(intent1);
        }
        mCallbacks = this;
        displaySurveyHeading(mCurrentModuleIndex);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Survey is in progress. Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(SurveyActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                }).create().show();
    }

    /**
     * Calculates and increments the current value by 1 of one of mCurrentDomainIndex, mCurrentSectionIndex, mCurrentModuleIndex
     * depending upon current position while filling the form
     *
     * @param modules
     */
    private void calculateNext(List<Module> modules) {
        List<SubModule> subModules = modules.get(mCurrentModuleIndex).getSections();
        List<Domain> domains = subModules.get(mCurrentSectionIndex).getDomains(); //TODO: bug present here in mCurrentSectionindex
        if (mCurrentDomainIndex > domains.size() - 1 || mCurrentDomainIndex == domains.size() - 1) {
            if (mCurrentSectionIndex > subModules.size() - 1 || mCurrentSectionIndex == subModules.size() - 1) {
                if (mCurrentModuleIndex > modules.size() - 1) {
                    throw new IllegalStateException("Unexpected Request for next set");
                } else {
                    isModuleChanged = true;
                    mCurrentModuleIndex++;
                    mCurrentSectionIndex = 0;/* mCurrentModuleIndex == 1 ? 0 : getFirstSectionIndexFromArray(isSectionIPresent);*/ //TODO: bug present here
                    mCurrentDomainIndex = 0;

                    Log.d(LOG_TAG, "Loop1: mCurrentModuleIndex = " + mCurrentModuleIndex + " mCurrentSectionIndex = " + mCurrentSectionIndex + " mCurrentDomainIndex = " + mCurrentDomainIndex + " isModuleChanged = " + isModuleChanged);
                }
            } else {
                isModuleChanged = false;
                mCurrentSectionIndex = getCurrentSectionIndexFromArray(isSectionIPresent, mCurrentSectionIndex); //TODO: faulty - bug present - mindlessly increasing index without looking if present or not
                mCurrentDomainIndex = 0;

                Log.d(LOG_TAG, "Loop2: mCurrentModuleIndex = " + mCurrentModuleIndex + " mCurrentSectionIndex = " + mCurrentSectionIndex + " mCurrentDomainIndex = " + mCurrentDomainIndex + " isModuleChanged = " + isModuleChanged);
            }
        } else {

            isModuleChanged = false;
            mCurrentDomainIndex++;

            Log.d(LOG_TAG, "Loop3: mCurrentModuleIndex = " + mCurrentModuleIndex + " mCurrentSectionIndex = " + mCurrentSectionIndex + " mCurrentDomainIndex = " + mCurrentDomainIndex + " isModuleChanged = " + isModuleChanged);
        }
    }


    /**
     * @param item
     */
    @Override
    public void onListItemClick(LeftPane item) {
        loadQuestions(item);
    }

    /**
     * @param item
     */
    private void loadQuestions(LeftPane item) {
        if (sectionDetailsFragment != null) {
           /* if (item.isEveryQuestionAnswered()) {
               // sectionDetailsFragment.setDataWithAnswers(item);
            } else {*/
            sectionDetailsFragment.setData(item);
        }
    }


    LeftPane item;


    /**
     * method called by ClickListener for the next button on the the toolbar.
     */
    public void onNextClick() {
       /* String prisonerId = ""; //TODO:
        int visitNumber = 1;*/
        boolean isUpdate;
        Module mCurrentModule = modules.get(mCurrentModuleIndex);
        item = sectionsListFragment.getCurrentItem();
        isUpdate = !sectionDetailsFragment.setEditableAnswers();
        if (!item.isEveryQuestionAnswered()) {
            showSnackBar("Please complete all the fields!");
            return;
        }
        //    saveToDb(item, isUpdate); //TODO: push on background thread
        loaderArgs.putBoolean("isUpdate", isUpdate);
        loaderArgs.putBoolean("isResults", false);
        LoaderManager.getInstance(SurveyActivity.this).restartLoader(LOADER_ANSWERS, loaderArgs, mCallbacks); //initloader repeats data

        calculateNext(modules);
        if (!isModuleChanged) {
            /*if (mCurrentModuleIndex == 2) {
             *//*int domainIndex*//*
                mCurrentDomainIndex = 0; //TODO check logic
                *//*int sectionIndex*//*
                mCurrentSectionIndex = getCurrentSectionIndexFromArray(isSectionIPresent, mCurrentSectionIndex);
            }*/
            sectionsListFragment.onStateChanged(false);
        } else {
            if (mCurrentModule.getName().equals("Basic Questionnaire")) {
                isSectionIPresent = Result.evaluateQuestionnaires(mCurrentModule, this);// evaluate next sections before setting data to list
            }

            if (mCurrentModuleIndex < 3) {
                /*int domainIndex*/
                mCurrentDomainIndex = mCurrentModuleIndex == 1 ? -1 : 0; //TODO check logic
                /*int sectionIndex*/
                mCurrentSectionIndex = mCurrentModuleIndex == 1 ? 0 : getFirstSectionIndexFromArray(isSectionIPresent);
                sectionsListFragment.setCurrentState(/*sectionIndex*/ mCurrentSectionIndex, /*domainIndex*/mCurrentDomainIndex, null);
                sectionsListFragment.setData(/*modules.get(mCurrentModuleIndex).getSections()*/modules.get(mCurrentModuleIndex));
                sectionsListFragment.onStateChanged(true);
                displaySurveyHeading(mCurrentModuleIndex);
            } else {
                loaderArgs.putBoolean("isResults", true);
                LoaderManager.getInstance(SurveyActivity.this).restartLoader(LOADER_RESULTS, loaderArgs, mCallbacks);
                //  saveResultsToDb();
                Intent intent = new Intent(SurveyActivity.this, ResultsActivity.class);
                startActivity(intent);


            }

        }
    }

    private void displaySurveyHeading(int currentModuleIndex){

        if(currentModuleIndex<3) {
            String heading = modules.get(currentModuleIndex).getName();
            surveyHeading.setText(heading);
        }
    }

    private int getFirstSectionIndexFromArray(boolean[] isSectionIPresent) {

        for (int i = 0; i < isSectionIPresent.length; i++) {
            if (isSectionIPresent[i]) {
                return i;
            }
        }
        return -1;
    }

    private int getCurrentSectionIndexFromArray(boolean[] isSectionIPresent, int currentSectionIndex) {
        if (isSectionIPresent == null) {
            if (mCurrentModuleIndex == 2)
                throw new IllegalArgumentException();
            else
                return currentSectionIndex + 1;
        }

        if(currentSectionIndex == isSectionIPresent.length - 1){
            return currentSectionIndex + 1;
        }
        for (int i = currentSectionIndex; i < isSectionIPresent.length - 1 ; i++) {
            if (isSectionIPresent[i + 1]) {
                return i + 1;
            }
        }

        return -1;

    }

    String userId;

    /**
     * @param item     the clicked left pane item i.e. the submodule or subdomain for which the questions are being answered
     * @param isUpdate true if entered data is being updated for example after an unexpected shutdown
     * @return true if values were inserted in database, else false
     */
    private boolean saveToDb(LeftPane item, boolean isUpdate) {
        List<Question> questions;
        if (item instanceof Domain) {
            Log.d(SurveyActivity.class.getName(), "Method:saveToDb Called" + " item = " + ((Domain) item).getName());
            questions = ((Domain) item).getQuestions();
            userId = String.valueOf(((Domain) item).getSubModule().getModule().getUser().getIdInDb());
            insertAnswers(questions, true, isUpdate);

            return true;
        } else if (item instanceof SubModule) {
            if (!((SubModule) item).hasDomains()) {
                questions = ((SubModule) item).getQuestions();
                userId = String.valueOf(((SubModule) item).getModule().getUser().getIdInDb());
                if (((SubModule) item).getName().equals("Basic Questionnaire")) {
                    insertAnswers(questions, true, isUpdate);
                } else {
                    insertAnswers(questions, false, isUpdate);
                    if (((SubModule) item).getIndex() == ((SubModule) item).getModule().getSections().size() - 1)
                        updateHistoryFlagInResultsTable(userId);
                }
                return true;

            } else {
                throw new IllegalStateException("Header cannot be inserted into DB; No Questions Found");
            }
        }
        return false;

    }

    /**
     * History flag is by default incomplete and would remain incomplete until all the sections are completed.
     * When Module is changed and entries are saved to database it must be marked completed in the user table
     *
     * @param userId
     * @see com.example.pfa_p.Database.SurveyContract
     */
    private void updateHistoryFlagInResultsTable(String userId) { //TODO

        ContentValues cv = new ContentValues();
        cv.put(SurveyEntry.RESULTS_COLUMN_HISTORY_FLAG, "COMPLETED");
      int i =  getContentResolver().update(SurveyEntry.TABLE_RESULTS_CONTENT_URI, cv, SurveyEntry.RESULTS_PRISONER_ID + " =?", new String[]{userId});
    Log.d(LOG_TAG, "method:updateHistoryFlagInResultsTable; rows updated = " + i);
    }

    /**
     * Shows snackbar at the bottom when user clicks next button without completing the form
     *
     * @param display
     */
    private void showSnackBar(String display) {
        Snackbar snackBar = Snackbar.make(sectionDetailsParent.getRootView(), display, Snackbar.LENGTH_LONG);
       /* CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                snackBar.getView().getLayoutParams();
        params.setMargins(params.leftMargin , //TODO: Show snackbar above Bottomnavigation
                params.topMargin,
                params.rightMargin ,
                params.bottomMargin + 48);

        snackBar.getView().setLayoutParams(params);*/
        snackBar.show();
    }

    String visitNumber;

    /**
     * Inserts the data filled by the user into local database
     *
     * @param questionList List of questions which needs to be saved to database
     * @param isAssessment if the list is for assessment table as history and assessment tables are different
     *                     needs to marked true for basic questionnaire
     * @param isUpdate     if the method is called for changing already saved values or an incomplete form
     * @see com.example.pfa_p.Database.SurveyContract
     */
    private void insertAnswers(List<Question> questionList, boolean isAssessment, boolean isUpdate) {
        Uri uri;

        for (Question question : questionList) {
            String userId = String.valueOf(question.getSubModule().getModule().getUser().getIdInDb());
            visitNumber = String.valueOf(question.getVisitNumber());
            ContentValues cv = question.getAnswerContentValues();
            Log.d(SurveyActivity.class.getName(), "Content Values: " + cv.toString());
            if (!isUpdate) {
                if (isAssessment)
                    uri = getContentResolver().insert(SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, cv);
                else
                    uri = getContentResolver().insert(SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, cv);

                long _id = ContentUris.parseId(uri);
                if (_id != -1) {
                    question.setAnswerIdInDb(_id);
                } else {
                    Log.d(SurveyActivity.class.getName(), "Inserting answers URI = " + uri.toString() + "Content Values = " + cv.toString());
                    throw new IllegalStateException("Failed to insert Answers");

                }
            } else {
                if (isAssessment)
                    getContentResolver().update(SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, cv,
                            SurveyEntry.ANSWERS_COLUMN_USER_ID + " =? AND " + SurveyEntry.ANSWERS_COLUMN_VISIT_NUMBER + " =?", new String[]{userId, visitNumber});
                else
                    getContentResolver().update(SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, cv, SurveyEntry.ANSWERS_COLUMN_USER_ID + " = ?",
                            new String[]{userId});
            }

        }


    }
    String results;
    private static final String LOG_TAG = SurveyActivity.class.getName();

    private boolean saveResultsToDb() {

        Log.d(LOG_TAG, "method: saveResultsToDb called");

        results   = SurveyDataSingleton.getInstance(this).getSurveyResultForInmateInJSON();
        SurveyDataSingleton.getInstance(this).setFinalResults(results);
        String selection = SurveyEntry.RESULTS_PRISONER_ID + " = ?";
        String[] selectionArgs = new String[]{userId};


        ContentValues cv = new ContentValues();
        //  cv.put(SurveyEntry.RESULTS_PRISONER_ID, userId);
        cv.put(SurveyEntry.RESULTS_COLUMN_VISIT_NUMBER, visitNumber);
        cv.put(SurveyEntry.RESULTS_JSON, results);
        cv.put(SurveyEntry.RESULTS_COLUMN_ASSESSMENT_FLAG, "COMPLETED");
        cv.put(SurveyEntry.RESULTS_COLUMN_FLAG, "dirty");
        //   getContentResolver().insert(SurveyEntry.TABLE_RESULTS_CONTENT_URI, cv);
        long _id = getContentResolver().update(SurveyEntry.TABLE_RESULTS_CONTENT_URI, cv, selection, selectionArgs);

        Log.d(LOG_TAG, "method: saveResultsToDb, content values = " + cv.toString());

        if (_id == 0) {
            throw new IllegalStateException();
        }
        return true;


    }


    //TODO: initloader

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

        switch (id) {
            case LOADER_ANSWERS: {
                return new SurveyTaskLoader<String>(this, args) {
                    @Nullable
                    @Override
                    public String loadInBackground() {
                        boolean isResults = args.getBoolean("isResults");
                        boolean isUpdate = args.getBoolean("isUpdate");
                        Log.d(LOG_TAG, "method: loadInBackground called : isResults = " + isResults + " isUpdate = " + isUpdate);

                        if (saveToDb(item, isUpdate))
                            return new String("Saved to Database");
                        return "Could Not Save values to Database";
                    }

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                    }
                };
            }


            case LOADER_RESULTS: {
                return new ResultTaskLoader<String>(this, args) {
                    @Nullable
                    @Override
                    public String loadInBackground() {
                        boolean isResults = args.getBoolean("isResults");
                        boolean isUpdate = args.getBoolean("isUpdate");
                        Log.d(LOG_TAG, "method: loadInBackground called : isResults = " + isResults + " isUpdate = " + isUpdate);


                        if (saveResultsToDb()) {
                            return new String("Saved Results to Database");
                        } else
                            return "Could Not save Results to Database";
                    }


                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                    }
                };

            }

            default:
                throw new IllegalStateException("unexpected call to loader");
        }
    }

    /* return new SurveyTaskLoader<String>(this*//*, prisonerId*//*, args) {
            @Nullable
            @Override
            public String loadInBackground() {
                boolean isResults = args.getBoolean("isResults");
                boolean isUpdate = args.getBoolean("isUpdate");
                Log.d(LOG_TAG, "method: loadInBackground called : isResults = " + isResults + " isUpdate = " + isUpdate);

                if (!isResults) {
                    if (saveToDb(item, isUpdate))
                        return new String("Saved to Database");
                    return "Could Not Save values to Database";
                } else {
                    if (saveResultsToDb()) {
                        return new String("Saved Results to Database");
                    } else
                        return "Could Not save Results to Database";
                }
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }
        };
    }*/

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {


        if (data != null) {
            showSnackBar((String) data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }

    @Override
    protected void onDestroy() {

       /* if (item != null && item.isEveryQuestionAnswered()) {
            saveToDb(item, false);
        }*/

        super.onDestroy();
    }
}
