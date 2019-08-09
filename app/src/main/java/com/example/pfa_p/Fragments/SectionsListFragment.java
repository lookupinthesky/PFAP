package com.example.pfa_p.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Activities.MainActivity;
import com.example.pfa_p.Activities.SurveyActivity;
import com.example.pfa_p.Adapter.LeftPaneAdapter;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;

import java.util.ArrayList;
import java.util.List;


import com.example.pfa_p.SurveyDataSingleton;

public class SectionsListFragment extends Fragment {

    Context context;
    List<Module> modules;
    private int moduleNumber;
    private OnListItemClickListener mListener;
    private RecyclerView parent;

    public static final String LOG_TAG = SurveyActivity.class.getName();


   /* public SectionsListFragment(int moduleNumber) {
        this.moduleNumber = moduleNumber;
    }*/

    public void setOnListItemClickListener(OnListItemClickListener mListener) {
        this.mListener = mListener;
    }

    public void createLayout(int moduleNumber) {
    }

    public static SectionsListFragment newInstance(int moduleNumber) {
        Bundle bundle = new Bundle();
        bundle.putInt("module_number", moduleNumber);
        SectionsListFragment sectionsListFragment = new SectionsListFragment();
        sectionsListFragment.setArguments(bundle);
        return sectionsListFragment;
    }
   /* private void readBundle(Bundle bundle) {

        this.moduleNumber = bundle.getInt("module_number");
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.master_sections_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void setDesignParams(TextView textView) {

    }

    public void clickNextItem(int moduleNumber, int sectionNumber) {
        if (moduleNumber == this.moduleNumber) {
            if (modules.get(moduleNumber).getNumberOfSections() < sectionNumber) {
                TextView tv = (TextView) parent.getChildAt(sectionNumber + 1);
                tv.performClick();
            } else {
                mListener.onListItemClick(moduleNumber, sectionNumber);
            }
        } else {
            Log.d(LOG_TAG, "Module Number Mismatch");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        moduleNumber = getArguments().getInt("module_number");

        parent = view.findViewById(R.id.parent_sections_list);
        modules = SurveyDataSingleton.getInstance(context).getSurveyData();
        //Module module = modules.get(moduleNumber);
        RecyclerView.Adapter<LeftPaneAdapter.LeftPaneViewHolder> adapter = new LeftPaneAdapter(modules);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        parent.setLayoutManager(layoutManager);
        parent.setAdapter(adapter);





        /*for (int i = 0; i < module.getNumberOfSections(); i++) {
            TextView tv = new TextView(context);
            tv.setText(module.getSections().get(i).getName());
            setDesignParams(tv);
            parent.addView(tv, i);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int sectionNumber = parent.indexOfChild(view);
                    mListener.onListItemClick(moduleNumber, sectionNumber);
                }
            });
        }*/
    }


    public interface OnListItemClickListener {
        void onListItemClick(int moduleNumber, int sectionNumber);
    }


}
