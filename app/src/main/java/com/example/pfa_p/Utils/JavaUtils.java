package com.example.pfa_p.Utils;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class JavaUtils {


    public static String getCurrentDateTime(){

        SimpleDateFormat s = new SimpleDateFormat("MMM dd, yy hh:mm", Locale.getDefault());
        return s.format(new Date());
    }

    public static String getCurrentTimeStamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }

    public static String timeStampToDateFormatter(String timeStamp){

        Calendar cal = Calendar.getInstance(Locale.getDefault());
        long time = Long.valueOf(timeStamp);
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format("MMM dd, yyyy ", cal).toString();

    }



}



