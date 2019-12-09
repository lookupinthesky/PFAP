package com.example.pfa_p.Activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

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
    private boolean isModuleChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        android.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        Button button = findViewById(R.id.next_button);
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
        if (mCurrentModuleIndex < 3) {
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            sectionDetailsParent = findViewById(R.id.fragment_section_details_parent);
            sectionsListParent = findViewById(R.id.fragment_sections_list_parent);
            sectionDetailsFragment = SectionDetailsFragment.newInstance(/*DEFAULT_SECTIONS_EMPTY*/);
            sectionsListFragment = SectionsListFragment.newInstance(0/*DEFAULT_SECTIONS_EMPTY*/);
            sectionsListFragment.setData(modules.get(mCurrentModuleIndex));
            sectionsListFragment.setCurrentState(mCurrentSectionIndex, mCurrentDomainIndex);
            sectionsListFragment.setOnListItemClickListener(this);
            ft.add(R.id.fragment_section_details_parent, sectionDetailsFragment);
            ft.add(R.id.fragment_sections_list_parent, sectionsListFragment);
            ft.commit();
        } else {
            Intent intent1 = new Intent(SurveyActivity.this, ResultsActivity.class);
            startActivity(intent1);
        }
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
        List<Domain> domains = subModules.get(mCurrentSectionIndex).getDomains();
        if (mCurrentDomainIndex > domains.size() - 1 || mCurrentDomainIndex == domains.size() - 1) {
            if (mCurrentSectionIndex > subModules.size() - 1 || mCurrentSectionIndex == subModules.size() - 1) {
                if (mCurrentModuleIndex > modules.size() - 1) {
                    throw new IllegalStateException("Unexpected Request for next set");
                } else {
                    isModuleChanged = true;
                    mCurrentModuleIndex++;
                    mCurrentSectionIndex = 0;
                    mCurrentDomainIndex = 0;
                }
            } else {
                mCurrentSectionIndex++;
                mCurrentDomainIndex = 0;
            }
        } else {
            mCurrentDomainIndex++;
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
     *
     */
    public void onNextClick() {
        String prisonerId = ""; //TODO:
        int visitNumber = 1;
        boolean isUpdate;
        Module mCurrentModule = modules.get(mCurrentModuleIndex);
        item = sectionsListFragment.getCurrentItem();
        isUpdate = !sectionDetailsFragment.setEditableAnswers();
        if (!item.isEveryQuestionAnswered()) {
            showSnackBar("Please complete all the fields!");
            return;
        }
        saveToDb(item, isUpdate); //TODO: push on background thread
        calculateNext(modules);
        if (!isModuleChanged) {
            sectionsListFragment.onStateChanged(false);
        } else {
            if (mCurrentModule.getName().equals("Basic Questionnaire")) {
                Result.evaluateQuestionnaires(mCurrentModule, this);// evaluate next sections before setting data to list
            }

            if (mCurrentModuleIndex < 3) {
                int domainIndex = mCurrentModuleIndex==1?-1:0;
                sectionsListFragment.setCurrentState(0, domainIndex);
                sectionsListFragment.setData(/*modules.get(mCurrentModuleIndex).getSections()*/modules.get(mCurrentModuleIndex));
                sectionsListFragment.onStateChanged(true);
            } else {
                saveResultsToDb(prisonerId,visitNumber);
                Intent intent = new Intent(SurveyActivity.this, ResultsActivity.class);
                startActivity(intent);


            }

        }
    }

    /**
     *
     * @param item the clicked left pane item i.e. the submodule or subdomain for which the questions are being answered
     * @param isUpdate true if entered data is being updated for example after an unexpected shutdown
     * @return true if values were inserted in database, else false
     */
    private boolean saveToDb(LeftPane item, boolean isUpdate) {
        List<Question> questions;
        if (item instanceof Domain) {
            questions = ((Domain) item).getQuestions();
            insertAnswers(questions, true, isUpdate);
            return true;
        } else if (item instanceof SubModule) {
            if (!((SubModule) item).hasDomains()) {
                questions = ((SubModule) item).getQuestions();
                String userId = String.valueOf(((SubModule) item).getModule().getUser().getIdInDb());
                if (((SubModule) item).getName().equals("Basic Questionnaire")) {
                    insertAnswers(questions, true, isUpdate);
                } else {
                    insertAnswers(questions, false, isUpdate);
                    if (((SubModule) item).getIndex() == ((SubModule) item).getModule().getSections().size() - 1)

                        updateHistoryFlagInUserTable(userId);
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
     * @see com.example.pfa_p.Database.SurveyContract
     * @param userId
     */
    private void updateHistoryFlagInUserTable(String userId) {

        ContentValues cv = new ContentValues();
        cv.put(SurveyEntry.USERS_COLUMN_HISTORY_FLAG, "COMPLETED");
        getContentResolver().update(SurveyEntry.TABLE_USERS_CONTENT_URI, cv, SurveyEntry.USERS_ID + " =?", new String[]{userId});
    }

    /**
     * Shows snackbar at the bottom when user clicks next button without completing the form
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

    /**
     * Inserts the data filled by the user into local database
     * @param questionList List of questions which needs to be saved to database
     * @param isAssessment if the list is for assessment table as history and assessment tables are different
     *                     needs to marked true for basic questionnaire
     * @see com.example.pfa_p.Database.SurveyContract
     * @param isUpdate if the method is called for changing already saved values or an incomplete form
     */
    private void insertAnswers(List<Question> questionList, boolean isAssessment, boolean isUpdate) {
        Uri uri;

        for (Question question : questionList) {
            String userId = String.valueOf(question.getSubModule().getModule().getUser().getIdInDb());
            String visitNumber = String.valueOf(question.getVisitNumber());
            ContentValues cv = question.getAnswerContentValues();
            if (!isUpdate) {
                if (isAssessment)
                    uri = getContentResolver().insert(SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, cv);
                else
                    uri = getContentResolver().insert(SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, cv);
                long _id = ContentUris.parseId(uri);
                if (_id != -1)
                    question.setAnswerIdInDb(_id);
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

    private void saveResultsToDb(String prisonerId, int visitNumber){

        String results = SurveyDataSingleton.getInstance(this).getSurveyResultForInmateInJSON(prisonerId);
        ContentValues cv = new ContentValues();
        cv.put(SurveyEntry.RESULTS_PRISONER_ID, prisonerId);
        cv.put(SurveyEntry.RESULTS_COLUMN_VISIT_NUMBER, visitNumber);
        cv.put(SurveyEntry.RESULTS_JSON, results);
        cv.put(SurveyEntry.RESULTS_COLUMN_FLAG,"dirty");
        getContentResolver().insert(SurveyEntry.TABLE_RESULTS_CONTENT_URI,cv);
    }


    //TODO: initloader

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new SurveyTaskLoader<String>(this/*, prisonerId*/) {
            @Nullable
            @Override
            public String loadInBackground() {
                if (saveToDb(item, false))
                    return new String("Saved to Database");
                return "Could Not Save values to Database";
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
            }
        };
    }

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

        if (item != null && item.isEveryQuestionAnswered()) {
            saveToDb(item, false);
        }

        super.onDestroy();
    }
}
