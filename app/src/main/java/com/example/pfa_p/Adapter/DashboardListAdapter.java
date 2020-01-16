package com.example.pfa_p.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Model.User;
import com.example.pfa_p.R;
import com.example.pfa_p.Utils.JavaUtils;

import java.util.ArrayList;
import java.util.List;

public class DashboardListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<User> data = new ArrayList<>();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private DashboardListInterface mListener;

    public DashboardListAdapter(List<User> data, DashboardListInterface mListener) {

        this.data = data;
        this.mListener = mListener;

    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == -1;
    }

    private User getItem(int position) {
        return data.get(position /*- 1*/);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_item_new, parent, false);

            return new DashboardListViewHolder(view);

        } else if (viewType == TYPE_HEADER) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_header, parent, false);

            return new DashboardHeaderViewHolder(view);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DashboardListViewHolder) {
            ((DashboardListViewHolder) holder).bind(getItem(position));
        } else if (holder instanceof DashboardHeaderViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        if (data.size() < 11)
            return data.size() /*+ 1*/;
        else
            return data.size();
    }

    DashboardListViewHolder previous = null;
    class DashboardListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //  @BindView(R.id.prisonerId)
        TextView prisonerId;

        //   @BindView(R.id.volunteer_id)
        AppCompatTextView volunteerId;

        //    @BindView(R.id.time_stamp)
        AppCompatTextView timeStamp;

        //     @BindView(R.id.status)
        TextView status;

        //      @BindView(R.id.action)
        AppCompatTextView action;

        LinearLayout listItemTop;
        LinearLayout listItemBottom;

        LinearLayout lastClickedView = null;




        public DashboardListViewHolder(@NonNull View itemView) {
            super(itemView);
            //      ButterKnife.bind(itemView);

            listItemTop = itemView.findViewById(R.id.list_item_top);
            listItemBottom = itemView.findViewById(R.id.list_item_bottom);
            prisonerId = itemView.findViewById(R.id.prisonerId);
            volunteerId = itemView.findViewById(R.id.volunteer_id);
            timeStamp = itemView.findViewById(R.id.time_stamp);
            status = itemView.findViewById(R.id.status);
            action = itemView.findViewById(R.id.action);
            action.setOnClickListener(this);
            listItemTop.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.action:
                    mListener.onActionClick(getItem(getAdapterPosition()));
                    break;
                case R.id.list_item_top: {
                    boolean isBottomVisible = listItemBottom.getVisibility() == View.VISIBLE;
                    listItemBottom.setVisibility(isBottomVisible ? View.GONE : View.VISIBLE);

                    if (previous != null && previous != this)

                        if (previous.listItemBottom.getVisibility() == View.VISIBLE) {
                            previous.listItemBottom.setVisibility(View.GONE);
                        }
                    previous = this;
                    break;
                }

            }
        }

        void bind(User user) {

            prisonerId.setText(user.getPrisonerId());
            volunteerId.setText(user.getVolunteerId());
            timeStamp.setText(JavaUtils.timeStampToDateFormatter(user.getTimeStamp()));
            status.setText(user.getStatus());
            action.setText(user.getAction());

        }


    }

    class DashboardHeaderViewHolder extends RecyclerView.ViewHolder {
        DashboardHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface DashboardListInterface {

        void onActionClick(User user);
    }

   /* public class RecentActivityListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RecentActivityListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }*/
}
