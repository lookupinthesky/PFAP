package com.example.pfa_p.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.SurveyViewHolder> {

    private final String LOG_TAG = QuestionsAdapter.class.getName();

    private List<Question> questions;
    private int moduleIndex;
    private int sectionIndex;

    public QuestionsAdapter(Context context, List<Question> objects, int moduleIndex, int sectionIndex) {

        //TODO: call to super????????????
        super();
        this.questions = objects;
        this.moduleIndex = moduleIndex;
        this.sectionIndex = sectionIndex;
        createRightPaneList();

    }

    public void setData(List<RightPane> data){

        this.rightPaneList = data;
        notifyDataSetChanged();
    }

    static abstract class SurveyViewHolder extends RecyclerView.ViewHolder {

        // @BindView(R.id.question_text)
        TextView questionText;

        SurveyViewHolder(@NonNull View parent) {
            super(parent);
        }

        abstract void bind(RightPane item);
    }

    public class EditableItemViewHolder extends SurveyViewHolder {

        //     @BindView(R.id.option_editable)
      public  EditText editText;
        List<View> views;
        View parent;
        Question question;

        EditableItemViewHolder(@NonNull View parent) {
            super(parent);
            this.parent = parent;
            questionText = parent.findViewById(R.id.question_text);
            //       ButterKnife.bind(this,parent);
        }

        @Override
        void bind(RightPane item) {
            Question question = (Question) item;
            questionText.setText(question.getQuestionName());
            views = bindViewsToId(parent, question.getOptions().getNumberOfOptions());
            editText = (EditText) views.get(0);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    question.setAnswer(charSequence.toString(), moduleIndex == 1);

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

  public  class TwoOptionsViewHolder extends SurveyViewHolder implements RadioGridGroup.OnCheckedChangeListener {

        /*@Nullable
        @BindView(R.id.option_one)
        RadioButton optionOne;
        @Nullable
        @BindView(R.id.option_two)
        RadioButton optionTwo;*/
        /*@Nullable
        @BindView(R.id.options_radioGroup)*/
     public   RadioGridGroup options;
        List<View> views;
        View parent;
        Question question;

        TwoOptionsViewHolder(@NonNull View parent) {
            super(parent);
            this.parent = parent;
            options = parent.findViewById(R.id.options_radioGroup);
            questionText = parent.findViewById(R.id.question_text);
            //     ButterKnife.bind(this,parent);
        }


        @Override
        void bind(RightPane item) {
            this.question = (Question) item;
            questionText.setText(question.getQuestionName());
            /*optionOne.setText(question.getOptions().getOptions().get(0));
            optionTwo.setText(question.getOptions().getOptions().get(1));*/

            Question question = (Question) item;
            views = bindViewsToId(parent, question.getOptions().getNumberOfOptions());
            //       Collections.sort(views, Collections.reverseOrder());
            for (int i = 0; i < views.size(); i++) {
                ((RadioButton) views.get(i)).setText(question.getOptions().getOptions().get(i));
            }
            // ((RadioButton)(views.get(views.size()-1))).setText(question.getOptions().getOptions().get(views.size()-1));


            options.setOnCheckedChangeListener(this);
            /*options.setOnCheckedChangeListener(new RadioGridGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGridGroup group, int checkedId) {
                    RadioButton button = group.findViewById(checkedId);
                    int index = group.indexOfChild(button);
                    question.setAnswer(index);
                }
            });*/
        }

        @Override
        public void onCheckedChanged(RadioGridGroup group, int checkedId) {
            RadioButton button = group.findViewById(checkedId);
            int index = group.indexOfChild(button);
            // boolean isAssessment = question.getSubModule().getModule();
            question.setAnswer(button.getText().toString(), moduleIndex == 1);
        }
    }

    private List<View> bindViewsToId(View parent, int numberOfOptions) {

        List<View> views = new ArrayList<>();
        Log.d("QuestionsAdapter", "methodcall: onBindViewWithId, number of options = " + numberOfOptions);
        switch (numberOfOptions) {

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
        return views;
    }

    class ThreeOptionsViewHolder extends TwoOptionsViewHolder {
       /* @Nullable
        @BindView(R.id.option_three)
        RadioButton optionThree;*/

        ThreeOptionsViewHolder(@NonNull View parent) {
            super(parent);
            //       ButterKnife.bind(this,parent);

        }

        @Override
        void bind(RightPane item) {
            super.bind(item);
            /*Question question = (Question)item ;
            views = bindViewsToId(parent,question.getOptions().getNumberOfOptions());
            Collections.sort(views, Collections.reverseOrder());
            ((RadioButton)(views.get(views.size()-1))).setText(question.getOptions().getOptions().get(views.size()-1));
            super.bind(item);
            optionThree.setText(question.getOptions().getOptions().get(2));*/
        }
    }

    class FourOptionsViewHolder extends ThreeOptionsViewHolder {
        /*@Nullable
        @BindView(R.id.option_four)
        RadioButton optionFour;*/

        FourOptionsViewHolder(@NonNull View parent) {
            super(parent);
            //    ButterKnife.bind(this,parent);
        }

        @Override
        void bind(RightPane item) {
            //Question question = (Question)item;
            super.bind(item);
            //  optionFour.setText(question.getOptions().getOptions().get(3));
        }
    }

    class FiveOptionsViewHolder extends FourOptionsViewHolder {
/*

        @Nullable
        @BindView(R.id.option_five)
        RadioButton optionFive;
*/

        FiveOptionsViewHolder(@NonNull View parent) {
            super(parent);
            //  ButterKnife.bind(this,parent);
        }

        @Override
        void bind(RightPane item) {
            //   Question question = (Question)item;
            super.bind(item);
            //  optionFive.setText(question.getOptions().getOptions().get(4));

        }
    }

    class SixOptionsViewHolder extends FiveOptionsViewHolder {

       /* @Nullable
        @BindView(R.id.option_six)
        RadioButton optionSix;*/

        SixOptionsViewHolder(@NonNull View parent) {
            super(parent);
            //         ButterKnife.bind(this,parent);
        }

        @Override
        void bind(RightPane item) {
            //          Question question = (Question)item;
            super.bind(item);
            //         optionSix.setText(question.getOptions().getOptions().get(5));
        }
    }

    class SevenOptionsViewHolder extends SixOptionsViewHolder {

     /*   @Nullable
        @BindView(R.id.option_seven)
        RadioButton optionSeven;*/

        SevenOptionsViewHolder(@NonNull View parent) {
            super(parent);
            //        ButterKnife.bind(this,parent);
        }

        @Override
        void bind(RightPane item) {
            //       Question question = (Question)item;
            super.bind(item);
            //          optionSeven.setText(question.getOptions().getOptions().get(6));
        }
    }

    public class EightOptionsViewHolder extends SevenOptionsViewHolder {

       /* @Nullable
        @BindView(R.id.option_eight)
        RadioButton optionEight;*/

        EightOptionsViewHolder(@NonNull View parent) {
            super(parent);
            //          ButterKnife.bind(this,parent);
        }

        @Override
        void bind(RightPane item) {
//            Question question = (Question)item;
            super.bind(item);
            //          optionEight.setText(question.getOptions().getOptions().get(7));
        }
    }


    public class HeaderViewHolder extends QuestionsAdapter.SurveyViewHolder {

        public HeaderViewHolder(@NonNull View parent) {
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

        holder.bind(rightPaneList.get(position));
        Log.d(LOG_TAG, "method call: onBindViewHolder, position = " + position);
    }

    @Override
    public int getItemCount() {
        return rightPaneList.size();
    }

    private List<RightPane> rightPaneList;

    private void createRightPaneList() {

        rightPaneList = new ArrayList<>();

        QuestionHeader header = new QuestionHeader();

        rightPaneList.add(header);
        rightPaneList.addAll(questions);
    }

    public int getItemViewType(int position) {


        if (rightPaneList.get(position) instanceof QuestionHeader) {
            return R.layout.header_view_questions;
        } else {
            // for questions
            switch (questions.get(position - 1).getOptions().getNumberOfOptions()) {

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

    }



/*    @Override
    public int getItemViewType(int position) {
        return getItem(position).getOptions().getNumberOfOptions();
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    private View getInflatedLayoutForType(int type) {
        if (type == TWO_OPTIONS) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_item_2_options, null);
        } else if (type == THREE_OPTIONS) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_item_3_options, null);
        } else if (type == FOUR_OPTIONS) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_item_4_options, null);
        } else if (type == FIVE_OPTIONS) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_item_5_options, null);
        } else if (type == SIX_OPTIONS) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_item_6_options, null);
        } else if (type == SEVEN_OPTIONS) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_item_7_options, null);
        } else if (type == EIGHT_OPTIONS) {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_item_8_options, null);
        } else {
            return LayoutInflater.from(getContext()).inflate(R.layout.list_item_edittext, null);
        }
    }*/
}
