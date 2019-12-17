package com.example.pfa_p.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pfa_p.R;

public class SearchResultsFragment extends Fragment {


    public long idInDb;
    public String prisonerId;

    public void setVisitNumber(int visitNumber) {
        this.visitNumber = visitNumber;
    }

    public String volunteerId;
    public String demographicStatus;
    public int visitNumber;

    public void setPrisonerId(String prisonerId) {
        this.prisonerId = prisonerId;
    }

    public String basicQuestionnaireStatus;
    public String assessmentStatus;

    public SearchResultsListener mListener;


    public void setId(long _id) {
        this.idInDb = _id;
    }

    public void setVolunteerId(String volunteerId) {
        this.volunteerId = volunteerId;
    }

    public void setDemographicStatus(String demographicStatus) {
        this.demographicStatus = demographicStatus;
    }

    public void setBasicQuestionnaireStatus(String basicQuestionnaireStatus) {
        this.basicQuestionnaireStatus = basicQuestionnaireStatus;
    }

    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }


    public static SearchResultsFragment newInstance() {

        return new SearchResultsFragment();
    }

    public void setSearchResultsListener(SearchResultsListener listener){
        this.mListener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        LinearLayout searchResults = view.findViewById(R.id.search_results_found);
        LinearLayout noSearchResults = view.findViewById(R.id.search_result_not_found);
        TextView prisonerIdText = view.findViewById(R.id.field_prisoner_id);
        TextView prisonerIdView = view.findViewById(R.id.value_prisoner_id);
        TextView volunteerIdText = view.findViewById(R.id.field_volunteer_id);
        TextView volunteerIdView = view.findViewById(R.id.value_volunteer_id);
        //TextView demographicsStatusText = view.
        TextView demographicStatusView = view.findViewById(R.id.status_demographics);
        TextView questionnaireStatus = view.findViewById(R.id.status_basic_questionnaire);
        TextView assessmentStatusView = view.findViewById(R.id.status_assessment);
        Button resumeButton = view.findViewById(R.id.resume_or_next_visit);
        Button startNewPrisoner = view.findViewById(R.id.start_new);
        Button startAssessment = view.findViewById(R.id.start_assessment);
        TextView noResultDisplayMessage = view.findViewById(R.id.not_found_message);


        if (idInDb == -1) {
            searchResults.setVisibility(View.GONE);
            noResultDisplayMessage.setText(getString(R.string.display_message, String.valueOf(prisonerId)));
            startNewPrisoner.setText(getString(R.string.start_new_survey));
            startNewPrisoner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.prepareAndStartSurvey(true, prisonerId, idInDb, false);
                }
            });
            startAssessment.setText(getString(R.string.start_survey_assessment));
            startAssessment.setOnClickListener(null);
        } else {
            noSearchResults.setVisibility(View.GONE);
            prisonerIdView.setText(String.valueOf(prisonerId));
            volunteerIdView.setText(volunteerId);
            demographicStatusView.setText(demographicStatus);
            questionnaireStatus.setText(demographicStatus);
            assessmentStatusView.setText(assessmentStatus);
            resumeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.prepareAndStartSurvey(false, prisonerId, idInDb, true);
                }
            });
        }

        return view;
    }


    public interface SearchResultsListener {

        void prepareAndStartSurvey(boolean newUser, String prisonerId, long idInDb, boolean resume);

    }
}




