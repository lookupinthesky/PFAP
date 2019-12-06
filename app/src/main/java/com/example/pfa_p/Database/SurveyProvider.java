package com.example.pfa_p.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class SurveyProvider extends ContentProvider {
    private static final String LOG_TAG = SurveyProvider.class.getSimpleName();
    public static final UriMatcher sUriMatcher = buildUriMatcher();
    private SurveyDbHelper mOpenHelper;

    // Codes for the UriMatcher //////
    private static final int SECTIONS = 100;
    private static final int SECTIONS_WITH_ID = 101;
    private static final int QUESTIONS = 200;
    private static final int QUESTIONS_WITH_ID = 201;
    private static final int USERS = 300;
    private static final int USERS_WITH_ID = 301;
    private static final int ANSWERS_ASSESSMENT = 400;
    private static final int ANSWERS_ASSESSMENT_WITH_ID = 401;
    private static final int ANSWERS_HISTORY = 500;
    private static final int ANSWERS_HISTORY_WITH_ID = 501;
    private static final int DOMAINS = 600;
    private static final int DOMAINS_WITH_ID = 601;
    private static final int SURVEYS = 700;
    private static final int SURVEYS_WITH_ID = 701;
    private static final int RESULTS = 800;
    private static final int RESULTS_WITH_ID = 801;
    private static final int RESULTS_TYPES = 900;
    private static final int RESULTS_TYPES_WITH_ID = 901;

    ////////

    private static UriMatcher buildUriMatcher() {
        // Build a UriMatcher by adding a specific code to return based on a match
        // It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SurveyContract.CONTENT_AUTHORITY;

        // add a code for each type of URI you want
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_SECTIONS, SECTIONS);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_SECTIONS + "/#", SECTIONS_WITH_ID);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_QUESTIONS, QUESTIONS);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_QUESTIONS + "/#", QUESTIONS);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_DOMAINS, DOMAINS);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_DOMAINS + "/#", DOMAINS_WITH_ID);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_SURVEYS, SURVEYS);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_SURVEYS + "/#", SURVEYS_WITH_ID);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS, ANSWERS_ASSESSMENT);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS + "/#", ANSWERS_ASSESSMENT_WITH_ID);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS, ANSWERS_HISTORY);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS + "/#", ANSWERS_HISTORY_WITH_ID);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_USERS, USERS);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_USERS + "/#", USERS_WITH_ID);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_RESULTS, RESULTS);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_RESULTS + "/#", RESULTS_WITH_ID);
        /*matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_RESULTS_TYPES, RESULTS_TYPES);
        matcher.addURI(authority, SurveyContract.SurveyEntry.TABLE_RESULTS_TYPES + "/#", RESULTS_TYPES_WITH_ID);
*/
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new SurveyDbHelper(getContext());

        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SECTIONS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_SECTIONS;
            }
            case SECTIONS_WITH_ID: {
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_SECTIONS;
            }
            case QUESTIONS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_QUESTIONS;
            }
            case QUESTIONS_WITH_ID: {
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_QUESTIONS;
            }
            case USERS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_USERS;
            }
            case USERS_WITH_ID: {
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_USERS;
            }
            case ANSWERS_ASSESSMENT: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_ASSESSMENT_ANSWERS;
            }
            case ANSWERS_ASSESSMENT_WITH_ID: {
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_ASSESSMENT_ANSWERS;
            }
            case ANSWERS_HISTORY: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_HISTORY_ANSWERS;
            }
            case ANSWERS_HISTORY_WITH_ID: {
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_HISTORY_ANSWERS;
            }
            case SURVEYS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_DOMAINS;
            }
            case SURVEYS_WITH_ID: {
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_DOMAINS;
            }
            case DOMAINS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_SURVEYS;
            }
            case DOMAINS_WITH_ID: {
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_SURVEYS;
            }
            case RESULTS: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_RESULTS;
            }
            case RESULTS_WITH_ID: {
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_RESULTS;
            }
           /* case RESULTS_TYPES: {
                return SurveyContract.SurveyEntry.CONTENT_DIR_TYPE_RESULTS_TYPES;
            }
            case RESULTS_TYPES_WITH_ID: {
                return SurveyContract.SurveyEntry.CONTENT_ITEM_TYPE_RESULTS_TYPES;
            }*/

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // All s selected
            case SECTIONS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_SECTIONS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual s based on Id selected
            case SECTIONS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_SECTIONS,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }

            case SURVEYS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_SURVEYS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual s based on Id selected
            case SURVEYS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_SURVEYS,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case DOMAINS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_DOMAINS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual s based on Id selected
            case DOMAINS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_DOMAINS,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case QUESTIONS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_QUESTIONS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual s based on Id selected
            case QUESTIONS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_QUESTIONS,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case USERS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_USERS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual s based on Id selected
            case USERS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_USERS,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case ANSWERS_ASSESSMENT: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual s based on Id selected
            case ANSWERS_ASSESSMENT_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case ANSWERS_HISTORY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            // Individual s based on Id selected
            case ANSWERS_HISTORY_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }

            case RESULTS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_RESULTS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case RESULTS_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_RESULTS,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
          /*  case RESULTS_TYPES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_RESULTS_TYPES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }
            case RESULTS_TYPES_WITH_ID: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SurveyContract.SurveyEntry.TABLE_RESULTS_TYPES,
                        projection,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                return retCursor;
            }*/

            default: {
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
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

            case ANSWERS_ASSESSMENT: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildAnswersAssessmentUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            case ANSWERS_HISTORY: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS, null, values);
                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildAnswersHistoryUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            case SURVEYS: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_SURVEYS, null, values);
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildSurveysUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            case DOMAINS: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_DOMAINS, null, values);
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildDomainsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            case RESULTS: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_RESULTS, null, values);
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildDomainsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

           /* case RESULTS_TYPES: {
                _id = db.insert(SurveyContract.SurveyEntry.TABLE_RESULTS_TYPES, null, values);
                if (_id > 0) {
                    returnUri = SurveyContract.SurveyEntry.buildDomainsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }*/


            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch (match) {

            case SECTIONS: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_SECTIONS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_SECTIONS + "'");
                break;
            }

            case SECTIONS_WITH_ID: {

//TODO:
            }
            case QUESTIONS: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_QUESTIONS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_QUESTIONS + "'");
                break;
            }
            case USERS: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_USERS, selection, selectionArgs);
/*                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_USERS + "'");*/
                break;
            }
            case ANSWERS_ASSESSMENT: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS + "'");
                break;
            }
            case ANSWERS_HISTORY: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS + "'");
                break;
            }
            case DOMAINS: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_DOMAINS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_DOMAINS + "'");
                break;
            }
            case SURVEYS: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_SURVEYS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_SURVEYS + "'");
                break;
            }

            case RESULTS: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_RESULTS, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_RESULTS + "'");
                break;
            }

          /*  case RESULTS_TYPES: {
                numDeleted = db.delete(SurveyContract.SurveyEntry.TABLE_RESULTS_TYPES, selection, selectionArgs);
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        SurveyContract.SurveyEntry.TABLE_RESULTS_TYPES + "'");
                break;
            }*/

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
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




   /* @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case :
            // allows for multiple transactions
            db.beginTransaction();

            // keep track of successful inserts
            int numInserted = 0;
            try {
                for (ContentValues value : values) {
                    if (value == null) {
                        throw new IllegalArgumentException("Cannot have null content values");
                    }
                    long _id = -1;
                    try {
                        _id = db.insertOrThrow(SurveyContract.SurveyEntry.,
                                null, value);
                    } catch (SQLiteConstraintException e) {
                        Log.w(LOG_TAG, "Attempting to insert " +
                                value.getAsString(
                                        SurveyContract.SurveyEntry.)
                                + " but value is already in database.");
                    }
                    if (_id != -1) {
                        numInserted++;
                    }
                }
                if (numInserted > 0) {
                    // If no errors, declare a successful transaction.
                    // database will not populate if this is not called
                    db.setTransactionSuccessful();
                }
            } finally {
                // all transactions occur at once
                db.endTransaction();
            }
            if (numInserted > 0) {
                // if there was successful insertion, notify the content resolver that there
                // was a change
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return numInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }*/

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int numUpdated = 0;

        if (contentValues == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }
        switch (sUriMatcher.match(uri)) {
            case SECTIONS: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_SECTIONS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case SECTIONS_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_SECTIONS,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case DOMAINS: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_DOMAINS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case DOMAINS_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_DOMAINS,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case QUESTIONS: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_QUESTIONS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case QUESTIONS_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_QUESTIONS,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case ANSWERS_ASSESSMENT: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case ANSWERS_ASSESSMENT_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case ANSWERS_HISTORY: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case ANSWERS_HISTORY_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case SURVEYS: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_SURVEYS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case SURVEYS_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_SURVEYS,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            case USERS: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_USERS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case USERS_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_USERS,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }

            case RESULTS: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_RESULTS,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }

            case RESULTS_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_RESULTS,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }

           /* case RESULTS_TYPES: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_RESULTS_TYPES,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }

            case RESULTS_TYPES_WITH_ID: {
                numUpdated = db.update(SurveyContract.SurveyEntry.TABLE_RESULTS_TYPES,
                        contentValues,
                        SurveyContract.SurveyEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                break;
            }*/

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }

}