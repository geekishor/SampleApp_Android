package com.healthcamp.healthapp.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ITH-143 on 08-Jul-17.
 */

public class DateTimeFormatter {
    public static String FormatDate(String date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-mm");
        Date formattedDate;
        try{
            formattedDate = dateFormat.parse(date);
            String newFormattedDate = dateFormat.format(formattedDate);
            return newFormattedDate;
        }catch (ParseException e){
            e.printStackTrace();
            return  "";
        }
    }
}
