package com.example.pfa_p.Database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class SurveyContract {

    public static final String CONTENT_AUTHORITY = "com.example.pfa_p.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class SurveyEntry implements BaseColumns {

        // table name - 1
        public static final String TABLE_SECTIONS = "sections";
        // columns
        public static final String SECTIONS_ID = "_id";
        public static final String SECTIONS_COLUMN_NAME = "name";
        public static final String SECTIONS_COLUMN_SURVEY_ID = "survey_id";


        public static final String TABLE_DOMAINS = "domains";
        public static final String DOMAINS_COLUMN_ID = "_ID";
        public static final String DOMAINS_COLUMN_NAME = "domain_name";
        public static final String DOMAINS_COLUMN_SECTION_ID = "section_id";
        public static final String DOMAINS_COLUMN_SURVEY_ID = "survey_id";


        public static final String TABLE_SURVEYS = "surveys";
        public static final String SURVEYS_COLUMN_PK = "_ID";
        public static final String SURVEY_COLUMN_SURVEY_ID = "survey_id";

        // table name - 2
        public static final String TABLE_QUESTIONS = "questions";
        public static final String QUESTIONS_ID = "_id";
        public static final String QUESTIONS_COLUMN_NAME = "question_name";
        public static final String QUESTIONS_COLUMN_SURVEY_ID = "survey_id";
        public static final String QUESTIONS_COLUMN_SECTION_ID = "section_id";
        public static final String QUESTIONS_COLUMN_DOMAIN_ID = "domain_id";
        public static final String QUESTIONS_COLUMN_TYPE = "answer_type";
        public static final String QUESTIONS_COLUMN_FLAG = "flag";

        // table name - 3
        public static final String TABLE_USERS = "user";
        public static final String USERS_ID = "_id";
        public static final String USERS_COLUMN_NAME = "name";
        public static final String USERS_COLUMN_INMATE_ID = "inmate_id";
        public static final String USERS_COLUMN_HISTORY_FLAG = "history_flag";
        public static final String USERS_COLUMN_ASSESSMENT_FLAG = "assessment_flag";
        public static final String USERS_COLUMN_TOTAL_VISITS = "total_visits_completed";
        public static final String USERS_COLUMN_FLAG = "flag";

        // table name - 4 and 5
        public static final String TABLE_ASSESSMENT_ANSWERS = "assessment_table";
        public static final String TABLE_HISTORY_ANSWERS = "history_table";
        public static final String ANSWERS_ID = "_id";
        public static final String ANSWERS_COLUMN_USER_ID = "user_id";
        public static final String ANSWERS_COLUMN_QUESTION_ID = "question_id";
        public static final String ANSWERS_COLUMN_RESPONSE = "response";
        public static final String ANSWERS_COLUMN_FLAG = "flag";
        public static final String ANSWERS_COLUMN_VISIT_NUMBER = "visit_number";
        public static final String ANSWERS_COLUMN_SURVEY_ID = "survey_id";

        // create content uri
        public static final Uri TABLE_SECTIONS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_SECTIONS).build();
        public static final Uri TABLE_QUESTIONS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_QUESTIONS).build();
        public static final Uri TABLE_USERS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_USERS).build();
        public static final Uri TABLE_ASSESSMENT_ANSWERS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_ASSESSMENT_ANSWERS).build();
        public static final Uri TABLE_HISTORY_ANSWERS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_HISTORY_ANSWERS).build();
        public static final Uri TABLE_DOMAINS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_DOMAINS).build();
        public static final Uri TABLE_SURVEYS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_SURVEYS).build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE_SECTIONS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_SECTIONS;
        public static final String CONTENT_ITEM_TYPE_SECTIONS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_SECTIONS;
        public static final String CONTENT_DIR_TYPE_QUESTIONS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_QUESTIONS;
        public static final String CONTENT_ITEM_TYPE_QUESTIONS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_QUESTIONS;
        public static final String CONTENT_DIR_TYPE_USERS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_USERS;
        public static final String CONTENT_ITEM_TYPE_USERS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_USERS;
        public static final String CONTENT_DIR_TYPE_ASSESSMENT_ANSWERS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ASSESSMENT_ANSWERS;
        public static final String CONTENT_ITEM_TYPE_ASSESSMENT_ANSWERS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ASSESSMENT_ANSWERS;
        public static final String CONTENT_DIR_TYPE_HISTORY_ANSWERS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ASSESSMENT_ANSWERS;
        public static final String CONTENT_ITEM_TYPE_HISTORY_ANSWERS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ASSESSMENT_ANSWERS;
        public static final String CONTENT_DIR_TYPE_DOMAINS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ASSESSMENT_ANSWERS;
        public static final String CONTENT_ITEM_TYPE_DOMAINS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ASSESSMENT_ANSWERS;
        public static final String CONTENT_DIR_TYPE_SURVEYS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ASSESSMENT_ANSWERS;
        public static final String CONTENT_ITEM_TYPE_SURVEYS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ASSESSMENT_ANSWERS;

        static Uri buildSectionsUri(long id) {
            return ContentUris.withAppendedId(TABLE_SECTIONS_CONTENT_URI, id);
        }

        static Uri buildQuestionsUri(long id) {
            return ContentUris.withAppendedId(TABLE_QUESTIONS_CONTENT_URI, id);
        }

        static Uri buildUsersUri(long id) {
            return ContentUris.withAppendedId(TABLE_USERS_CONTENT_URI, id);
        }

        static Uri buildAnswersAssessmentUri(long id) {
            return ContentUris.withAppendedId(TABLE_ASSESSMENT_ANSWERS_CONTENT_URI, id);
        }

        static Uri buildAnswersHistoryUri(long id) {
            return ContentUris.withAppendedId(TABLE_HISTORY_ANSWERS_CONTENT_URI, id);
        }

        static Uri buildDomainsUri(long id) {
            return ContentUris.withAppendedId(TABLE_DOMAINS_CONTENT_URI, id);
        }

        static Uri buildSurveysUri(long id) {
            return ContentUris.withAppendedId(TABLE_SURVEYS_CONTENT_URI, id);
        }
    }
}