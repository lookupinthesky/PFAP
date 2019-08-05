package com.example.pfa_p.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Adapter.QuestionsAdapter;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

import java.util.ArrayList;
import java.util.List;

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

    public static SectionDetailsFragment newInstance(int sectionNumber) {

        Bundle bundle = new Bundle();

        bundle.putInt("section_number", sectionNumber);

        SectionDetailsFragment sectionDetailsFragment = new SectionDetailsFragment();

        sectionDetailsFragment.setArguments(bundle);

        return sectionDetailsFragment;

    }

    private void readBundle(Bundle bundle) {

        int sectionNumber = bundle.getInt("section_number");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.master_section_details, container, false);


    }


    private boolean ifEmptyFields() {


        return false;
    }


    public void createLayout(int moduleNumber, int sectionNumber) {


        if (ifEmptyFields()) {

            showDialog();
        } else {


            modules = SurveyDataSingleton.getInstance(context).getSurveyData();
            Module module = modules.get(moduleNumber);
            SubModule subModule = module.getSections().get(sectionNumber);
            List<Question> questions = subModule.getQuestions();
            QuestionsAdapter adapter = new QuestionsAdapter(context, questions, moduleNumber, sectionNumber);
            RecyclerView.LayoutManager mLayoutmanager = new LinearLayoutManager(context);

            parent.setAdapter(adapter);

        }
    }

    private void showDialog(){


    }


    public interface OnNextClickListener{

        void onNextClick(int moduleNumber, int sectionNumber);
    }

    OnNextClickListener mListener;

    private void onNextClick(){

        if(ifEmptyFields()){

        }
        else{


            mListener.onNextClick(moduleNumber, sectionNumber);




        }

    }

    public void setOnNextClickListener(OnNextClickListener mListener){
        this.mListener = mListener;
    }



    /*private void createAnswerOptionsLayout(Question question, LinearLayout answerBox) {
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
*/


   /* private int getModuleNumber(){}

    private int getSectionNumber(){}*/


    RecyclerView parent;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        parent = view.findViewById(R.id.parent_list_section_details);
        createLayout(0, 0);


    }

    public SectionDetailsFragment() {
    }


}


