package com.example.pfa_p.Adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.QuestionHeader;
import com.example.pfa_p.Model.RightPane;
import com.example.pfa_p.R;
import com.example.pfa_p.Utils.RadioGridGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.SurveyViewHolder> {

    private final String LOG_TAG = QuestionsAdapter.class.getName();

    private List<Question> questions;
    private int moduleIndex;
    private int sectionIndex;
    private List<RightPane> rightPaneList;
    String[] answersArray;

    public QuestionsAdapter(List<RightPane> data) {

        //TODO: call to super????????????
        super();
        this.rightPaneList = data;
        answersArray = new String[rightPaneList.size()];
    }

    public void setData(List<RightPane> data) {

        this.rightPaneList = data;
        notifyDataSetChanged();
        answersArray = new String[rightPaneList.size()];
    }

    static abstract class SurveyViewHolder extends RecyclerView.ViewHolder {

        TextView questionText;

        SurveyViewHolder(@NonNull View parent) {
            super(parent);
        }

        abstract void bind(RightPane item);
    }


    private class CustomEditTextListener implements TextWatcher {

        //int position;
        public CustomEditTextListener(Question question) {
            this.question = question;
        }

        private Question question;

        public void setQuestion(Question question) {
            this.question = question;
        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // question.setAnswer(editable.toString(), moduleIndex == 1);
            answersArray[rightPaneList.indexOf(question)] = editable.toString(); // this is the right method to retain data
        }
    }


    public class EditableItemViewHolder extends SurveyViewHolder {

        public EditText editText;
        List<View> views;
        View parent;
        Question question;
        TextWatcher mTextWatcher;

        EditableItemViewHolder(@NonNull View parent) {
            super(parent);
            this.parent = parent;
            questionText = parent.findViewById(R.id.question_text);
            editText = parent.findViewById(R.id.option_editable);
            mTextWatcher = new CustomEditTextListener(question);

           /* new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                   *//* if (charSequence != null) {
                        question.setAnswer(charSequence.toString(), moduleIndex == 1);
                    }*//*
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    question.setAnswer(editable.toString(), moduleIndex == 1);
                    Log.d(LOG_TAG, "after textchanged for question " + question.getQuestionName() + " answer = " + editable.toString());
                }
            };*/
        }

        @Override
        void bind(RightPane item) {
            question = (Question) item;
            questionText.setText(question.getQuestionName());

            editText.removeTextChangedListener(mTextWatcher); // for view recycling
            mTextWatcher = new CustomEditTextListener(question);
            editText.addTextChangedListener(mTextWatcher);
            editText.setText(answersArray[rightPaneList.indexOf(question)]);
            editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            editText.setInputType(question.getOptions().getInputType());
            //    views = bindViewsToId(parent, question.getOptions().getNumberOfOptions());
            //    editText = (EditText) views.get(0);
           /* TextWatcher mTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    if (charSequence != null) {
                        question.setAnswer(charSequence.toString(), moduleIndex == 1);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    question.setAnswer(editable.toString(), moduleIndex == 1);
                    Log.d(LOG_TAG, "after textchanged for question " + question.getQuestionName() + " answer = " + editable.toString() );
                }
            };*/
          /*  editText.removeTextChangedListener(mTextWatcher);
            editText.setText("");
            if(question.getAnswer()!=null && question.getAnswer().length()>0){ // condition for when view recycles no data loses
                editText.removeTextChangedListener(mTextWatcher);
                editText.setText(question.getAnswer());
                Log.d(LOG_TAG, "Answer being set for question after recycling " + question.getQuestionName() + " answer = " + question.getAnswer());
                editText.addTextChangedListener(mTextWatcher);
            }*/



            /* editText.addTextChangedListener(mTextWatcher);*//*new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    if (charSequence != null) {
                        question.setAnswer(charSequence.toString(), moduleIndex == 1);
                    }
                }
                @Override
                public void afterTextChanged(Editable editable) {

                }
            });*/
        }
    }


   /* private int getInputType(Question question){

        String inputType =question.getOptions().getInputType();
        int inputTypefinal;
        switch (inputType){


            case "NUMBER":
        }



    }*/

    public String[] getEditableAnswers() {
        return answersArray;
    }

    public class TwoOptionsViewHolder extends SurveyViewHolder implements RadioGridGroup.OnCheckedChangeListener {

        public RadioGridGroup options;
        public RadioGridGroup despondency;
        public LinearLayout despondencyParent;
        List<View> views;
        View parent;
        Question question;
        int count = 0;

        TwoOptionsViewHolder(@NonNull View parent) {
            super(parent);
            this.parent = parent;
            options = parent.findViewById(R.id.options_radioGroup);
            despondencyParent = parent.findViewById(R.id.despondency_parent);
            despondency = parent.findViewById(R.id.despondency_radioGroup);
            questionText = parent.findViewById(R.id.question_text);
            Log.d(LOG_TAG, "is options null in constructor? " + options.toString() + " count number = " + count++);
        }

        @Override
        void bind(RightPane item) {
            this.question = (Question) item;
            questionText.setText(question.getQuestionName());
            Question question = (Question) item;
            views = bindViewsToId(parent, question.getOptions().getNumberOfOptions());
            for (int i = 0; i < views.size(); i++) {
                RadioButton rb = (RadioButton) views.get(i);
                rb.setText(question.getOptions().getOptions().get(i));
            }
/*if(question.hasDespondency()){

    despondency.setVisibility(View.VISIBLE);
}*/
            options.setOnCheckedChangeListener(null); //reset when view recycles
            options.clearCheck();


            if (question.getAnswer() != null) { // condition for when view recycles, no data loses
                if (question.getAnswerIndex() >= 0) {
                    options.setOnCheckedChangeListener(null);
                    options.check(views.get(question.getAnswerIndex()).getId());
                    options.setOnCheckedChangeListener(this);
                }
            }


            Log.d(LOG_TAG, "is options null in bind method ? " + options.toString() + " ; postion = " + rightPaneList.indexOf(item));
           /* RadioGridGroup.OnCheckedChangeListener mListener = new RadioGridGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGridGroup group, int checkedId) {
                    RadioButton button = (RadioButton) group.findViewById(checkedId);
                    Log.d(LOG_TAG, "checkedId = " + checkedId + " question is " + question.getQuestionName());
                    button.setChecked(true);
                    int index = group.indexOfChild(button);
                    // boolean isAssessment = question.getSubModule().getModule();
                    question.setAnswer(button.getText().toString(), moduleIndex == 1);
                }
            };*/
            options.setOnCheckedChangeListener(this);

        }

        @Override
        public void onCheckedChanged(RadioGridGroup group, int checkedId) {


            RadioButton button = (RadioButton) group.findViewById(checkedId);
            Log.d(LOG_TAG, "checkedId = " + checkedId + " question is " + question.getQuestionName());
            button.setChecked(true);
            int index = group.indexOfChild(button);
            // boolean isAssessment = question.getSubModule().getModule();
            question.setAnswer(button.getText().toString(), question.getSubModule().getModule().getIndex() != 0);

            if (question.hasDespondency()) {
                despondencyParent.setVisibility(View.VISIBLE);
                despondency.setOnCheckedChangeListener(new RadioGridGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGridGroup group, int checkedId) {
                        RadioButton despButton = group.findViewById(checkedId);
                        despButton.setChecked(true);
                        question.setDespondency(checkedId);
                    }
                });
            }


        }
    }

    private List<View> bindViewsToId(View parent, int numberOfOptions) {

        List<View> views = new ArrayList<>();
        Log.d("QuestionsAdapter", "methodcall: onBindViewWithId, number of options = " + numberOfOptions);

        switch (numberOfOptions) {
// The order is important here, case 8 must be checked before case 7 and so on
            case 8:
                AppCompatRadioButton option8 = parent.findViewById(R.id.option_eight);
                views.add(option8);
            case 7:
                AppCompatRadioButton option7 = parent.findViewById(R.id.option_seven);
                views.add(option7);
            case 6:
                AppCompatRadioButton option6 = parent.findViewById(R.id.option_six);
                views.add(option6);
            case 5:
                AppCompatRadioButton option5 = parent.findViewById(R.id.option_five);
                views.add(option5);
            case 4:
                AppCompatRadioButton option4 = parent.findViewById(R.id.option_four);
                views.add(option4);
            case 3:
                AppCompatRadioButton option3 = parent.findViewById(R.id.option_three);
                views.add(option3);
            case 2:
                AppCompatRadioButton option2 = parent.findViewById(R.id.option_two);
                AppCompatRadioButton option1 = parent.findViewById(R.id.option_one);
                views.add(option2);
                views.add(option1);
                break;
            case 0:
                EditText editText = parent.findViewById(R.id.option_editable);
                views.add(editText);
                break;
        }
        Log.d(LOG_TAG, "method call: onBindViewWithId, size of views array = " + views.size());
        Collections.reverse(views);
        return views;
    }

    class ThreeOptionsViewHolder extends TwoOptionsViewHolder {

        ThreeOptionsViewHolder(@NonNull View parent) {
            super(parent);
        }

        @Override
        void bind(RightPane item) {
            super.bind(item);
        }
    }

    class FourOptionsViewHolder extends ThreeOptionsViewHolder {

        FourOptionsViewHolder(@NonNull View parent) {
            super(parent);
        }

        @Override
        void bind(RightPane item) {
            super.bind(item);
        }
    }

    class FiveOptionsViewHolder extends FourOptionsViewHolder {

        FiveOptionsViewHolder(@NonNull View parent) {
            super(parent);
        }

        @Override
        void bind(RightPane item) {
            super.bind(item);
        }
    }

    class SixOptionsViewHolder extends FiveOptionsViewHolder {

        SixOptionsViewHolder(@NonNull View parent) {
            super(parent);
        }

        @Override
        void bind(RightPane item) {
            super.bind(item);
        }
    }

    class SevenOptionsViewHolder extends SixOptionsViewHolder {

        SevenOptionsViewHolder(@NonNull View parent) {
            super(parent);
        }

        @Override
        void bind(RightPane item) {
            super.bind(item);
        }
    }

    public class EightOptionsViewHolder extends SevenOptionsViewHolder {

        EightOptionsViewHolder(@NonNull View parent) {
            super(parent);
        }

        @Override
        void bind(RightPane item) {
            super.bind(item);
        }
    }


    public class HeaderViewHolder extends QuestionsAdapter.SurveyViewHolder {

        HeaderViewHolder(@NonNull View parent) {
            super(parent);
        }

        @Override
        void bind(RightPane item) {

        }
    }

    @NonNull
    @Override
    public SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);
        SurveyViewHolder holder = null;

        switch (viewType) {

            case (R.layout.list_item_editable):
                holder = new EditableItemViewHolder(view);
                break;
            case R.layout.list_item_two_options:
                holder = new TwoOptionsViewHolder(view);
                break;
            case R.layout.list_item_three_options:
                holder = new ThreeOptionsViewHolder(view);
                break;
            case R.layout.list_item_four_options:
                holder = new FourOptionsViewHolder(view);
                break;
            case R.layout.list_item_five_options:
                holder = new FiveOptionsViewHolder(view);
                break;
            case R.layout.list_item_six_options:
                holder = new SixOptionsViewHolder(view);
                break;
            case R.layout.list_item_seven_options:
                holder = new SevenOptionsViewHolder(view);
                break;
            case R.layout.list_item_eight_options:
                holder = new EightOptionsViewHolder(view);
                break;
            case R.layout.header_view_questions:
                holder = new HeaderViewHolder(view);
        }
        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull SurveyViewHolder holder, int position) {

        //     holder.setIsRecyclable(false);
        holder.bind(rightPaneList.get(position));
        Log.d(LOG_TAG, "method call: onBindViewHolder, position = " + position);
    }

    @Override
    public int getItemCount() {
        return rightPaneList.size();
    }

    public int getItemViewType(int position) {

        if (rightPaneList.get(position) instanceof QuestionHeader) {
            return R.layout.header_view_questions;
        } else if (rightPaneList.get(position) instanceof Question) {
            // for questions
            Question question = (Question) rightPaneList.get(position);
            switch (question.getOptions().getNumberOfOptions()) {

                case 0:
                    return R.layout.list_item_editable;
                case 2:
                    return R.layout.list_item_two_options;
                case 3:
                    return R.layout.list_item_three_options;
                case 4:
                    return R.layout.list_item_four_options;
                case 5:
                    return R.layout.list_item_five_options;
                case 6:
                    return R.layout.list_item_six_options;
                case 7:
                    return R.layout.list_item_seven_options;
                case 8:
                    return R.layout.list_item_eight_options;
                default:
                    return R.layout.list_item_editable;
            }
        }
        return -1;
    }


}
