package com.example.pfa_p.Database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class SurveyContract {

    public static final String CONTENT_AUTHORITY = "com.example.pfa_p.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class SurveyEntry implements BaseColumns {


        /*TABLE_SECTIONS
         * Sections table is written to database on app startup based on the data from parsed json file
         * _id is PRIMARY KEY
         * name is the name of submodule
         * survey_id is FOREIGN KEY, the same as _ID in TABLE_SURVEY
         * */
        public static final String TABLE_SECTIONS = "sections";

        public static final String SECTIONS_ID = "_id";

        public static final String SECTIONS_COLUMN_NAME = "name";

        public static final String SECTIONS_COLUMN_SURVEY_ID = "survey_id";

        public static final String SECTIONS_COLUMN_FLAG = "flag";


        /*
         * TABLE_DOMAINS
         * Domains table for domains meta data {@see readme}, written to database on app startup
         * _ID is the PRIMARY KEY
         * domain_name is the name of the domain
         * section_id is the FOREIGN KEY same as _id in TABLE_SECTIONS
         * */
        public static final String TABLE_DOMAINS = "domains";

        public static final String DOMAINS_COLUMN_ID = "_ID";

        public static final String DOMAINS_COLUMN_NAME = "domain_name";

        public static final String DOMAINS_COLUMN_SECTION_ID = "section_id";

        public static final String DOMAINS_COLUMN_FLAG = "flag";


        /*
         * TABLE_SURVEYS
         * surveys table is to keep the data organized in case the given survey is modified
         * and both new and old data is present in local database
         * written to database on app startup as this is scalar data
         * _ID is the PRIMARY KEY
         * survey_id is the version id for the survey which must be generated from the json file
         * and maintained by the developer each time a new @link R.raw#surveydata.json file is used
         * */
        public static final String TABLE_SURVEYS = "surveys";

        public static final String SURVEYS_COLUMN_PK = "_ID";

        public static final String SURVEY_COLUMN_SURVEY_ID = "survey_id";

        public static final String SURVEY_COLUMN_FLAG = "flag";


        /**
         * TABLE_QUESTIONS
         * Table for Questions meta-data ; written on app startup
         * _id is PRIMARY KEY
         * question_name is the question itself
         * section_id is FOREIGN KEY same as _id in TABLE_SECTIONS
         * domain_id is FOREIGN KEY same as _id in TABLE_DOMAINS
         * answer_type same as the answer_type parameter in the csv or json file
         * flag is
         * */
        public static final String TABLE_QUESTIONS = "questions";

        public static final String QUESTIONS_ID = "_id";

        public static final String QUESTIONS_COLUMN_NAME = "question_name";

        public static final String QUESTIONS_COLUMN_SECTION_ID = "section_id";

        public static final String QUESTIONS_COLUMN_DOMAIN_ID = "domain_id";

        public static final String QUESTIONS_COLUMN_TYPE = "answer_type";

        public static final String QUESTIONS_COLUMN_FLAG = "flag";


        /**
         * TABLE_USERS
         * Table for storing a prisoner's information
         * _id is the PRIMARY KEY
         * name is the name of the prisoner
         * inmate_id is the prisoner's inmate id
         * history_flag displays the status of history forms completed or not
         * assessment_flag displays the status of basic questionnaire and all questionnaires combined
         * total_visits_completed keeps track of number of total **completed** assessments done till date
         * flag is dirty if not synced or up to date with server
         **/
        public static final String TABLE_USERS = "user";

        public static final String USERS_ID = "_id";

        public static final String USERS_COLUMN_INMATE_ID = "inmate_id";

        public static final String USERS_COLUMN_VOLUNTEER_ID = "volunteer_id";

        public static final String USERS_COLUMN_HISTORY_FLAG = "history_flag";

        public static final String USERS_COLUMN_ASSESSMENT_FLAG = "assessment_flag";

        public static final String USERS_COLUMN_TOTAL_VISITS = "total_visits_completed";

        public static final String USERS_COLUMN_FLAG = "flag";


        /**
         * The submitted answers are recorded in two separate tables as Socio-Demographic information for each prisoner is only taken once
         * while all other information has to be taken multiple times. It allows for easy retrieval and organisation of data.
         * <p>
         * TABLE_ASSESSMENT_ANSWERS : Table to store answers for two modules - Basic Questionnaire and Assessing need for PFAP
         * TABLE_HISTORY_ANSWERS : Table to store answers for first module - Socio-Demographics
         * <p>
         * Both tables have the following column names as common :
         * _id is PRIMARY KEY
         * user_id is FOREIGN KEY and is the same as _id in TABLE_USERS
         * question_id is FOREIGN KEY and is same as _id in TABLE_QUESTIONS
         * response is the answer provided to the given question_id
         * flag maintains if the data is up to date with server
         * <p>
         * TABLE_ASSESSMENT has the following extra columns
         * visit_number maintains the answer recorded in a different visit number for the same prisoner
         */
        public static final String TABLE_ASSESSMENT_ANSWERS = "assessment_table";

        public static final String TABLE_HISTORY_ANSWERS = "history_table";

        public static final String ANSWERS_ID = "_id";

        public static final String ANSWERS_COLUMN_USER_ID = "user_id";

        public static final String ANSWERS_COLUMN_VOLUNTEER_ID = "volunteer_id";

        public static final String ANSWERS_COLUMN_TIME_STAMP = "time_stamp";

        public static final String ANSWERS_COLUMN_QUESTION_ID = "question_id";

        public static final String ANSWERS_COLUMN_RESPONSE = "response";

        public static final String ANSWERS_COLUMN_FLAG = "flag";

        public static final String ANSWERS_COLUMN_VISIT_NUMBER = "visit_number";

        public static final String ANSWERS_COLUMN_DESPONDENCY = "despondency";


        /**
         * TABLE_RESULTS
         */

        public static final String TABLE_RESULTS = "results_table";

        public static final String RESULTS_ID = "_id";

        public static final String RESULTS_PRISONER_ID = "prisoner_id";

        public static final String RESULTS_COLUMN_VISIT_NUMBER = "visit_number";

        public static final String RESULTS_SURVEY_ID = "survey_id";

        public static final String RESULTS_VOLUNTEER_ID ="volunteer_id";

        public static final String RESULTS_TIME_STAMP = "time_stamp";

        public static final String RESULTS_COLUMN_HISTORY_FLAG = "history_flag";

        public static final String RESULTS_COLUMN_ASSESSMENT_FLAG = "assessment_flag";

        public static final String RESULTS_JSON = "result";

        public static final String RESULTS_COLUMN_FLAG = "flag";


        //   public static final String RESULTS_DOMAIN_ID = "domain_id";



        /**
         * TABLE_RESULTS_TYPES
         */

      /*  public static final String TABLE_RESULTS_TYPES = "results_types_table";

        public static final String RESULTS_TYPE_ID = "_id" ;

        public static final String RESULTS_TYPES_DOMAIN_ID = "domain_id";

        public static final String RESULTS_TYPE_NAMES = "name";*/








        /**
         * Content Uris for all tables
         */
        public static final Uri TABLE_SECTIONS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_SECTIONS).build();

        public static final Uri TABLE_QUESTIONS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_QUESTIONS).build();

        public static final Uri TABLE_USERS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_USERS).build();

        public static final Uri TABLE_ASSESSMENT_ANSWERS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_ASSESSMENT_ANSWERS).build();

        public static final Uri TABLE_HISTORY_ANSWERS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_HISTORY_ANSWERS).build();

        public static final Uri TABLE_DOMAINS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_DOMAINS).build();

        public static final Uri TABLE_SURVEYS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_SURVEYS).build();

   //     public static final Uri TABLE_RESULTS_TYPES_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_RESULTS_TYPES).build();

        public static final Uri TABLE_RESULTS_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_RESULTS).build();


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

  //      public static final String CONTENT_DIR_TYPE_RESULTS_TYPES = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RESULTS_TYPES;

  //      public static final String CONTENT_ITEM_TYPE_RESULTS_TYPES = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RESULTS_TYPES;

        public static final String CONTENT_DIR_TYPE_RESULTS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RESULTS;

        public static final String CONTENT_ITEM_TYPE_RESULTS = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RESULTS;


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

        static Uri buildResultsUri(long id) {
            return ContentUris.withAppendedId(TABLE_RESULTS_CONTENT_URI, id);
        }
    }
}