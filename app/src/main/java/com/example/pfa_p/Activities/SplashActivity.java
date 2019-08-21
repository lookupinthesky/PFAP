package com.example.pfa_p.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout_three);
        new Thread(new Runnable(){
            @Override
            public void run() {
                SurveyDataSingleton.getInstance(SplashActivity.this);
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(mainIntent);
                finish();
            }
        } ).start();
    }
    }

