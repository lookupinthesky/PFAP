package com.example.pfa_p.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Model.Question;
import com.example.pfa_p.Model.QuestionHeader;
import com.example.pfa_p.Model.RightPane;
import com.example.pfa_p.R;
import com.example.pfa_p.Utils.RadioGridGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.SurveyViewHolder> {


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


    static abstract class SurveyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.question_text)
        TextView questionText;

        SurveyViewHolder(@NonNull View parent) {
            super(parent);
        }

        abstract void bind(Question question);
    }

    public class EditableItemViewHolder extends SurveyViewHolder {

        @BindView(R.id.option_editable)
        EditText editText;

        EditableItemViewHolder(@NonNull View parent) {
            super(parent);
            ButterKnife.bind(this,parent);
        }

        @Override
        void bind(final Question question) {
            questionText.setText(question.getQuestionName());
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

    class TwoOptionsViewHolder extends SurveyViewHolder implements RadioGridGroup.OnCheckedChangeListener {

        @BindView(R.id.option_one)
        RadioButton optionOne;
        @BindView(R.id.option_two)
        RadioButton optionTwo;
        @BindView(R.id.options_radioGroup)
        RadioGridGroup options;

        TwoOptionsViewHolder(@NonNull View parent) {
            super(parent);
            ButterKnife.bind(this,parent);
        }

        Question question;

        @Override
        void bind(Question question) {
            this.question = question;
            questionText.setText(question.getQuestionName());
            optionOne.setText(question.getOptions().getOptions().get(0));
            optionTwo.setText(question.getOptions().getOptions().get(1));
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
            question.setAnswer(button.getText().toString(), moduleIndex==1);
        }
    }

    class ThreeOptionsViewHolder extends TwoOptionsViewHolder {
        @BindView(R.id.option_three)
        RadioButton optionThree;

        ThreeOptionsViewHolder(@NonNull View parent) {
            super(parent);
            ButterKnife.bind(this,parent);
        }

        @Override
        void bind(Question question) {
            super.bind(question);
            optionThree.setText(question.getOptions().getOptions().get(2));
        }
    }

    class FourOptionsViewHolder extends ThreeOptionsViewHolder {
        @BindView(R.id.option_four)
        RadioButton optionFour;

        FourOptionsViewHolder(@NonNull View parent) {
            super(parent);
            ButterKnife.bind(this,parent);
        }

        @Override
        void bind(Question question) {
            super.bind(question);
            optionFour.setText(question.getOptions().getOptions().get(3));
        }
    }

    class FiveOptionsViewHolder extends FourOptionsViewHolder {

        @BindView(R.id.option_five)
        RadioButton optionFive;

        FiveOptionsViewHolder(@NonNull View parent) {
            super(parent);
            ButterKnife.bind(this,parent);
        }

        @Override
        void bind(Question question) {
            super.bind(question);
            optionFive.setText(question.getOptions().getOptions().get(4));

        }
    }

    class SixOptionsViewHolder extends FiveOptionsViewHolder {

        @BindView(R.id.option_six)
        RadioButton optionSix;

        SixOptionsViewHolder(@NonNull View parent) {
            super(parent);
            ButterKnife.bind(this,parent);
        }

        @Override
        void bind(Question question) {
            super.bind(question);
            optionSix.setText(question.getOptions().getOptions().get(5));
        }
    }

    class SevenOptionsViewHolder extends SixOptionsViewHolder {

        @BindView(R.id.option_seven)
        RadioButton optionSeven;

        SevenOptionsViewHolder(@NonNull View parent) {
            super(parent);
            ButterKnife.bind(this,parent);
        }

        @Override
        void bind(Question question) {
            super.bind(question);
            optionSeven.setText(question.getOptions().getOptions().get(6));
        }
    }

    public class EightOptionsViewHolder extends SevenOptionsViewHolder {

        @BindView(R.id.option_eight)
        RadioButton optionEight;

        EightOptionsViewHolder(@NonNull View parent) {
            super(parent);
            ButterKnife.bind(this,parent);
        }

        @Override
        void bind(Question question) {
            super.bind(question);
            optionEight.setText(question.getOptions().getOptions().get(7));
        }
    }


    public class HeaderViewHolder extends QuestionsAdapter.SurveyViewHolder {

        public HeaderViewHolder(@NonNull View parent) {
            super(parent);
        }

        @Override
        void bind(Question question) {

        }


    }

    @NonNull
    @Override
    public SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);

        SurveyViewHolder holder = null;

        switch (viewType) {
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

        holder.bind((Question) rightPaneList.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
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
            switch (questions.get(position + 1).getOptions().getNumberOfOptions()) {

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
