package com.example.pfa_p.Fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.example.pfa_p.R;

import java.util.Timer;
import java.util.TimerTask;

public class SurveySchemaFragment extends Fragment {

    StartSurveyListener mListener;
    Button startButton;
    NumberProgressBar progressBar;
    public static final String LOG_TAG = SurveySchemaFragment.class.getName();
  //  int mCurrentProgress = 1000;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.survey_schema_fragment, container, false);
        progressBar = (NumberProgressBar)view.findViewById(R.id.progress_bar);
//        progressBar.setMax(10000);
   //     progressBar.setProgress(0);
        progressBar.setOnProgressBarListener(new OnProgressBarListener() {



            @Override
            public void onProgressChange(int current, int max) {
                if(current==max || current>max){
                    mListener.onStartClick();
                    Log.d(LOG_TAG, "onProgressChanged Called with current = " + current + " and max = " + max);
                }
                Log.d(LOG_TAG, "onProgressChanged Called with current = " + current + " and max = " + max);
            }
        });

       /* startButton = view.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onStartClick();
            }
        });*/
        return view;
    }

    public void receiveProgressUpdate(int progress){


    //    progressBar.setProgress(progress);

        int mCurrentProgress = progressBar.getProgress();

        ObjectAnimator progressAnim =   ObjectAnimator.ofInt(progressBar, "progress",  progress).setDuration(5000);
        progressAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                progressBar.incrementProgressBy(progress - mCurrentProgress);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        progressAnim.start();




}

    public static SurveySchemaFragment getInstance(StartSurveyListener mListener) {

        SurveySchemaFragment fragment = new SurveySchemaFragment();
        fragment.mListener = mListener;
        return fragment;

    }

    public interface StartSurveyListener {

        void onStartClick();
    }
}