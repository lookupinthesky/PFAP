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
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.tonicartos.superslim.LayoutManager;
import com.tonicartos.superslim.LinearSLM;
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

        LeftPane item;

        public LeftPaneViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(LeftPane item) {
            this.item = item;

            if (tv != null && item instanceof Domain) {
                tv.setText(((Domain) item).getName());
                tv.setOnClickListener(this);
            } else if(item instanceof SubModule && ((SubModule) item).hasDomains() && headerName != null) {
                    headerName.setText(((SubModule) item).getName());
            } else if(tv!=null && item instanceof SubModule && !((SubModule) item).hasDomains()){
                tv.setText(((SubModule) item).getName());
                tv.setOnClickListener(this);
                //TODO: verify this case
            }

            }

        @Override
        public void onClick(View view) {
            ((TextView) view).setSelected(true);
            mListener.onItemClick(item);
        }
    }
    private List<LeftPane> leftPaneList;
    private LeftPaneClickListener mListener;


    public LeftPaneAdapter(List<LeftPane> list, LeftPaneClickListener listener) {
        this.leftPaneList = list;
        this.mListener = listener;
    }

    public void setData(List<LeftPane> data) {
        //1. add list 2. notifydatasetchanged
        this.leftPaneList = data;
        notifyDataSetChanged();

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

    private int getHeaderPosition(int position) {
        if (leftPaneList.get(position) instanceof Domain) {
            while (leftPaneList.get(position) instanceof Domain) {
                position = position - 1;
            }
            return position;
        } else return position;
    }

    @Override
    public void onBindViewHolder(@NonNull LeftPaneViewHolder holder, int position) {

        /** Embed section configuration. **/
        LayoutManager.LayoutParams params;
        params = (LayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        params.setSlm(LinearSLM.ID);
        params.setFirstPosition(getHeaderPosition(position));
        holder.bind(leftPaneList.get(position));
    }

    @Override
    public int getItemCount() {
        return leftPaneList.size();
    }


    @Override
    public int getItemViewType(int position) {

        LeftPane item = leftPaneList.get(position);

       if(item instanceof SubModule && ((SubModule) item).hasDomains()){
           return R.layout.header_view_left_pane ;
       }else {//if(item instanceof SubModule && !((SubModule) item).hasDomains() || item instanceof Domain){
           return R.layout.item_view_left_pane;
       }

       // return leftPaneList.get(position) instanceof SubModule ? (R.layout.header_view_left_pane) : R.layout.item_view_left_pane;
    }

    public interface LeftPaneClickListener {
        void onItemClick(LeftPane item);

    }
}
