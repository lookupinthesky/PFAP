package com.example.pfa_p.Activities;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.pfa_p.Fragments.SurveySchemaFragment;
import com.example.pfa_p.Fragments.UserEntryFragment;
import com.example.pfa_p.R;

public class BeforeSurveyActivity extends FragmentActivity  {
    FragmentManager fm;
    FragmentTransaction ft;
    SurveySchemaFragment schemaFragment;
    UserEntryFragment userEntryFragment;
    CoordinatorLayout parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_survey);
        parent = findViewById(R.id.fragment_container);
        parent.setAlpha(0.2f);

         fm = getSupportFragmentManager();
         ft = fm.beginTransaction();


        schemaFragment = SurveySchemaFragment.getInstance(() -> {
            Intent intent = new Intent(BeforeSurveyActivity.this, SurveyActivity.class);
            startActivity(intent);

        });
        userEntryFragment = UserEntryFragment.getInstance(() -> {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, schemaFragment).addToBackStack(null).commit();


       //     ft.replace(R.id.fragment_container, schemaFragment);
            parent.setAlpha(1);
        //    ft.commit();

        });

        ft.add(R.id.fragment_container, userEntryFragment);
        ft.commit();

    }


}
