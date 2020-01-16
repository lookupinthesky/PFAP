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

    public void setSearchResultsListener(SearchResultsListener listener) {
        this.mListener = listener;
    }

    View view;
    LinearLayout searchResults;
    LinearLayout noSearchResults;
    TextView prisonerIdView;
    TextView volunteerIdView;
    TextView demographicStatusView;
    TextView questionnaireStatus;
    TextView assessmentStatusView;
    Button resumeButton;
    Button startNewPrisoner;
    TextView noResultDisplayMessage;
   // int visitNumber;

    Button nextButton;
    Button discardButton;

    View confirmationScreen;
    View searchResultsNotFound;
    TextView visitNumberView;

    public boolean isNewPrisoner() {
        return isNewPrisoner;
    }

    public void setIsNewPrisoner(boolean newPrisoner) {
        isNewPrisoner = newPrisoner;
    }

    View bottomBar;
    boolean isNewPrisoner = true;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_results_new, container, false);

        searchResultsNotFound = view.findViewById(R.id.search_result_not_found);
        confirmationScreen = view.findViewById(R.id.search_results_found);



      //  searchResults = view.findViewById(R.id.search_results_found);



        nextButton = view.findViewById(R.id.next_button);
        discardButton = view.findViewById(R.id.discard_button);
   //     noSearchResults = view.findViewById(R.id.search_result_not_found);
        TextView prisonerIdText = view.findViewById(R.id.field_prisoner_id);
        prisonerIdView = confirmationScreen.findViewById(R.id.value_prisoner_id);
        TextView volunteerIdText = confirmationScreen.findViewById(R.id.field_volunteer_id);
        volunteerIdView = confirmationScreen.findViewById(R.id.value_volunteer_id);
        //TextView demographicsStatusText = view.
        demographicStatusView = confirmationScreen.findViewById(R.id.status_demographics);
        questionnaireStatus = confirmationScreen.findViewById(R.id.status_basic_questionnaire);
        assessmentStatusView = confirmationScreen.findViewById(R.id.status_assessment);
     //   resumeButton = confirmationScreen.findViewById(R.id.resume_or_next_visit);
     //   startNewPrisoner = confirmationScreen.findViewById(R.id.start_new);
        //      Button startAssessment = view.findViewById(R.id.start_assessment);
        noResultDisplayMessage = searchResultsNotFound.findViewById(R.id.not_found_message);
        visitNumberView = confirmationScreen.findViewById(R.id.status_visit_number);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNewPrisoner){
                    showConfirmationScreen();
                } else{
                    mListener.prepareAndStartSurvey(false, prisonerId, idInDb, true);
                }
            }
        });

        //TODO: null checks for all data
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Remove ID from DB
            }
        });


        if (isNewPrisoner) { //TODO: make it a boolean : bug : on screen sleep the value changes
            showNoResultsView();
        } else {
            showConfirmationScreen();
        }

        return view;
    }


    public interface SearchResultsListener {

        void prepareAndStartSurvey(boolean newUser, String prisonerId, long idInDb, boolean resume);

    }


    private void showNoResultsView() {


        confirmationScreen.setVisibility(View.GONE);
        searchResultsNotFound.setVisibility(View.VISIBLE);
        noResultDisplayMessage.setText(getString(R.string.display_message, String.valueOf(prisonerId)));
     //   startNewPrisoner.setText(getString(R.string.start_new_survey));
       /* startNewPrisoner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.prepareAndStartSurvey(true, prisonerId, idInDb, false);
            }
        });*/
        //           startAssessment.setText(getString(R.string.start_survey_assessment));
        //         startAssessment.setOnClickListener(null);

    }

    private void showConfirmationScreen(){
        if(isNewPrisoner){
            isNewPrisoner = false;
        }
        searchResultsNotFound.setVisibility(View.GONE);
        confirmationScreen.setVisibility(View.VISIBLE);
        prisonerIdView.setText(String.valueOf(prisonerId));
        volunteerIdView.setText(volunteerId);
        demographicStatusView.setText(demographicStatus);
        questionnaireStatus.setText(demographicStatus);
        assessmentStatusView.setText(assessmentStatus);
        visitNumberView.setText(String.valueOf(visitNumber));

        /*resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.prepareAndStartSurvey(false, prisonerId, idInDb, true);
            }
        });*/

    }
}




