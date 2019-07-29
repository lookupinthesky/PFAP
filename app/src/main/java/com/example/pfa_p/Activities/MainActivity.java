package com.example.pfa_p.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.pfa_p.Fragments.SectionsListFragment;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurveyDataSingleton surveyData = SurveyDataSingleton.getInstance(this);

        ArrayList<Module> modules = surveyData.getModules();
        Log.d(MainActivity.class.getName(), modules.toString());


        // not to be done in the mainactivity but some other activity to be created later.
/*        SectionsListFragment fragment1 = new SectionsListFragment(1);
        fragment1.setOnListItemClickListener(new SectionsListFragment.OnListItemClickListener() {
            @Override
            public void onListItemClick(int sectionNumber) {
                //get section number
                //open the corresponding details fragment
            }
        });*/
    }


}
