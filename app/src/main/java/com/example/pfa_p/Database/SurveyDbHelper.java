package com.example.pfa_p.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SurveyDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = SurveyDbHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "Survey.db";
    private static final int DATABASE_VERSION = 12;

    private static final String SQL_CREATE_TABLE_SECTIONS = "CREATE TABLE " +
            SurveyContract.SurveyEntry.TABLE_SECTIONS + "(" + SurveyContract.SurveyEntry.SECTIONS_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SurveyContract.SurveyEntry.SECTIONS_COLUMN_NAME + " TEXT NOT NULL);";

    private static final String SQL_CREATE_TABLE_QUESTIONS = "CREATE TABLE " +
            SurveyContract.SurveyEntry.TABLE_QUESTIONS + "(" + SurveyContract.SurveyEntry.QUESTIONS_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SurveyContract.SurveyEntry.QUESTIONS_COLUMN_SECTION_ID + " INTEGER NOT NULL, " +
            SurveyContract.SurveyEntry.QUESTIONS_COLUMN_DESCRIPTION +
				" TEXT NOT NULL);";

    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE " +
            SurveyContract.SurveyEntry.TABLE_USERS + "(" + SurveyContract.SurveyEntry.USERS_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SurveyContract.SurveyEntry.USERS_COLUMN_NAME + " TEXT NOT NULL, " +
            SurveyContract.SurveyEntry.USERS_COLUMN_INMATE_ID +
            " TEXT NOT NULL, " +
            SurveyContract.SurveyEntry.USERS_COLUMN_FLAG +
            " INTEGER NOT NULL);";

    private static final String SQL_CREATE_TABLE_ANSWERS = "CREATE TABLE " +
            SurveyContract.SurveyEntry.TABLE_ANSWERS + "(" + SurveyContract.SurveyEntry.ANSWERS_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SurveyContract.SurveyEntry.ANSWERS_COLUMN_USER_ID + " INTEGER NOT NULL, " +
            SurveyContract.SurveyEntry.ANSWERS_COLUMN_QUESTION_ID +
            " INTEGER NOT NULL, " +
            SurveyContract.SurveyEntry.ANSWERS_COLUMN_RESPONSE +
            " TEXT NOT NULL, " +
            SurveyContract.SurveyEntry.ANSWERS_COLUMN_FLAG +
            " INTEGER NOT NULL);";



    public SurveyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_SECTIONS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_QUESTIONS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_ANSWERS);
    }

    // Upgrade database when version is changed.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SurveyContract.SurveyEntry.TABLE_SECTIONS);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                SurveyContract.SurveyEntry.TABLE_SECTIONS + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SurveyContract.SurveyEntry.TABLE_QUESTIONS);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                SurveyContract.SurveyEntry.TABLE_QUESTIONS + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SurveyContract.SurveyEntry.TABLE_USERS);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                SurveyContract.SurveyEntry.TABLE_USERS + "'");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SurveyContract.SurveyEntry.TABLE_ANSWERS);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                SurveyContract.SurveyEntry.TABLE_ANSWERS + "'");

        // re-create database
        onCreate(sqLiteDatabase);
    }
}
