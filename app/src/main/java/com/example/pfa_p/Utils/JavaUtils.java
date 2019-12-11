package com.example.pfa_p.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class JavaUtils {


    public static String getCurrentDateTime(){

        SimpleDateFormat s = new SimpleDateFormat("ddMMMyyyyhhmmss", Locale.getDefault());
        return s.format(new Date());
    }



}



