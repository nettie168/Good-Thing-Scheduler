package com.example.goodthingscheduler.Calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class CalendarUtils {
    public static LocalDate selectedDate;

    public static String dateToStart;
    public static String dateToEnd;

    public static String formattedDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMM yyyy");
        return date.format(formatter);
    }

    /*public static String formattedTime(LocalTime time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);
    }*/

   /* public static ArrayList<LocalDate> daysInMonthArray(LocalDate date)
    {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
                daysInMonthArray.add(null);
            else
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i - dayOfWeek));
        }
        return  daysInMonthArray;
    }*/


    public static ArrayList<LocalDate> daysInMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

       // ArrayList<String> daysInLastMonthArray = new ArrayList<>();
     //   ArrayList<String> daysInNextMonthArray = new ArrayList<>();
        //LocalDate firstOfLastMonth = selectedDate.minusMonths(1).withDayOfMonth(1);
        int daysInLastMonth = yearMonth.minusMonths(1).lengthOfMonth();
       // int daysInNextMonth = yearMonth.plusMonths(1).lengthOfMonth();

        for(int i = 1; i<= 42; i++){ //i<=42
            if(i <= dayOfWeek){
                //daysInMonthArray.add(null);
                daysInMonthArray.add(LocalDate.of(yearMonth.minusMonths(1).getYear(),selectedDate.minusMonths(1).getMonth(),daysInLastMonth-dayOfWeek+i));
                // daysInMonthArray.add(LocalDate.of((daysInLastMonth-dayOfWeek+i)));
               // daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.minusMonths(1).getMonth(),i));
            }else if(i > daysInMonth + dayOfWeek){ // && i > 40){
                //daysInMonthArray.add(null);
                daysInMonthArray.add(LocalDate.of(yearMonth.plusMonths(1).getYear(),selectedDate.plusMonths(1).getMonth(),i-daysInMonth-dayOfWeek));
            }
          //  else if(i > daysInMonth + dayOfWeek && i >40){
            //    daysInMonthArray.add(LocalDate.ofEpochDay(daysInNextMonth-i-1));
            //}
            else{
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),i-dayOfWeek));
              //  daysInMonthArray.add(LocalDate.ofEpochDay(i - dayOfWeek));
            }
        }

     /*   for(int i = 1; i<= 42; i++){ //i<=42
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek){
            //daysInMonthArray.add("");
                daysInMonthArray.add(String.valueOf(daysInLastMonth-dayOfWeek+i));
            }
            else{
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        } */
        return daysInMonthArray;
    }

    public static LocalDate toLocalDate(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale( Locale.UK );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        return LocalDate.parse(dateString, formatter);
    }

    public static ArrayList<LocalDate> daysInXPMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
       // LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        //int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i<= 14; i++){ //i<=daysInMonth
                daysInMonthArray.add(LocalDate.of(yearMonth.plusMonths(1).getYear(),date.getMonth(),i));
        }

        return daysInMonthArray;
    }



}
