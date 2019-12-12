package com.example.pfa_p.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Model.User;
import com.example.pfa_p.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListAdapter.DashboardListViewHolder> {


    List<User> data = new ArrayList<>();


    public DashboardListAdapter(List<User> data){

        this.data = data;

    }





    @NonNull
    @Override
    public DashboardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_item, parent, false);

        return new DashboardListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DashboardListViewHolder holder, int position) {

        holder.bind(data.get(position));

    }

    @Override
    public int getItemCount() {
        if(data.size()<10)
            return data.size();
        else
            return 10;
    }



    public class DashboardListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView prisonerId;

        TextView volunteerId;

        TextView timeStamp;

        TextView status;

        TextView action;



        public DashboardListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }

        void bind(User user){

            prisonerId.setText(user.getPrisonerId());
            volunteerId.setText(user.getVolunteerId());
            timeStamp.setText(user.getTimeStamp());
            status.setText(user.getStatus());
            action.setText(user.getAction());
        }



    }

    public class RecentActivityListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RecentActivityListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
