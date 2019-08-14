package com.example.pfa_p.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.pfa_p.Database.SurveyContract.SurveyEntry;
import com.example.pfa_p.Database.SurveyTaskLoader;
import com.example.pfa_p.Fragments.SectionDetailsFragment;
import com.example.pfa_p.Fragments.SectionsListFragment;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.LeftPane;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

import java.util.List;

public class SurveyActivity extends FragmentActivity implements SectionsListFragment.OnListItemClickListener,
        SectionDetailsFragment.OnNextClickListener {


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
        setContentView(R.layout.activity_survey_dual_pane);
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
        if (mCurrentDomainIndex > domains.size() - 1) {
            if (mCurrentSectionIndex > subModules.size() - 1) {
                if (mCurrentModuleIndex > modules.size() - 1) {
                    throw new IllegalStateException("Unexpected Request for next set");
                } else {
                    isModuleChanged = true;
                    mCurrentModuleIndex++;
                }
            } else {
                mCurrentSectionIndex++;
            }
        } else {
            mCurrentDomainIndex++;
        }
    }

    @Override
    public void onListItemClick(LeftPane item) {
        /*if (sectionDetailsFragment == null) {
            createSectionDetailsFragment();
        }*//**/
        loadQuestions(item);
    }

    private void createSectionDetailsFragment() {

        FragmentManager fm = getSupportFragmentManager();

        sectionDetailsFragment = SectionDetailsFragment.newInstance(/*DEFAULT_SECTIONS_EMPTY*/);
        fm.beginTransaction().add(R.id.fragment_section_details_parent, sectionDetailsFragment).commit();
    //    fm.executePendingTransactions();
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


    @Override
    public void onNextClick() {
        calculateNext(modules); //TODO:
        if (!isModuleChanged) {
            sectionsListFragment.onStateChanged(false);
        } else {
            sectionsListFragment.setCurrentState(0, 0);
            sectionsListFragment.setData(/*modules.get(mCurrentModuleIndex).getSections()*/null);
            sectionsListFragment.onStateChanged(true);
        }
    }


}
