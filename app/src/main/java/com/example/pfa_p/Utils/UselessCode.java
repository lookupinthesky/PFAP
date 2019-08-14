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



/*
        if (meanSectionScore > 0 && meanSectionScore <= firstLimit) {
            returnString =  RatingSystem.RESULT_NO_INTERVENTION_REQUIRED;
            if("questionnaire E"){
                returnString = RatingSystem.RESULT_NORMAL;
            }
        } else if (meanSectionScore > firstLimit && meanSectionScore <= secondLimit) {
            returnString =  RatingSystem.RESULT_INTERVENTION_REQUIRED;
            if("questionnaire E"){
                returnString = RatingSystem.RESULT_PERSONALITY_SLIGHTLY_ANTISOCIAL;
            }
        } else if (meanSectionScore > secondLimit && meanSectionScore <= thirdLimit) {
            returnString =  RatingSystem.RESULT_INTERVETION_REQUIRED_WITH_FOLLOW_UP;
            if("questionnaire E"){
                returnString = RatingSystem.RESULT_PERSONALITY_PROMINENT_ANTISOCIAL;
            }
        } else {
            returnString =  RatingSystem.RESULT_NEED_REFERRAL;
        }*/





    }


    /*
    private void startSurvey(String prisonerId) {
        String[] projection_users = new String[]{SurveyEntry.USERS_ID, SurveyEntry.USERS_COLUMN_INMATE_ID};
        String[] projection_history = new String[]{SurveyEntry.ANSWERS_COLUMN_QUESTION_ID,
                SurveyEntry.ANSWERS_COLUMN_SURVEY_ID,
                SurveyEntry.ANSWERS_COLUMN_USER_ID};
        String[] projection_questions = new String[]{SurveyEntry.QUESTIONS_COLUMN_NAME};
        String[] selectionArgs_users = new String[]{prisonerId};
        String selection_users = SurveyEntry.USERS_COLUMN_INMATE_ID;
        String selection_history = SurveyEntry.ANSWERS_COLUMN_USER_ID;
        String selection_question = SurveyEntry.QUESTIONS_ID;
        String[] selectionArgs_history;
        String[] selectionArgs_questions;
        long _id = -1;
        long question_id = -1;
        String question_name = "";
        Cursor cursor;
        */
/*Cursor cursor = getContentResolver().query(SurveyEntry.TABLE_USERS_CONTENT_URI, projection_users, selection_users, selectionArgs_users, null);

        if (cursor.moveToFirst()) {
            _id = cursor.getLong(cursor.getColumnIndex(SurveyEntry.USERS_ID));
        }
        cursor.close();*//*


        _id = getUserIdFromDatabase(prisonerId);

        selectionArgs_history = new String[]{String.valueOf(_id)};
        if (_id != -1) //user present in user table
        {
            cursor = getContentResolver().query(SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, projection_history, selection_history, selectionArgs_history, null);

            if (cursor.getCount() != 0 && cursor.moveToLast()) {
                SubModule submodule = getSubModuleFromDb(cursor, projection_questions, selection_history);
                Module module = submodule.getModule();

                if (module.getSections().indexOf(submodule) == module.getSections().size() - 1) {
                    // complete data - check assessments table
                    Cursor cursor3 = getContentResolver().query(SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, projection_history, selection_history, selectionArgs_history, null);

                    if (cursor3.getCount() != 0 && cursor3.moveToLast()) {
                        SubModule submodule2 = getSubModuleFromDb(cursor3, projection_questions, selection_question);
                        Module module2 = submodule2.getModule();

                        if (module2.getSections().indexOf(submodule2) < module.getSections().size() - 1) {

                            //incomplete data - start assessment from next domain
                            handleSectionsListFragment(modules.indexOf(module2));
                            startSectionDetailsFragment((module2.getSections().indexOf(submodule2)));

                        }
                    } else {

                        // start assessment from start
                        handleSectionsListFragment(2);
                        startSectionDetailsFragment(0);
                    }


                } else if (module.getSections().indexOf(submodule) < module.getSections().size() - 1) {

                    //incomplete data - start history with next section or new visit.
                    handleSectionsListFragment(0);
                    startSectionDetailsFragment(module.getSections().indexOf(submodule));
                }
            } else {

                handleSectionsListFragment(0);

                startSectionDetailsFragment(0);
                // no data - start history with first section
            }


        } else {
            insertNewUser(prisonerId);
            handleSectionsListFragment(0);
            startSectionDetailsFragment(0);
            // no data - start history with first section
        }
    }
*/

    /*private SubModule getSubModuleFromDb(Cursor cursor, String[] projection, String selection) {

        long question_id;
        question_id = cursor.getLong(cursor.getColumnIndex(SurveyEntry.ANSWERS_COLUMN_QUESTION_ID));
        cursor.close();
        String[] selectionArgs_questions = new String[]{String.valueOf(question_id)};
        Cursor cursor2 = getContentResolver().query(SurveyEntry.TABLE_QUESTIONS_CONTENT_URI, projection, selection, selectionArgs_questions, null);
        //cursor check TODO
        String question_name = cursor2.getString(cursor.getColumnIndex(SurveyEntry.QUESTIONS_COLUMN_NAME)); // should be from a global serial number
        cursor2.close();
        SubModule subModule = SubModule.getSubModule(this, question_name);
        return subModule;
    }
*/

     /*private static final int ACTION_REFRESH = 2;
    private static final int ACTION_CREATE = 1;*/

  /*  private void handleSectionsListFragment(int action) {

        //  mCurrentModuleIndex = moduleIndex;
        if (sectionsListFragment == null) {
            sectionsListFragment = SectionsListFragment.newInstance(mCurrentModuleIndex);
        } else *//* move to next module*//* {

            sectionsListFragment.setCurrentState(mCurrentSectionIndex, mCurrentDomainIndex);

            *//*Module module = modules.get(mCurrentModuleIndex);
            List<SubModule> subModules = module.getSections();
            sectionsListFragment.setData(subModules);*//*
        }
    }*/

  /*  private void handleSectionDetailsFragment() {


    }

    private void startSectionDetailsFragment(int sectionIndex) {

    }*/


 /*   private SubModule getSubModuleFromDb(Cursor cursor, String[] projection, String selection) {

        long question_id;
        question_id = cursor.getLong(cursor.getColumnIndex(SurveyEntry.ANSWERS_COLUMN_QUESTION_ID));
        cursor.close();
        String[] selectionArgs_questions = new String[]{String.valueOf(question_id)};
        Cursor cursor2 = getContentResolver().query(SurveyEntry.TABLE_QUESTIONS_CONTENT_URI, projection, selection, selectionArgs_questions, null);
        //cursor check TODO
        String question_name = cursor2.getString(cursor.getColumnIndex(SurveyEntry.QUESTIONS_COLUMN_NAME)); // should be from a global serial number
        cursor2.close();
        SubModule subModule = SubModule.getSubModule(this, question_name);
        return subModule;
    }
*/
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



}
