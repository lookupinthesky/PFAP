package com.example.pfa_p.Activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pfa_p.Adapter.ResultsAdapter;
import com.example.pfa_p.Database.SurveyContract;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = ResultsActivity.class.getName();
    RecyclerView parent;
    Button buttonHome;
    Button buttonExit;
  //  ResetSurveyListener mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        buttonHome = findViewById(R.id.button_home);
        buttonExit = findViewById(R.id.button_exit);
        buttonExit.setOnClickListener(this);
        buttonHome.setOnClickListener(this);
        parent = findViewById(R.id.results_list);
        parent.setLayoutManager(new LinearLayoutManager(this));
        List<SubModule> subModules = SurveyDataSingleton.getInstance(this).getModules().get(2).getSections();
        setDataAndInvalidate(subModules);


    }


    List<SubModule> list = new ArrayList<>();


    public void setDataAndInvalidate(List<SubModule> data) {

        for (SubModule subModule : data) {
            if (subModule.isPresent()) {
                list.add(subModule);
            }
        }

        RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> adapter = new ResultsAdapter(list);
        parent.setAdapter(adapter);

    }


    public JSONArray getExportableDatabaseInJSON() {
        JSONArray arr = new JSONArray();
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_USERS_CONTENT_URI, this));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_SURVEYS_CONTENT_URI, this));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_SECTIONS_CONTENT_URI, this));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_DOMAINS_CONTENT_URI, this));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_QUESTIONS_CONTENT_URI, this));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, this));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, this));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_RESULTS_CONTENT_URI, this ));
        Log.d(TAG, arr.toString());
        return arr;
    }


    public JSONObject getCursorFromTable(Uri tableuri, Context context) {

        JSONObject obj;
        Cursor cursor = context.getContentResolver().query(tableuri, null, null, null, null);
        obj = cursorToJSON(cursor);
        cursor.close();
        return obj;
    }

    public JSONObject cursorToJSON(Cursor cursor) {

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            String columnName = cursor.getColumnName(i);
            try {
                switch (cursor.getType(i)) {
                    case Cursor.FIELD_TYPE_INTEGER:
                        jsonObject.put(columnName, cursor.getInt(i));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        jsonObject.put(columnName, cursor.getFloat(i));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        jsonObject.put(columnName, cursor.getString(i));
                        break;

                }
            } catch (Exception ex) {
                Log.e(TAG, "Exception converting cursor column to json field: " + columnName);
            }
        }
        return jsonObject;

    }


    @Override
    public void onClick(View v) {
        switch(v.getId() ){

        case R.id.button_home:{
    //        SurveyDataSingleton.getInstance(this).createSurveyDataAndAddToCurrentSession(this);
            Intent intent = new Intent(ResultsActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
            case R.id.button_exit:{
                this.finishAffinity();
            }

            case R.id.force_sync: {

                Bundle bundle = new Bundle();
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_FORCE, true);
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                ContentResolver.requestSync(null, SurveyContract.CONTENT_AUTHORITY, bundle);

            }
        }
    }

    public interface ResetSurveyListener{
        void onSurveyFinished();
    }
}
