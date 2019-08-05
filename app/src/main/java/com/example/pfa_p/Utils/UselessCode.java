package com.example.pfa_p.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.pfa_p.Model.AnswerOptions;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UselessCode {


    /*public class ListViewHolder extends SurveyViewHolder {

        @BindView(R.id.question)
        TextView questionText;
        @BindView(R.id.serial_number)
        TextView serialNumber;

        @BindView(R.id.option_1)
        RadioButton option1;
        @BindView(R.id.option_2)
        RadioButton option2;
        @BindView(R.id.option_3)
        RadioButton option_3;
        @BindView(R.id.option_4)
        RadioButton option_4;
        @BindView(R.id.option_5)
        RadioButton option_5;
        @BindView(R.id.option_6)
        RadioButton option_6;
        @BindView(R.id.option_7)
        RadioButton option_7;
        @BindView(R.id.option_8)
        RadioButton option_8;
        @BindView(R.id.edittext)
        EditText editText;

        ListViewHolder(@NonNull View parent, int type) {
            super(parent, type);
        }

        @Override
        void Bind(final Question question) {
            questionText.setText(question.getQuestionName());
            serialNumber.setText(question.getSerialNumber());
            AnswerOptions options = question.getOptions();
            List<RadioButton> views = new ArrayList<>();

            views.add(option1);
            views.add(option2);
            views.add(option_3);
            views.add(option_4);
            views.add(option_5);
            views.add(option_6);
            views.add(option_7);
            views.add(option_8);

            if (options.getAnswerType() == TYPE_EDIT) {
                questionText.setText(question.getQuestionName());
                serialNumber.setText(question.getSerialNumber());
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        if (!charSequence.equals("")) {

                            finalArray.setAnswer(charSequence);
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

            }

            int i = 0;

            while (i < options.getOptions().size()) {

                views.get(i).setText(options.getOptions().get(i));
                views.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    }
                });
            }

        }

    }*/

    void onCreateViewHolder() {
  /*  View view;
    if (viewType != R.layout.header_view_questions) {

        switch (viewType) {
            case R.layout.list_item_edittext: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_edittext, parent, false);
            }
            case R.layout.list_item_2_options: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_2_options, parent, false);
            }
            case R.layout.list_item_3_options: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_3_options, parent, false);
            }
            case R.layout.list_item_4_options: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_4_options, parent, false);
            }
            case R.layout.list_item_5_options: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_5_options, parent, false);
            }
            case R.layout.list_item_6_options: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_6_options, parent, false);
            }
            case R.layout.list_item_7_options: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_7_options, parent, false);
            }
            case R.layout.list_item_8_options: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_edittext, parent, false);
            }
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_8_options, parent, false);
        }
        return new ListViewHolder(view, viewType);
    } else {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view_questions, parent, false);
        return new HeaderViewHolder(view, viewType);
    }

}*/
    }
}
