package com.example.pfa_p.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pfa_p.Model.AnswerOptions;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

import java.util.ArrayList;

public class SectionDetailsFragment extends Fragment {


    ArrayList<Module> modules;
    private int moduleNumber;
    int sectionNumber;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.master_section_details, container, false);


    }

    private void createLayout(View view) {

        GridLayout parent = view.findViewById(R.id.parent_grid_section_details);
        modules = SurveyDataSingleton.getInstance(context).getSurveyData();
        Module module = modules.get(moduleNumber);
        SubModule subModule = module.getSections().get(sectionNumber);
        ArrayList<Question> questions = subModule.getQuestions();


        for (int i = 0; i < subModule.getNumberOfQuestions(); i++) {

            Question question = questions.get(i);

            TextView questionBox = new TextView(context);
            parent.addView(questionBox);

            LinearLayout answerBox = new LinearLayout(context);
            parent.addView(answerBox);


            createAnswerOptionsLayout(question, answerBox);


            setDesignParams();

        }


    }


    private void createAnswerOptionsLayout(Question question, LinearLayout answerBox) {
        int answerType = question.getOptions().getAnswerType();
        switch (answerType) {
            case AnswerOptions.OPTION_YESNO: {
                createButtons(2, answerBox);
            }
            case AnswerOptions.OPTION_MCQ: {
                createButtons(question.getOptions().getNumberOfOptions(), answerBox);
            }
            case AnswerOptions.OPTION_TEXT: {
                EditText textBox = new EditText(context);
                answerBox.addView(textBox);
            }
        }
    }

    private void createButtons(int number, View parent){

        GridLayout answerBox = (GridLayout) parent;
        answerBox.setColumnCount(number+2);


        for(int i = 0; i<number; i++){

            Button button = new Button(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
            params.gravity = 77 ;

            //LinearLayout mcQBox = (LinearLayout) parent ;
        }


    }


    private void setDataToViews(View questionBox, View answerBox) {


    }

    private void setDesignParams() {


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createLayout(view);

    }

    public SectionDetailsFragment() {
    }


}


