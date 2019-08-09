package com.example.pfa_p.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.LeftPane;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.RightPane;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeftPaneAdapter extends RecyclerView.Adapter<LeftPaneAdapter.LeftPaneViewHolder> {


    public class LeftPaneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Nullable
        @BindView(R.id.left_pane_item)
        TextView tv;

        @Nullable
        @BindView(R.id.header_name)
        TextView headerName;

        public LeftPaneViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(LeftPane item) {

            if (tv != null && item instanceof Domain) {
                tv.setText(((Domain) item).getName());
                tv.setOnClickListener(this);

            } else if (headerName != null && item instanceof SubModule) {
                headerName.setText(((SubModule) item).getName());
                headerName.setOnClickListener(this);
            }


        }
        @Override
        public void onClick(View view) {

        }

    }





    List<Module> modules;
    List<LeftPane> leftPaneList;
    LeftPaneClickListener mListener;

    public LeftPaneAdapter(List<Module> modules) {

        //this.modules = modules;
        createLeftPaneList(modules, 0);
    }

    public void setLeftPaneClickListener(LeftPaneClickListener mListener) {
        this.mListener = mListener;
    }

    private void createLeftPaneList(List<Module> data, int position) {

        leftPaneList = new ArrayList<>();
        List<SubModule> sections = data.get(position).getSections();
        List<Domain> domains;

        for (int i = 0; i < sections.size(); i++) {
            leftPaneList.add(sections.get(i));
            domains = sections.get(i).getDomains();
            leftPaneList.addAll(domains);
        }

    }


    @NonNull
    @Override
    public LeftPaneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == R.layout.header_view_left_pane) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_view_left_pane, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_left_pane, parent, false);
        }

        return new LeftPaneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeftPaneViewHolder holder, int position) {

        holder.bind(leftPaneList.get(position));


        /*LeftPane item = leftPaneList.get(position);
        TextView tv = (TextView) holder.itemView;
        if (item instanceof SubModule) {
            tv.setText(((SubModule) item).getName());
        } else if (item instanceof Domain) {
            tv.setText(((Domain) item).getName());
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return leftPaneList.size();
    }


    @Override
    public int getItemViewType(int position) {

        return leftPaneList.get(position) instanceof SubModule ? (R.layout.header_view_left_pane) : R.layout.item_view_left_pane;

    }

public interface LeftPaneClickListener {

    void onHeaderClick();

    void onItemClick();

}
}
