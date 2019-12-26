package com.example.pfa_p.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout_three);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SurveyDataSingleton.getInstance(SplashActivity.this);

                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 0);

        //    new Thread( ).start();
    }
}

