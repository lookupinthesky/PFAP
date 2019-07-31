package com.example.pfa_p.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.pfa_p.Fragments.SectionDetailsFragment;
import com.example.pfa_p.Fragments.SectionsListFragment;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

import java.util.ArrayList;

public class SurveyActivity extends AppCompatActivity implements SectionsListFragment.OnListItemClickListener, SectionDetailsFragment.OnNextClickListener {


    SectionDetailsFragment sectionDetailsFragment;
    SectionsListFragment sectionsListFragment;
    SurveyDataSingleton surveyData;
    ArrayList<Module> modules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        surveyData = SurveyDataSingleton.getInstance(this);
        modules = surveyData.getModules();
        FragmentManager fragmentManager = getSupportFragmentManager();
        sectionDetailsFragment = (SectionDetailsFragment) fragmentManager.findFragmentById(R.id.fragment_section_details);
        sectionsListFragment = (SectionsListFragment) fragmentManager.findFragmentById(R.id.fragment_sections_list);


    }




    @Override
    public void onListItemClick(int moduleNumber, int sectionNumber) {

        if(sectionNumber<modules.get(moduleNumber).getNumberOfSections()){


        sectionDetailsFragment.createLayout(moduleNumber, sectionNumber);}

        else  {

            sectionsListFragment.createLayout(moduleNumber + 1);
        }

    }

    @Override
    public void onNextClick(int moduleNumber, int sectionNumber) {

        sectionsListFragment.clickNextItem(moduleNumber, sectionNumber);

    }
}
