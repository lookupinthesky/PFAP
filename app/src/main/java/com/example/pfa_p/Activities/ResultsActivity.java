package com.example.pfa_p.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Adapter.ResultsAdapter;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {


    RecyclerView parent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        parent = findViewById(R.id.results_list);

        parent.setLayoutManager(new GridLayoutManager(this, 2));

    }


    List<SubModule> list = new ArrayList<>();


    public void setDataAndInvalidate(List<SubModule> data) {

        for (SubModule subModule : data) {
            if (subModule.isPresent()) {
                list.add(subModule);
            }
        }

        RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> adapter = new ResultsAdapter(list);
        parent.setAdapter(adapter);

    }


}
