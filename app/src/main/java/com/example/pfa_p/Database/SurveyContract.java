package com.example.pfa_p.Database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class SurveyContract {

    public static final String CONTENT_AUTHORITY = "com.example.pfa_p";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class SurveyEntry implements BaseColumns {

        // table name - 1
        public static final String TABLE_SECTIONS = "sections";
        // columns
        public static final String SECTIONS_ID = "_id";
        public static final String SECTIONS_COLUMN_NAME = "name";

        // table name - 2
        public static final String TABLE_QUESTIONS = "questions";
        // columns
        public static final String QUESTIONS_ID = "_id";
        public static final String QUESTIONS_COLUMN_SECTION_ID = "section_id";
        public static final String QUESTIONS_COLUMN_DESCRIPTION = "description";

        // table name - 3
        public static final String TABLE_USERS = "user";
        // columns
        public static final String USERS_ID = "_id";
        public static final String USERS_COLUMN_NAME = "name";
        public static final String USERS_COLUMN_INMATE_ID = "inmate_id";
        public static final String USERS_COLUMN_FLAG = "flag";

        // table name - 4
        public static final String TABLE_ANSWERS = "answers";
        // columns
        public static final String ANSWERS_ID = "_id";
        public static final String ANSWERS_COLUMN_USER_ID = "user_id";
        public static final String ANSWERS_COLUMN_QUESTION_ID = "question_id";
        public static final String ANSWERS_COLUMN_RESPONSE = "response";
        public static final String ANSWERS_COLUMN_FLAG = "flag";

        // create content uri
        public static final Uri TABLE_SECTIONS_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_SECTIONS).build();


        public static final Uri TABLE_QUESTIONS_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_QUESTIONS).build();

        public static final Uri TABLE_USERS_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_USERS).build();

        public static final Uri TABLE_ANSWERS_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_ANSWERS).build();

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE_SECTIONS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_SECTIONS;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE_SECTIONS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_SECTIONS;

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE_QUESTIONS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_QUESTIONS;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE_QUESTIONS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_QUESTIONS;

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE_USERS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_USERS;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE_USERS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_USERS;

        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE_ANSWERS =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ANSWERS;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE_ANSWERS =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_ANSWERS;

        // for building URIs on insertion
        public static Uri buildSectionsUri(long id) {
            return ContentUris.withAppendedId(TABLE_SECTIONS_CONTENT_URI, id);
        }

        public static Uri buildQuestionsUri(long id) {
            return ContentUris.withAppendedId(TABLE_QUESTIONS_CONTENT_URI, id);
        }

        public static Uri buildUsersUri(long id) {
            return ContentUris.withAppendedId(TABLE_USERS_CONTENT_URI, id);
        }

        public static Uri buildAnswersUri(long id) {
            return ContentUris.withAppendedId(TABLE_ANSWERS_CONTENT_URI, id);
        }
    }
}