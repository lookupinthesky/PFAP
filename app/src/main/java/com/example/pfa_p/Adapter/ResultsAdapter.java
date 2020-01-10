package com.example.pfa_p.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Activities.ResultsActivity;
import com.example.pfa_p.Model.Bar;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.Result;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {
    public ResultsAdapter(List<SubModule> list) {
        super();
        this.data = list;
    }

    List<SubModule> data;

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ResultsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_results, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {

        holder.onBind(data.get(position));

    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_view_results;


     /*   LeftPane item = leftPaneList.get(position);

        if (item instanceof SubModule && ((SubModule) item).hasDomains()) {
            return R.layout.header_view_left_pane;
        } else {//if(item instanceof SubModule && !((SubModule) item).hasDomains() || item instanceof Domain){

        }*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ResultsViewHolder extends RecyclerView.ViewHolder {

        TextView heading;
        TextView resultView;
        LinearLayout details;
        View view;

        public ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            heading = itemView.findViewById(R.id.item_heading);
            resultView = itemView.findViewById(R.id.item_result);
            details = itemView.findViewById(R.id.bars_container);

        }

        void onBind(SubModule subModule) {

            //   createChart(itemView.getContext(),subModule);

            if (subModule.isPresent()) {
                List<Domain> domains = subModule.getDomains();
                for (Domain domain : domains) {
                    Result result = domain.getResult();
                    Bar bar = new Bar(itemView.getContext(), result.getMaxResultValue(), result.getResultValueActual(), result.getNameForResults(), result.getResultText());
                    details.addView(bar.getView());
                    if (domain.getDespondency()) {
                        Bar bar1 = new Bar(itemView.getContext(), 20, result.getDespondencyValue(), result.getDespondencyText(), result.getDespondencyResultText());
                        details.addView(bar1.getView());
                        Log.d(ResultsActivity.class.getName(), "Domain Result is: " + result.getNameForResults() + " = " + result.getResultValueActual() + " = " + result.getResultText());

                    }
                }

                Result result = subModule.getResult();

                heading.setText(subModule.getName());
                resultView.setText(result.getResultText());
                Log.d(ResultsActivity.class.getName(), "Section Result is: " + subModule.getName() + ": " + result.getResultText());
                }
        }
    }







    void createChart(Context context, SubModule subModule) {

        HorizontalBarChart chart = new HorizontalBarChart(context);
        chart.getXAxis().setValueFormatter(new LabelValueFormatter(subModule));
        BarData data = new BarData(createBarDataSet(subModule));
        chart.setData(data);
    }

    List<BarEntry> createBarEntry(SubModule subModule) {

        List<Domain> domains = subModule.getDomains();
        List<BarEntry> barEntries = new ArrayList<>();

        int j = 0;
        for (int i = 0; i < domains.size(); i++) {
            Domain domain = domains.get(i);
            barEntries.add(new BarEntry(domain.getResult().getResultValueActual(), j));
            if (domain.getDespondency()) {
                barEntries.add(new BarEntry(domain.getResult().getDespondencyValue(), j + 1));
            }
            j++;

        }
        return barEntries;
    }


    BarDataSet createBarDataSet(SubModule subModule) {


        List<BarEntry> barEntries = createBarEntry(subModule);
        return new BarDataSet(barEntries, "Score");
    }

    class LabelValueFormatter extends ValueFormatter {

        SubModule subModule;

        private final String[] labels;

        public LabelValueFormatter(SubModule subModule) {
         //   this.subModule = subModule;
         List<String> labelsArray =  createLabels(subModule);

            this.labels = labelsArray.toArray(new String[0]);
        }

        ArrayList<String> createLabels(SubModule subModule) {
            ArrayList<String> labelsArray = new ArrayList<>();
            List<Domain> domains = subModule.getDomains();
            for (int i = 0; i < domains.size(); i++) {
                Domain domain = domains.get(i);
                labelsArray.add(domain.getResult().getNameForResults());
                if (domain.getDespondency()) {
                    labelsArray.add(domain.getResult().getDespondencyText());
                }
            }
            return labelsArray;
        }


        @Override
        public String getFormattedValue(float value) {
            return labels[(int)value];
        }
    }


}
