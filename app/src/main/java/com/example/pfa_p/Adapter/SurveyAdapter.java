package com.example.pfa_p.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.Question;
import com.example.pfa_p.R;

import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {


    List<Question> questions;
    int moduleIndex;
    int sectionIndex;

    public SurveyAdapter(Context context, List<Question> objects, int moduleIndex, int sectionIndex) {

        //TODO: call to super????????????
        super();
        this.questions = objects;
        this.moduleIndex = moduleIndex;
        this.sectionIndex = sectionIndex;


    }

    @NonNull
    @Override
    public SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch(viewType){
            case R.layout.list_item_edittext:  {}
            case R.layout.list_item_2_options: {}
            case R.layout.list_item_3_options: {}
            case R.layout.list_item_4_options: {}
            case R.layout.list_item_5_options: {}
            case R.layout.list_item_6_options: {}
            case R.layout.list_item_7_options: {}
            case R.layout.list_item_8_options: {}
            case R.layout.list_header: {}

        }
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static abstract class SurveyViewHolder extends RecyclerView.ViewHolder {


        public SurveyViewHolder(@NonNull View parent, int type) {
            super(parent);

        }

        abstract void bind(Question question) ;


    }

    public class ListViewHolder extends SurveyViewHolder{

        public ListViewHolder(@NonNull View parent, int type) {
            super(parent, type);
        }

        @Override
        void bind(Question question) {

        }

    }

    public class HeaderViewHolder extends SurveyViewHolder{

        public HeaderViewHolder(@NonNull View parent, int type) {
            super(parent, type);
        }

        @Override
        void bind(Question question) {

        }
    }


    public int getItemViewType(int position){

        // for questions
        switch(questions.get(position).getOptions().getNumberOfOptions()){

            case 0:
                return R.layout.list_item_edittext;
            case 2:
                return R.layout.list_item_2_options;
            case 3:
                return R.layout.list_item_3_options;
            case 4:
                return R.layout.list_item_4_options;
            case 5:
                return R.layout.list_item_5_options;
            case 6:
                return R.layout.list_item_6_options;
            case 7:
                return R.layout.list_item_7_options;
            case 8:
                return R.layout.list_item_8_options;
            default:
                return R.layout.list_item_edittext;
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
