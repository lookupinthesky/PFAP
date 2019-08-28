package com.example.pfa_p.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pfa_p.Model.User;
import com.example.pfa_p.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentActivityAdapter extends ArrayAdapter<User> implements View.OnClickListener {

    List<User> users;
    int position;
    Context mContext;
    ActionButtonsListener mListener;
    // String[]headers = {"S No.", "Prisoner Id", "Name", "No. of Visits", "History Status", "Assessment Status", "Edit", "Delete", "Results"};


    public RecentActivityAdapter(Context context, int resource, User[] objects) {
        super(context, resource, objects);
    }
   LayoutInflater inflater;
    public RecentActivityAdapter(Context context, List<User> users, ActionButtonsListener listener) {
        super(context, R.layout.recent_activity_list_item, users);
        inflater = LayoutInflater.from(context);
     //   mContext = context;
        this.users = users;
        this.mListener = listener;
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.recent_activity_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        this.position = position;
        holder.deleteButton.setOnClickListener(this);
        holder.resultsButton.setOnClickListener(this);
        holder.editButton.setOnClickListener(this);
        User user = getItem(position);
        holder.prisonerId.setText(user.getPrisonerId());
        //TODO: set user values to fields.
        return convertView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.action_delete: {
                mListener.onDeleteClick(getItem(position));
            }

            case R.id.action_edit: {
                mListener.onEditClick(getItem(position));
            }

            case R.id.results: {
                mListener.onResultsClick(getItem(position));
            }

        }
    }


    public interface ActionButtonsListener {

        void onResultsClick(User user);

        void onEditClick(User user);

        void onDeleteClick(User user);

    }



    static final class ViewHolder {

        @BindView(R.id.serial_num)
        TextView serialNumber;
        @BindView(R.id.prisonerId)
        TextView prisonerId;
        @BindView(R.id.volunteer_id)
        TextView volunteerId;
        @BindView(R.id.numberOfVisits)
        TextView numberOfVisits;
        @BindView(R.id.assessment_status)
        TextView assessmentStatus;
        @BindView(R.id.history_status)
        TextView historyStatus;
        @BindView(R.id.action_delete)
        ImageView deleteButton;
        @BindView(R.id.results)
        ImageView resultsButton;
        @BindView(R.id.action_edit)
        ImageView editButton;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}
