package com.example.pfa_p.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Adapter.QuestionsAdapter;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.LeftPane;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.QuestionHeader;
import com.example.pfa_p.Model.RightPane;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

import java.util.ArrayList;
import java.util.List;

public class SectionDetailsFragment extends Fragment {


    List<Module> modules;
    private int moduleNumber;
    int sectionNumber;

    LeftPane item;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    Context context;

    public static SectionDetailsFragment newInstance(/*int sectionNumber*/) {
       /* Bundle bundle = new Bundle();
        bundle.putInt("section_number", sectionNumber);*/
        SectionDetailsFragment sectionDetailsFragment = new SectionDetailsFragment();
        //      sectionDetailsFragment.setArguments(bundle);
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

    QuestionsAdapter adapter;

    public void createLayout(int moduleNumber, int sectionNumber) {
        if (ifEmptyFields()) {
            showDialog();
        } else {
            modules = SurveyDataSingleton.getInstance(context).getSurveyData();
            Module module = modules.get(moduleNumber);
            SubModule subModule = module.getSections().get(sectionNumber);
            List<Question> questions = subModule.getQuestions();
            adapter = new QuestionsAdapter(context, questions, moduleNumber, sectionNumber);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            parent.setLayoutManager(layoutManager);
            parent.setAdapter(adapter);

        }
    }

    public void setData(LeftPane item) {
        this.item = item;
        createRightPaneList();
        adapter.setData(rightPaneList);

    }


    public void setDataWithAnswers(LeftPane item) {
        this.item = item;
        createRightPaneList();
        adapter.setData(rightPaneList);
        setAnswers();
    }

    private void showDialog() {


    }


    public interface OnNextClickListener {

        void onNextClick();
    }

    OnNextClickListener mListener;

    private void onNextClick() {

        if (item.getFilledValue() < item.getMaxValue()) {
            showDialog();
        } else {
            mListener.onNextClick();
        }

    }

    public void setOnNextClickListener(OnNextClickListener mListener) {
        this.mListener = mListener;
    }

    List<RightPane> rightPaneList;

    private void createRightPaneList() {

        rightPaneList = new ArrayList<>();
        if (item instanceof Domain) {
            questions = ((Domain) item).getQuestions();
        } else if (item instanceof SubModule) {
            questions = ((SubModule) item).getQuestions();
        }
        QuestionHeader header = new QuestionHeader();
        rightPaneList.add(header);
        rightPaneList.addAll(questions);

    }

    List<Question> questions;

    private void setAnswers() {

        for (Question question : questions) {
            parent.post(new Runnable() {
                @Override
                public void run() {
                    RecyclerView.ViewHolder holder = parent.findViewHolderForAdapterPosition(rightPaneList.indexOf(question));
                    if (holder instanceof QuestionsAdapter.EditableItemViewHolder) {
                        ((QuestionsAdapter.EditableItemViewHolder) holder).editText.setText(question.getAnswer());
                    }
                    if (holder instanceof QuestionsAdapter.TwoOptionsViewHolder) {
                        ((QuestionsAdapter.TwoOptionsViewHolder) holder).options.check(question.getAnswerIndex());
                    }
                }
            });

        }
    }

   /* private void something(int position, Question question) {

        RecyclerView.ViewHolder holder = parent.findViewHolderForAdapterPosition(position);
        if (holder instanceof QuestionsAdapter.EditableItemViewHolder) {
            ((QuestionsAdapter.EditableItemViewHolder) holder).editText.setText(question.getAnswer());
        }
        if (holder instanceof QuestionsAdapter.TwoOptionsViewHolder) {
            ((QuestionsAdapter.TwoOptionsViewHolder) holder).options.check(question.getAnswerIndex());
        }

    }
*/

    //     Question question = questions.get(i);

           /* new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    something(i, );
                }
            }, 100);*/
     /*       parent.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    RecyclerView.ViewHolder holder =  parent.findViewHolderForAdapterPosition(lastPosition).itemView.performClick();

                    parent.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });


*/

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
        //     createLayout(0, 0);


    }

    public SectionDetailsFragment() {
    }


}


