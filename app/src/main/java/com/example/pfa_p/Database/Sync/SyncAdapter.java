package com.example.pfa_p.Database.Sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.pfa_p.Database.SurveyContract;
import com.example.pfa_p.SurveyDataSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * https://github.com/codepath/android_guides/wiki/Server-Synchronization-(SyncAdapter) - Shubham
 * <p>
 * <p>
 * This is used by the Android framework to perform synchronization. IMPORTANT: do NOT create
 * new Threads to perform logic, Android will do this for you; hence, the name.
 * <p>
 * The goal here to perform synchronization, is to do it efficiently as possible. We use some
 * ContentProvider features to batch our writes to the local data source. Be sure to handle all
 * possible exceptions accordingly; random crashes is not a good user-experience.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = "SYNC_ADAPTER";

    private static final int RESPONSE_CODE_ERROR = 404;

    private static final int RESPONSE_CODE_SUCCESSFUL = 100;



    /**
     * This gives us access to our local data source.
     */
    private final ContentResolver resolver;


    public SyncAdapter(Context c, boolean autoInit) {
        this(c, autoInit, false);
    }

    public SyncAdapter(Context c, boolean autoInit, boolean parallelSync) {
        super(c, autoInit, parallelSync);
        this.resolver = c.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        String url = "http://mhfindia.org/apps/jsonpostdata.php";
        performPostCall(url);

        //TODO: manage synacadapter timing.

    }

    public String performPostCall(String requestURL) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");

            Log.d(TAG, "11 - url : " + requestURL);

            /*
             * JSON
             */

            JSONArray array = SurveyDataSingleton.getInstance(getContext()).getExportableDatabaseInJSON(getContext());

            Log.d(TAG, "onPerformSync Called: " + array.toString());
            //
            // TODO: get json from results

            String str = array.toString();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(str);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
            //     OutputStream os = conn.getOutputStream();

/*
            int responseCode = conn.getResponseCode();

            Log.e(TAG, "13 - responseCode : " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.e(TAG, "14 - HTTP_OK");

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                Log.e(TAG, "14 - False - HTTP_OK");
                response = "";
            }*/

            /*if (conn.getResponseCode() == RESPONSE_CODE_SUCCESSFUL) {

                markDirtyAsSynced(getContext());
            } else {
                // do nothing;
            }*/


            Log.d(TAG, String.valueOf(conn.getResponseCode()));
            Log.d(TAG, conn.getResponseMessage());
        } catch (Exception e) {
            Log.e(TAG, "Connection Failed" );
            e.printStackTrace();
        }

        return response;
    }

    public void forceSync(){
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_FORCE, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(null, SurveyContract.CONTENT_AUTHORITY, bundle);
    }


    private void markDirtyAsSynced(Context context) {
        String selection = "flag";


        String[] selectionArgsBefore = new String[]{"dirty"};

        String[] selectionArgsAfter = new String[]{"syncing"};

        //   String[] selectionArgsFinal = new String[] {"uptodate"};

        ContentValues cv = new ContentValues();
        cv.put("flag", "uptodate");

        //       String tableName = getTableName(SurveyProvider.sUriMatcher.match(tableuri));
        //  JSONObject obj;

        SurveyDataSingleton singleton = SurveyDataSingleton.getInstance(context);

        // int currentUser = singleton.getUserCount();

        //   List<Uri> updatedTables = singleton.getCurrentSessionData().get(currentUser).getTablesUpdated();

        // for (Uri table : updatedTables) {
        ContentResolver resolver = context.getContentResolver();

        resolver.update(SurveyContract.SurveyEntry.TABLE_USERS_CONTENT_URI, cv, selection, selectionArgsAfter);
        resolver.update(SurveyContract.SurveyEntry.TABLE_SURVEYS_CONTENT_URI, cv, selection, selectionArgsAfter);
        resolver.update(SurveyContract.SurveyEntry.TABLE_SECTIONS_CONTENT_URI, cv, selection, selectionArgsAfter);
        resolver.update(SurveyContract.SurveyEntry.TABLE_DOMAINS_CONTENT_URI, cv, selection, selectionArgsAfter);
        resolver.update(SurveyContract.SurveyEntry.TABLE_QUESTIONS_CONTENT_URI, cv, selection, selectionArgsAfter);
        resolver.update(SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, cv, selection, selectionArgsAfter);
        resolver.update(SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, cv, selection, selectionArgsAfter);
        resolver.update(SurveyContract.SurveyEntry.TABLE_RESULTS_CONTENT_URI, cv, selection, selectionArgsAfter);
        //   Cursor cursor = context.getContentResolver().query(tableuri, null, selection, selectionArgsAfter, null);

        //    context.getContentResolver().update(tableuri, cv,  selection, selectionArgs);


        // } //TODO: Note: if this doesn't work, update all tables individually

    }

    public JSONArray getExportableDatabaseInJSON() {
        JSONArray arr = new JSONArray();
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_USERS_CONTENT_URI, getContext()));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_SURVEYS_CONTENT_URI, getContext()));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_SECTIONS_CONTENT_URI, getContext()));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_DOMAINS_CONTENT_URI, getContext()));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_QUESTIONS_CONTENT_URI, getContext()));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_HISTORY_ANSWERS_CONTENT_URI, getContext()));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, getContext()));
        arr.put(getCursorFromTable(SurveyContract.SurveyEntry.TABLE_RESULTS_CONTENT_URI, getContext()));
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


}


