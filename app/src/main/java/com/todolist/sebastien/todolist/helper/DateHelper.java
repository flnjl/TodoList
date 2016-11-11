package com.todolist.sebastien.todolist.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sebastien on 10/11/16.
 */

public class DateHelper {
    private static String format = "HH:mm:ss";
    private static SimpleDateFormat formatter = null;

    /**
     * create formatted date from timestamp.
     * @param timestamp
     * @return
     */
    public static String getFormattedDate(long timestamp) throws ParseException {
        if(formatter == null){
            formatter = new SimpleDateFormat(format);
        }
        return formatter.format(new Date(timestamp)).toString();
    }
}
