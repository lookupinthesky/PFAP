package com.example.pfa_p.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pfa_p.R;
import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

public class ActivityResultsNew extends AppCompatActivity {
    List<PieEntry> values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_new);


      /*  PieChart pieChart = findViewById(R.id.results_piechart);
        values= new ArrayList<>();
        values.add(new PieEntry())*/
    }

    /*private void createPieEntries(List<SubModule> subModules){

        for(SubModule subModule: subModules){

            if (subModule.isPresent()) {
                List<Domain> domains = subModule.getDomains();
                for (Domain domain : domains) {
                    Result result = domain.getResult();
                    Bar bar = new Bar(this, result.getMaxResultValue(), result.getResultValueActual(), result.getNameForResults(), result.getResultText());
                    details.addView(bar.getView());
                    if (domain.getDespondency()) {
                        Bar bar1 = new Bar(this, 20, result.getDespondencyValue(), result.getDespondencyText(), result.getDespondencyResultText());
                        details.addView(bar1.getView());
                    }
                }

                Result result = subModule.getResult();

                heading.setText(subModule.getName());
                resultView.setText(result.getResultText());
            }


        }*/


    }

