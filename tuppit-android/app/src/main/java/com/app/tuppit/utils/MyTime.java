package com.app.tuppit.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by David on 3/9/16.
 */
public class MyTime {

    public static String getCurrentTime() {

        DateFormat timeFormat = new SimpleDateFormat("HH:MM");
        Date today = Calendar.getInstance().getTime();
        return timeFormat.format(today);
    }
}
