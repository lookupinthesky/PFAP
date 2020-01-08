package com.example.pfa_p.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Activities.SurveyActivity;
import com.example.pfa_p.Adapter.LeftPaneAdapter;
import com.example.pfa_p.Model.Domain;
import com.example.pfa_p.Model.LeftPane;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.tonicartos.superslim.LayoutManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private TextView moduleName;
    private int mCurrentSectionIndex;
    private int mCurrentDomainIndex;
    private boolean[] isSectionIPresent;
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
        return inflater.inflate(R.layout.fragment_left_pane, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setData(Module module) {

        this.module = module;
        sections = module.getSections();
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
            int clickedPosition = module.getIndex() == 2 ? 1 : 0;
            setClicked(leftPaneList.get(clickedPosition));
            //  loadSectionDetails(leftPaneList.get(0));
        }
    }


    public void setCurrentState(/*int moduleIndex,*/ int subModuleIndex, int domainIndex, boolean[] isSectionIPresent) {

        Log.d(LOG_TAG, "method: setCurrentState; mCurrentDomainIndex = " + mCurrentDomainIndex + " mCurrentSectionIndex = " + mCurrentSectionIndex);
        this.mCurrentDomainIndex = domainIndex;
        this.mCurrentSectionIndex = subModuleIndex;
        this.isSectionIPresent = isSectionIPresent;
        if(isSectionIPresent!=null && sections!=null){
            setPresentSections(isSectionIPresent);
        }
    }

    private void setPresentSections(boolean[] isSectionIPresent){
        for(int i = 0; i<sections.size(); i++){
            sections.get(i).setIsPresent(isSectionIPresent[i]);
        }
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

    List<SubModule> sections;
    List<Domain> domains;

    private void createLeftPaneListData() {
        leftPaneList = new ArrayList<>();
        for (SubModule subModule : sections) {
            if (subModule.isPresent()) {
                leftPaneList.add(subModule);
                if (subModule.hasDomains()) {
                    domains = subModule.getDomains();
                    leftPaneList.addAll(domains);


                }
            }
        }
    }


    private void moveToNext() {
        LeftPane item;
        if (mCurrentDomainIndex != -1 && mCurrentDomainIndex < sections.get(mCurrentSectionIndex).getDomains().size() - 1) {
            mCurrentDomainIndex++;
            item = sections.get(mCurrentSectionIndex).getDomains().get(mCurrentDomainIndex);
        } else {
            //     mCurrentSectionIndex++;
            mCurrentSectionIndex = getNextSectionIndex(mCurrentSectionIndex);
            if (mCurrentDomainIndex != -1) {
                mCurrentDomainIndex = 0;
                item = sections.get(mCurrentSectionIndex).getDomains().get(mCurrentDomainIndex);
            } else {
                item = sections.get(mCurrentSectionIndex);
            }
        }
       //     setClicked(item)/*next clickable item in List*/;
            loadSectionDetails(item);
        }

    private int getNextSectionIndex(int currentIndex) {

        //find current index in left pane list
        boolean found = false;
        int index = -1;

        SubModule subModule = sections.get(currentIndex);

        for (int i = 0; i < leftPaneList.size(); i++) {
            if (leftPaneList.get(i) instanceof SubModule) {
                if (found) {
                    index = ((SubModule) leftPaneList.get(i)).getIndex();
                    break;
                }
                if (subModule.equals(leftPaneList.get(i))) {
                    found = true;

                }

            }
        }
        Log.d("SectionsListFragment", "method: getNextSectionIndex called, index found = " + index);
        return index;


        // find next in left pane list and get index


    }


        @Override
        public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState){

            //TODO: setValue to module Name to TextView

            parent = view.findViewById(R.id.parent_sections_list);
            moduleName = view.findViewById(R.id.list_module_name);
            sections = module.getSections();
            if(isSectionIPresent!=null && sections!=null){
                setPresentSections(isSectionIPresent);
            }
            createLeftPaneListData();
            adapter = new LeftPaneAdapter(leftPaneList, this);
            parent.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            parent.setLayoutManager(new LayoutManager(context));
            parent.setAdapter(adapter);
            int subModuleIndexInList = leftPaneList.indexOf(sections.get(mCurrentSectionIndex));
            /*if(mCurrentDomainIndex==-1){
                loadSectionDetails(leftPaneList.get(getLeftPaneIndexForSectionIndex(mCurrentSectionIndex)));
            }
            else{
                int sectionPosition = getLeftPaneIndexForSectionIndex(mCurrentSectionIndex);
                int domainPosition = mCurrentDomainIndex + 1;
                int finalPositionInList = sectionPosition + domainPosition;
                loadSectionDetails(leftPaneList.get(getLeftPaneIndexForSectionIndex(finalPositionInList)));
            }*/
            Log.d(SectionDetailsFragment.class.getName(), "onViewCreated: mCurrentDomainIndex = " + mCurrentDomainIndex + " mCurrentSectionIndex = " + mCurrentSectionIndex + " mSectionIndexinList = " + subModuleIndexInList  );
            loadSectionDetails(mCurrentDomainIndex == -1 ? leftPaneList.get(subModuleIndexInList) :
                    leftPaneList.get(subModuleIndexInList + mCurrentDomainIndex + 1));
            moduleName.setText(module.getName());
        }


       /* public int getLeftPaneIndexForSectionIndex ( int currentSectionIndex){
            SubModule subModule = sections.get(currentSectionIndex);
            for(int i = 0; i<leftPaneList.size(); i++){
                LeftPane item = leftPaneList.get(i);
                if(item instanceof SubModule){
                    if(item.equals(subModule)){
                        return i;
                    }
                }
            }
        }*/

        public LeftPane getCurrentItem () {

            Log.d(LOG_TAG, "method: getCurrentItem ; mCurrentDomainIndex = " + mCurrentDomainIndex + " mCurrentSectionIndex = " + mCurrentSectionIndex);
            if (sections.get(mCurrentSectionIndex).getName().equals("Basic Questionnaire")) {
                return sections.get(mCurrentSectionIndex);
            }
            if (mCurrentDomainIndex == -1) {
                return leftPaneList.get(mCurrentSectionIndex);
            } else {
                return sections.get(mCurrentSectionIndex).getDomains().get(mCurrentDomainIndex);
            }
        }

        private void loadSectionDetails (LeftPane item){
            mListener.onListItemClick(item);
        }

        @Override
        public void onItemClick (LeftPane item){
            mListener.onListItemClick(item);
        }

        public interface OnListItemClickListener {
            void onListItemClick(LeftPane item);
        }
    }
