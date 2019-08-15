package com.example.pfa_p.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Activities.SurveyActivity;
import com.example.pfa_p.Adapter.LeftPaneAdapter;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.LeftPane;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;

import java.util.ArrayList;
import java.util.List;


import com.tonicartos.superslim.LayoutManager;

public class SectionsListFragment extends Fragment implements LeftPaneAdapter.LeftPaneClickListener {


    /**
     * SectionsListFragment is characterised by two properties : STATE and DATA -
     * DATA is only defined by the module, no other core survey data is required. Regardless of its controller, it will produce
     * the given layout and functionality according to the module provided to it.
     * state defines the current position of user that is the current submodule and current domain within the provided module
     * of whose questions the user is filling out the form of
     * <p>
     * SectionsListFragment = f(STATE, DATA) where STATE: mCurrentSectionIndex, mCurrentDomainIndex & DATA instance of Module
     */


    private Context context;
    private OnListItemClickListener mListener;
    private RecyclerView parent;
    private int mCurrentSectionIndex;
    private int mCurrentDomainIndex;
    private List<LeftPane> leftPaneList;
    private RecyclerView.Adapter<LeftPaneAdapter.LeftPaneViewHolder> adapter;
    private Module module;

    public static final String LOG_TAG = SurveyActivity.class.getName();

    public static SectionsListFragment newInstance(int moduleNumber) {
        Bundle bundle = new Bundle();
        bundle.putInt("module_number", moduleNumber);
        SectionsListFragment sectionsListFragment = new SectionsListFragment();
        sectionsListFragment.setArguments(bundle);
        return sectionsListFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.master_sections_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setData(Module module) {
        this.module = module;
    }

    private void setDataToAdapter() {
        if (adapter instanceof LeftPaneAdapter) {
            ((LeftPaneAdapter) adapter).setData(leftPaneList);
        }
    }

    public void onStateChanged(boolean isModuleChanged) {
        if (!isModuleChanged) {
            moveToNext();
        } else {
            createLeftPaneListData();
            setDataToAdapter();
        }
    }

    public void setCurrentState(/*int moduleIndex,*/ int subModuleIndex, int domainIndex) {
        this.mCurrentDomainIndex = domainIndex;
        this.mCurrentSectionIndex = subModuleIndex;
    }

    public void setOnListItemClickListener(OnListItemClickListener mListener) {
        this.mListener = mListener;
    }

    private void setClicked(LeftPane item) {
        int i = leftPaneList.indexOf(item);
        LeftPaneAdapter.LeftPaneViewHolder holder;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                parent.findViewHolderForAdapterPosition(i).itemView.performClick();
            }
        }, 100);
    }

    private void createLeftPaneListData() {
        leftPaneList = new ArrayList<>();
        List<SubModule> sections = module.getSections();
        List<Domain> domains;
        for (SubModule subModule : sections) {
            leftPaneList.add(subModule);
            if (subModule.hasDomains()) {
                domains = subModule.getDomains();
                leftPaneList.addAll(domains);
            }
        }
    }

    private void moveToNext() {
        LeftPane item;
        if (mCurrentDomainIndex != -1) {
            mCurrentDomainIndex++;
            item = getLeftPaneItemForPosition(mCurrentDomainIndex);
        } else {
            mCurrentSectionIndex++;
            item = getLeftPaneItemForPosition(mCurrentSectionIndex);
        }
        setClicked(item)/*next clickable item in List*/;
        loadSectionDetails(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //TODO: setValue to module Name to TextView

        parent = view.findViewById(R.id.parent_sections_list);
        createLeftPaneListData();
        adapter = new LeftPaneAdapter(leftPaneList, this);
        parent.setLayoutManager(new LayoutManager(context));
        parent.setAdapter(adapter);
        loadSectionDetails(mCurrentDomainIndex == -1 ? getLeftPaneItemForPosition(mCurrentSectionIndex) : getLeftPaneItemForPosition(mCurrentDomainIndex));
    }


    private LeftPane getLeftPaneItemForPosition(int position) {
        return leftPaneList.get(position);
    }

    private void loadSectionDetails(LeftPane item) {
        mListener.onListItemClick(item);
    }

    @Override
    public void onItemClick(LeftPane item) {
        mListener.onListItemClick(item);
    }

    public interface OnListItemClickListener {
        void onListItemClick(LeftPane item);
    }


}
