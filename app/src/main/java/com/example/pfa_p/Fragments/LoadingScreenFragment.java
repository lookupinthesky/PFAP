package com.example.pfa_p.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pfa_p.R;
import com.victor.loading.book.BookLoading;

public class LoadingScreenFragment extends Fragment {

    StartSurveyListener mListener;
    Button startButton;
  //  NumberProgressBar progressBar;
    public static final String LOG_TAG = LoadingScreenFragment.class.getName();
    private BookLoading progressView;
    double elapsedSeconds;
    long elapsedMilliSeconds;
    long endTime;
    long startTime;
  //  int mCurrentProgress = 1000;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading_survey, container, false);
        progressView = view.findViewById(R.id.progressView);
        progressView.start();
        startTime  = SystemClock.elapsedRealtime();




//        progressBar = (NumberProgressBar)view.findViewById(R.id.progress_bar);
//        progressBar.setMax(10000);
   //     progressBar.setProgress(0);
       /* progressBar.setOnProgressBarListener(new OnProgressBarListener() {



            @Override
            public void onProgressChange(int current, int max) {
                if(current==max || current>max){
                    mListener.onLoaderFinished();
                    Log.d(LOG_TAG, "onProgressChanged Called with current = " + current + " and max = " + max);
                }
                Log.d(LOG_TAG, "onProgressChanged Called with current = " + current + " and max = " + max);
            }
        });*/

       /* startButton = view.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onLoaderFinished();
            }
        });*/
        return view;
    }

    public void receiveProgressUpdate(int progress){
        if(progress==10000){
            endTime = SystemClock.elapsedRealtime();
            elapsedMilliSeconds  = endTime - startTime;
       //     elapsedSeconds = elapsedMilliSeconds / 1000.0;
            if(elapsedMilliSeconds>5000) {
                progressView.stop();
                mListener.onLoaderFinished();
            } else{
           final Handler  handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressView.stop();
                        mListener.onLoaderFinished();
                    }
                }, 5000-elapsedMilliSeconds);
            }
        }

    //    progressBar.setProgress(progress);

   //     int mCurrentProgress = progressBar.getProgress();

        /*ObjectAnimator progressAnim =   ObjectAnimator.ofInt(progressBar, "progress",  progress).setDuration(5000);
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

        progressAnim.start();*/




}

    public static LoadingScreenFragment getInstance(StartSurveyListener mListener) {

        LoadingScreenFragment fragment = new LoadingScreenFragment();
        fragment.mListener = mListener;
        return fragment;

    }

    public interface StartSurveyListener {

        void onLoaderFinished();
    }
}