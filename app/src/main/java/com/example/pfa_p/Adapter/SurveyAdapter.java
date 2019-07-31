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

public class SurveyAdapter extends RecyclerView.Adapter<> {


    List<Question> questions;
    int moduleIndex;
    int sectionIndex;

    public SurveyAdapter(Context context, List<Question> objects, int moduleIndex, int sectionIndex) {
        super(context, 0, objects);
        this.questions = objects;
        this.moduleIndex = moduleIndex;
        this.sectionIndex = sectionIndex;


    }


    public static abstract class SurveyViewHolder{


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
