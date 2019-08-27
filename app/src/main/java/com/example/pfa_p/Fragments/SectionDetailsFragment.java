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
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.LeftPane;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.RightPane;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionDetailsFragment extends Fragment {


    List<Module> modules;
    private int moduleNumber;
    int sectionNumber;
    boolean isDataSet = false;

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
       // setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_right_pane, container, false);
    }


    private boolean ifEmptyFields() {
        return false;
    }

    QuestionsAdapter adapter;

    public void createLayout() {
        if (ifEmptyFields()) {
            showDialog();
        } else {
            /*modules = SurveyDataSingleton.getInstance(context).getSurveyData();
            Module module = modules.get(moduleNumber);
            SubModule subModule = module.getSections().get(sectionNumber);
            List<Question> questions = subModule.getQuestions();*/
      //      adapter = new QuestionsAdapter(rightPaneList);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            parent.setLayoutManager(layoutManager);
            //   parent.setAdapter(adapter);

        }
    }

    public void setData(LeftPane item) {
        this.item = item;
        createRightPaneList();
        adapter = new QuestionsAdapter(rightPaneList);
        parent.setAdapter(adapter);
       /* if (adapter == null) {

        } else {
            adapter.setData(rightPaneList);
        }*/
        /*if(!isDataSet){
            parent.setAdapter(adapter);
            isDataSet = true;
        }*/


    }
    public boolean setEditableAnswers() {
        String[] array = adapter.getEditableAnswers();

        if(Arrays.equals(array,getOriginalArray()))
            return false;

        for (int i = 0; i < rightPaneList.size(); i++) {
            if (array[i] == null)
                continue;
            ((Question) rightPaneList.get(i)).setAnswer(array[i], false);
        }
        return  true;
    }

     // return   adapter.getEditableAnswers();


    public void setDataWithAnswers(LeftPane item) {
        this.item = item;
        createRightPaneList();
        adapter = new QuestionsAdapter(rightPaneList);
        parent.setAdapter(adapter);
       /* if (adapter == null) {

        } else {
            adapter.setData(rightPaneList);
        }*/
        setAnswersTwo();

    }

    private void showDialog() {


    }


    public interface OnNextClickListener {

        void onNextClick();
    }

    OnNextClickListener mListener;

    /*private void onNextClick() {

        if (item.getFilledValue() < item.getMaxValue()) {
            showDialog();
        } else {
            mListener.onNextClick();
        }

    }*/

    public void setOnNextClickListener(OnNextClickListener mListener) {
        this.mListener = mListener;
    }

    List<RightPane> rightPaneList = new ArrayList<>();

    private void createRightPaneList() {

        rightPaneList = new ArrayList<>();
        if (item instanceof Domain) {
            questions = ((Domain) item).getQuestions();
        } else if (item instanceof SubModule) {
            questions = ((SubModule) item).getQuestions();
        }
        /*QuestionHeader header = new QuestionHeader();
        rightPaneList.add(header);*/
        rightPaneList.addAll(questions);

    }

    List<Question> questions;


    private void setAnswersTwo() {

        String[] answersArray = adapter.getEditableAnswers();

        for (int i = 0; i < questions.size(); i++) {
            answersArray[i] = questions.get(i).getAnswer();
        }

    }

    private String[] getOriginalArray(){

        String [] originalArray = new String[questions.size()];
        for (int i = 0; i < questions.size(); i++) {
            originalArray[i] = questions.get(i).getAnswer();
        }
        return originalArray;
    }

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


    RecyclerView parent;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        parent = view.findViewById(R.id.parent_list_section_details);
      //  parent.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
    //    layoutManager.setAutoMeasureEnabled(false);
        parent.setLayoutManager(layoutManager);
     //   createLayout();

        //     createLayout(0, 0);


    }

    public SectionDetailsFragment() {
    }


}


