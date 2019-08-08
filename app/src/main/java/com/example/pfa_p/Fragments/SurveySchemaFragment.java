package com.example.pfa_p.Fragments;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pfa_p.R;

public class SurveySchemaFragment extends Fragment {

    StartSurveyListener mListener;
    Button startButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.survey_schema_fragment, container, false);
        startButton = view.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onStartClick();
            }
        });
        return view;
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