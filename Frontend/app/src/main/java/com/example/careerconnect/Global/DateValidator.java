package com.example.careerconnect.Global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator {

    /**
     * Checks if a specified date in the format MM-dd-yyyy is valid
     * @param dateToCheck given date in the format MM-dd-yyyy
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateToCheck){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setLenient(false);

        try {
            Date date = simpleDateFormat.parse(dateToCheck);
            return true;
        } catch (ParseException e){
            return false;
        }
    }
}
