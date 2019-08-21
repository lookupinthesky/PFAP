package com.example.pfa_p.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pfa_p.Adapter.RecentActivityAdapter;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.User;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardActivity extends AppCompatActivity  {


    @BindView(R.id.recent_activity)
    ListView recentActivityList;

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
    FloatingActionButton newSurvey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        SurveyDataSingleton surveyData = SurveyDataSingleton.getInstance(this);
        ButterKnife.bind(this);
        List<Module> modules = surveyData.getModules();
        List<User> users = surveyData.getUsers();
        Log.d(DashboardActivity.class.getName(), modules.toString());
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
                //TODO: how to store results? Store or not?
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

        ArrayAdapter<User> mAdapter = new RecentActivityAdapter(this,users,mListener);
        recentActivityList.setAdapter(mAdapter);


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

    /*private void populateListHeaders(){

        serialNumber.setText("S No.");
        prisonerId.setText("Prisoner ID");
        volunteerId.setText("Volunteer ID");
        numberOfVisits.setText("Total Visits");
        assessmentStatus.setText("Assessment Status");
        historyStatus.setText("History Status");
    }*/



}
