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
    int mCurrentDomainIndex ;
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
            if (item.getFilledValue() > 0) {
                sectionDetailsFragment.setDataWithAnswers(item);
            } else {
                sectionDetailsFragment.setData(item);
            }
        }
    }

    LeftPane item;

    // @Override
    public void onNextClick() {
        Module mCurrentModule = modules.get(mCurrentModuleIndex);
//        item = sectionsListFragment.getCurrentItem();

        /*if (!item.isEveryQuestionAnswered()) {
            showSnackBar("Please complete all the fields!");
            return;
        }
        saveToDb(item);*/
        calculateNext(modules); //TODO:
        if (!isModuleChanged) {
            sectionsListFragment.onStateChanged(false);
        } else {
            if (mCurrentModule.getName().equals("Basic Questionnaire")) {
                Result result = new Result();
                result.evaluateQuestionnaires(mCurrentModule, this);
            }
            sectionsListFragment.setCurrentState(0, 0);
            sectionsListFragment.setData(/*modules.get(mCurrentModuleIndex).getSections()*/modules.get(mCurrentModuleIndex));
            sectionsListFragment.onStateChanged(true);
        }
    }

    private boolean saveToDb(LeftPane item) {
        List<Question> questions;
        if (item instanceof Domain) {
            questions = ((Domain) item).getQuestions();


            insertAnswers(questions);
            return true;
        } else if (item instanceof SubModule) {
            if (!((SubModule) item).hasDomains()) {
                questions = ((SubModule) item).getQuestions();
                insertAnswers(questions);
                return true;

            } else {
                throw new IllegalStateException("Header cannot be inserted into DB; No Questions Found");
            }
        }
        return false;

    }

    private void showSnackBar(String display) {
     Snackbar snackBar =   Snackbar.make(sectionDetailsParent.getRootView(), display, Snackbar.LENGTH_LONG);
       /* CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                snackBar.getView().getLayoutParams();
        params.setMargins(params.leftMargin , //TODO: Show snackbar above Bottomnavigation
                params.topMargin,
                params.rightMargin ,
                params.bottomMargin + 48);

        snackBar.getView().setLayoutParams(params);*/
        snackBar.show();
    }


    private void insertAnswers(List<Question> questionList) {
        for (Question question : questionList) {
            ContentValues cv = question.getAnswerContentValues();
            Uri uri = getContentResolver().insert(SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, cv);
            long _id = ContentUris.parseId(uri);
            if (_id != -1)
                question.setAnswerIdInDb(_id);
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
                if (saveToDb(item))
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

        if (item.isEveryQuestionAnswered()) {
            saveToDb(item);
        }

        super.onDestroy();
    }
}
