package com.example.pfa_p.Activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
       if(mCurrentModuleIndex<3) {
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
       }
       else{
           Intent intent1 = new Intent(SurveyActivity.this, ResultsActivity.class);
           startActivity(intent1);
       }
    }

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

    @Override
    public void onListItemClick(LeftPane item) {
        loadQuestions(item);
    }

    private void loadQuestions(LeftPane item) {
        if (sectionDetailsFragment != null) {
           /* if (item.isEveryQuestionAnswered()) {
               // sectionDetailsFragment.setDataWithAnswers(item);
            } else {*/
            sectionDetailsFragment.setData(item);
        }
    }


    LeftPane item;


    // @Override
    public void onNextClick() {
        boolean isUpdate;
        Module mCurrentModule = modules.get(mCurrentModuleIndex);
        item = sectionsListFragment.getCurrentItem();
        isUpdate = !sectionDetailsFragment.setEditableAnswers();
        if (!item.isEveryQuestionAnswered()) {
            showSnackBar("Please complete all the fields!");
            return;
        }
        saveToDb(item, isUpdate);
        calculateNext(modules); //TODO:
        if (!isModuleChanged) {
            sectionsListFragment.onStateChanged(false);
        } else {
            if (mCurrentModule.getName().equals("Basic Questionnaire")) {
                Result.evaluateQuestionnaires(mCurrentModule, this);
            }


            if (mCurrentModuleIndex < 3) {
                int domainIndex = mCurrentModuleIndex==1?-1:0;
                sectionsListFragment.setCurrentState(0, domainIndex);
                sectionsListFragment.setData(/*modules.get(mCurrentModuleIndex).getSections()*/modules.get(mCurrentModuleIndex));
                sectionsListFragment.onStateChanged(true);
            }
            else{

                Intent intent = new Intent(SurveyActivity.this, ResultsActivity.class);
                startActivity(intent);


            }

        }
    }

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

    private void updateHistoryFlagInUserTable(String userId) {

        ContentValues cv = new ContentValues();
        cv.put(SurveyEntry.USERS_COLUMN_HISTORY_FLAG, "COMPLETED");
        getContentResolver().update(SurveyEntry.TABLE_USERS_CONTENT_URI, cv, SurveyEntry.USERS_ID + " =?", new String[]{userId});
    }

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
