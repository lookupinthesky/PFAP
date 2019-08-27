package com.example.pfa_p.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Model.Bar;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.Result;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;

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

        return new ResultsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view_left_pane, parent, false));
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

            List<Domain> domains = subModule.getDomains();
            for (Domain domain : domains) {
                Result result = domain.getResult();
                Bar bar = new Bar(itemView.getContext(), result.getMaxResultValue(), result.getResultValueActual(), result.getNameForResults(), result.getResultText());
            }

            Result result = subModule.getResult();

            heading.setText(subModule.getName());
            resultView.setText(result.getResultText());
        }
    }
}
