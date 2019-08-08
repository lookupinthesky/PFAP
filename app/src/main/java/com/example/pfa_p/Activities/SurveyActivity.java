package com.example.pfa_p.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.pfa_p.Database.SurveyContract.SurveyEntry;
import com.example.pfa_p.Database.SurveyProvider;
import com.example.pfa_p.Fragments.SectionDetailsFragment;
import com.example.pfa_p.Fragments.SectionsListFragment;
import com.example.pfa_p.Model.Module;
import com.example.pfa_p.Model.SubModule;
import com.example.pfa_p.R;
import com.example.pfa_p.SurveyDataSingleton;

import java.util.List;

public class SurveyActivity extends FragmentActivity implements SectionsListFragment.OnListItemClickListener,
        SectionDetailsFragment.OnNextClickListener, LoaderManager.LoaderCallbacks<String> {


    FrameLayout sectionDetailsParent;
    FrameLayout sectionsListParent;
    SectionDetailsFragment sectionDetailsFragment;
    SectionsListFragment sectionsListFragment;
    SurveyDataSingleton surveyData;
    List<Module> modules;
    FragmentManager fm;
    FragmentTransaction ft;

    private static final int DEFAULT_MODULE_EMPTY = -1 ;
    private static final int DEFAULT_SECTIONS_EMPTY = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_dual_pane);
        surveyData = SurveyDataSingleton.getInstance(this);
        modules = surveyData.getModules();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        sectionDetailsParent = findViewById(R.id.fragment_section_details_parent);
        sectionsListParent = findViewById(R.id.fragment_sections_list_parent);
        sectionDetailsFragment = SectionDetailsFragment.newInstance(0/*DEFAULT_SECTIONS_EMPTY*/);
        sectionsListFragment = SectionsListFragment.newInstance(0/*DEFAULT_SECTIONS_EMPTY*/);
        ft.add(R.id.fragment_sections_list_parent, sectionsListFragment);
        ft.add(R.id.fragment_section_details_parent, sectionDetailsFragment);
        ft.commit();

    }


    private void startSectionsListFragment(int moduleIndex){



    }

    private void startSectionDetailsFragment(int sectionIndex){

    }


    private SubModule getSubModuleFromDb(Cursor cursor, String[] projection, String selection) {

        long question_id;
        question_id = cursor.getLong(cursor.getColumnIndex(SurveyEntry.ANSWERS_COLUMN_QUESTION_ID));
        cursor.close();
        String[] selectionArgs_questions = new String[]{String.valueOf(question_id)};
        Cursor cursor2 = getContentResolver().query(SurveyEntry.TABLE_QUESTIONS_CONTENT_URI, projection, selection, selectionArgs_questions, null);
        //cursor check TODO
        String question_name = cursor2.getString(cursor.getColumnIndex(SurveyEntry.QUESTIONS_COLUMN_NAME)); // should be from a global serial number
        cursor2.close();
        SubModule subModule = SubModule.getSubModule(this, question_name);
        return subModule;
    }

    private void startSurvey(String prisonerId) {
        String[] projection_users = new String[]{SurveyEntry.USERS_ID, SurveyEntry.USERS_COLUMN_INMATE_ID};
        String[] projection_history = new String[]{SurveyEntry.ANSWERS_COLUMN_QUESTION_ID,
                SurveyEntry.ANSWERS_COLUMN_SURVEY_ID,
                SurveyEntry.ANSWERS_COLUMN_USER_ID};
        String[] projection_questions = new String[]{SurveyEntry.QUESTIONS_COLUMN_NAME};
        String[] selectionArgs_users = new String[]{prisonerId};
        String selection_users = SurveyEntry.USERS_COLUMN_INMATE_ID;
        String selection_history = SurveyEntry.ANSWERS_COLUMN_USER_ID;
        String selection_question = SurveyEntry.QUESTIONS_ID;
        String[] selectionArgs_history;
        String[] selectionArgs_questions;
        long _id = -1;
        long question_id = -1;
        String question_name = "";

        Cursor cursor = getContentResolver().query(SurveyEntry.TABLE_USERS_CONTENT_URI, projection_users, selection_users, selectionArgs_users, null);

        if (cursor.moveToFirst()) {
            _id = cursor.getLong(cursor.getColumnIndex(SurveyEntry.USERS_ID));
        }
        cursor.close();
        selectionArgs_history = new String[]{String.valueOf(_id)};
        if (_id != -1) //user present in user table
        {
            cursor = getContentResolver().query(SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, projection_history, selection_history, selectionArgs_history, null);

            if (cursor.getCount() != 0 && cursor.moveToLast()) {
                SubModule submodule = getSubModuleFromDb(cursor, projection_questions, selection_history);
                Module module = submodule.getModule();

                if (module.getSections().indexOf(submodule) == module.getSections().size() - 1) {
                    // complete data - check assessments table
                    Cursor cursor3 = getContentResolver().query(SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, projection_history, selection_history, selectionArgs_history, null);

                    if (cursor3.getCount() != 0 && cursor3.moveToLast()) {
                        SubModule submodule2 = getSubModuleFromDb(cursor3, projection_questions, selection_question);
                        Module module2 = submodule2.getModule();

                        if (module2.getSections().indexOf(submodule2) < module.getSections().size() - 1) {

                            //incomplete data - start assessment from next domain
                            startSectionsListFragment(modules.indexOf(module2));
                            startSectionDetailsFragment((module2.getSections().indexOf(submodule2)));

                        }
                    } else {

                        // start assessment from start
                        startSectionsListFragment(2);
                        startSectionDetailsFragment(0);
                    }


                } else if (module.getSections().indexOf(submodule) < module.getSections().size() - 1) {

                    //incomplete data - start history with next section or new visit.
                    startSectionsListFragment(0);
                    startSectionDetailsFragment(module.getSections().indexOf(submodule));
                }
            } else {

                startSectionsListFragment(0);

                startSectionDetailsFragment(0);
                // no data - start history with first section
            }


        } else {
            insertNewUser(prisonerId);
            startSectionsListFragment(0);
            startSectionDetailsFragment(0);
            // no data - start history with first section
        }
    }

    private void insertNewUser(String prisonerId){

        ContentValues cv = new ContentValues();
        cv.put(SurveyEntry.USERS_COLUMN_INMATE_ID, prisonerId);
        getContentResolver().insert(SurveyEntry.TABLE_USERS_CONTENT_URI, cv);

    }


    @Override
    public void onListItemClick(int moduleNumber, int sectionNumber) {
        if (sectionNumber < modules.get(moduleNumber).getNumberOfSections()) {
            sectionDetailsFragment.createLayout(moduleNumber, sectionNumber);
        } else {
            sectionsListFragment.createLayout(moduleNumber + 1);
        }
    }

    @Override
    public void onNextClick(int moduleNumber, int sectionNumber) {
        sectionsListFragment.clickNextItem(moduleNumber, sectionNumber);
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
