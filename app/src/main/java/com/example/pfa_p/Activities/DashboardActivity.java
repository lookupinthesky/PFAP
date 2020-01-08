package com.example.pfa_p.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Adapter.DashboardListAdapter;
import com.example.pfa_p.Adapter.RecentActivityAdapter;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.User;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pfa_p.Activities.LoginActivity.ARG_IS_RESULTS;
import static com.example.pfa_p.Activities.LoginActivity.ARG_PRISONER_ID;
import static com.example.pfa_p.Activities.LoginActivity.ARG_VOLUNTEER_ID;

/**
 * The home screen for the PFA-P app, essentially a dashboard showing following things
 * 1. A list of recently edited/filled entries with options to view result, continue editing, delete etc.
 * 2. Total Number of Entries present in the Database currently
 * 3. Total Number of Prisoners assessed till date
 * 4. Number of synced vs unsynced entries with server and a button to start or stop the sync
 */

public class DashboardActivity extends AppCompatActivity {


    private static final String LOG_TAG =DashboardActivity.class.getName() ;
    @BindView(R.id.recent_activity)
    RecyclerView recentActivityList;

   /* @BindView(R.id.column_headers)
    View headerView;*/
    /*@BindView(R.id.serial_num)
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
    ImageView editButton;*/

    @BindView(R.id.fabNewSurvey)
    ImageButton newSurvey;

    @BindView(R.id.tv_survey_number)
    TextView totalSurveys;

    @BindView(R.id.tv_user_number)
    TextView userNumber;

    @BindView(R.id.empty_view_parent)
    LinearLayout emptyViewParent;


    DashboardListAdapter.DashboardListInterface actionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SurveyDataSingleton surveyData = SurveyDataSingleton.getInstance(this);
        ButterKnife.bind(this);
        List<Module> modules = surveyData.getModules();
        List<User> users = surveyData.getUsersDataFromDb(this);
        Log.d(DashboardActivity.class.getName(), modules.toString());
        //      totalSurveys.setText(surveyData.getTotalSurveysTaken());
        //      userNumber.setText(surveyData.getTotalUserSurveyed());
        //   populateListHeaders();
        newSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        RecentActivityAdapter.ActionButtonsListener mListener = new RecentActivityAdapter.ActionButtonsListener() {
            @Override
            public void onResultsClick(User user) {

            }

            @Override
            public void onEditClick(User user) {

                //further implementation required
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(User user) {

            }
        };

        actionListener = new DashboardListAdapter.DashboardListInterface() {
            @Override
            public void onActionClick(User user) {


                showResults(user);

               /* if (isCompleted) {
                    showResults();
                } else {
                    resumeSurvey();
                }*/


            }
        };

        //  ArrayAdapter<User> mAdapter = new RecentActivityAdapter(this,users,mListener);
        if (users.size() != 0) {
            recentActivityList.setVisibility(View.VISIBLE);
            RecyclerView.Adapter mAdapter = new DashboardListAdapter(users, actionListener);
            recentActivityList.setLayoutManager(new LinearLayoutManager(this));
            recentActivityList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
            recentActivityList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            recentActivityList.setAdapter(mAdapter);
            emptyViewParent.setVisibility(View.GONE);
        } else {
            emptyViewParent.setVisibility(View.VISIBLE);
            recentActivityList.setVisibility(View.GONE);
        }

        // not to be done in the mainactivity but some other activity to be created later.
/*        SectionsListFragment fragment1 = new SectionsListFragment(1);
        fragment1.setOnListItemClickListener(new SectionsListFragment.OnListItemClickListener() {
            @Override
            public void onListItemClick(int sectionNumber) {
                //get section number
                //open the corresponding details fragment
            }
        });*/
    }

    public static final int REQUEST_CODE = 1;

    public static final String ARG_BUNDLE = "BundleForDashboard";

    private void showResults(User user) {
        String prisonerId = user.getPrisonerId();
        String volunteerId = user.getVolunteerId();
        boolean isResults = user.getStatus().equalsIgnoreCase("COMPLETED");
        Log.d(LOG_TAG, "method: showResults: prisonerId = " + prisonerId + " volunteerId = " + volunteerId + " isResults = " + isResults);

        Bundle args = new Bundle();
        args.putString(ARG_PRISONER_ID, prisonerId);
        args.putString(ARG_VOLUNTEER_ID, volunteerId);
        args.putBoolean(ARG_IS_RESULTS, isResults);
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.putExtra(ARG_BUNDLE, args);
        startActivity(intent);
    }



    /*private void populateListHeaders(){

        serialNumber.setText("S No.");
        prisonerId.setText("Prisoner ID");
        volunteerId.setText("Volunteer ID");
        numberOfVisits.setText("Total Visits");
        assessmentStatus.setText("Assessment Status");
        historyStatus.setText("History Status");
    }*/


}
