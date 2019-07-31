package com.example.pfa_p.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


public class SurveyProvider extends ContentProvider{
    private static final String LOG_TAG = SurveyProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SurveyDbHelper mOpenHelper;

    // Codes for the UriMatcher //////
    private static final int SECTIONS = 100;
    private static final int SECTIONS_WITH_ID = 101;
    private static final int QUESTIONS = 200;
    private static final int QUESTIONS_WITH_ID = 201;
    private static final int USERS = 300;
    private static final int USERS_WITH_ID = 301;
    private static final int ANSWERS = 400;
    private static final int ANSWERS_WITH_ID = 401;
    ////////

    private static UriMatcher buildUriMatcher(){
        // Build a UriMatcher by adding a specific code to return based on a match
        // It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SurveyContract.CONTENT_AUTHORITY;

        // add a code for each type of URI you want
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_SECTIONS, SECTIONS);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_SECTIONS + "/#", SECTIONS_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate(){
        mOpenHelper = new SurveyDbHelper(getContext());

        return true;
    }

    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);

        switch (match){
            case SECTIONS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_SECTIONS;
            }
            case SECTIONS_WITH_ID:{
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_SECTIONS;
            }
            case QUESTIONS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_QUESTIONS;
            }
            case QUESTIONS_WITH_ID:{
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_QUESTIONS;
            }
            case USERS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_USERS;
            }
            case USERS_WITH_ID:{
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_USERS;
            }
            case ANSWERS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_ANSWERS;
            }
            case ANSWERS_WITH_ID:{
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_ANSWERS;
            }

            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){
            // All s selected
            case s:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_s,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual s based on Id selected
            case s_WITH_ID:{
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_s,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            default:{
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        long _id = 0;
        switch (sUriMatcher.match(uri)) {
            case SECTIONS: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_SECTIONS, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildSectionsUri(_id); //get _id from uri
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            case QUESTIONS: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_QUESTIONS, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildQuestionsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }


            case USERS: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_USERS, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildUsersUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }


            case ANSWERS: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_ANSWERS, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildAnswersUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }


            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch(match){

            case SECTIONS: {

                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_SECTIONS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_SECTIONS + "'");
                break;
            }

            case QUESTIONS: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_QUESTIONS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_QUESTIONS + "'");
                break;
            }
            
            case USERS: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_USERS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_USERS + "'");
                break;
            }
            
            case ANSWERS: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_ANSWERS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_ANSWERS + "'");
                break;
            }
            





		/*	case s:
				numDeleted = db.delete(
						SurveyContract.SurveyEntry.TABLE_s, selection, selectionArgs);
				// reset _ID
				db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
						SurveyContract.SurveyEntry.TABLE_s + "'");
				break;
			case s_WITH_ID:
				numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_s,
						SurveyContract.SurveyEntry._ID + " = ?",
						new String[]{String.valueOf(ContentUris.parseId(uri))});
				// reset _ID
				db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
						SurveyContract.SurveyEntry.TABLE_s + "'");

				break;*/

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch(match){
            case :
                // allows for multiple transactions
                db.beginTransaction();

                // keep track of successful inserts
                int numInserted = 0;
                try{
                    for(ContentValues value : values){
                        if (value == null){
                            throw new IllegalArgumentException("Cannot have null content values");
                        }
                        long _id = -1;
                        try{
                            _id = db.insertOrThrow(SurveyContract.SurveyEntry.,
                                    null, value);
                        }catch(SQLiteConstraintException e) {
                            Log.w(LOG_TAG, "Attempting to insert " +
                                    value.getAsString(
                                            SurveyContract.SurveyEntry.)
                                    + " but value is already in database.");
                        }
                        if (_id != -1){
                            numInserted++;
                        }
                    }
                    if(numInserted > 0){
                        // If no errors, declare a successful transaction.
                        // database will not populate if this is not called
                        db.setTransactionSuccessful();
                    }
                } finally {
                    // all transactions occur at once
                    db.endTransaction();
                }
                if (numInserted > 0){
                    // if there was successful insertion, notify the content resolver that there
                    // was a change
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return numInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numUpdated = 0;

        if (contentValues == null){
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch(sUriMatcher.match(uri)){
            case s:{
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_s,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case s_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_s,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }

}