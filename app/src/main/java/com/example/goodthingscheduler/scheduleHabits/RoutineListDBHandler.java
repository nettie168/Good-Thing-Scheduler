package com.example.goodthingscheduler.scheduleHabits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.goodthingscheduler.Calendar.CalendarUtils;

import java.time.LocalTime;
import java.util.ArrayList;

public class RoutineListDBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "RoutinesDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "RoutineListTable";
    private static final String ROUTINE_ID = "id";
    private static final String ROUTINE = "routine";
    private static final String START_HOUR = "startHour";
    private static final String START_MINUTE = "startMinute";
    private static final String END_HOUR = "endHour";
    private static final String END_MINUTE = "endMinute";
    private static final String END_TIME = "endTime";
    private static final String DAYS_OF_WEEK = "days";
    private static final String HABITS_SEEN = "openClosed";


    //private static final String NO_OF_HABITS = "noOfHabits";
   // private static final String START_DATE = "startDate";
    //private static final String END_DATE = "endDate";
    //private static final String TARGET = "target";
    //private static final String DIRECTION = "direction";
    //private static final String STATUS = "status";
    //private static final String FREQUENCY = "frequency";
    //private static final String ON_DATE_NEXT = "onDateNext";
    //private static final String TIME_TIL_NEXT = "countdown";
    //private static final String ROUTINE_IMG_ID = "routineImgId";


    public RoutineListDBHandler(Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ROUTINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROUTINE + " TEXT, "
                + START_HOUR + " INTEGER, "
                + START_MINUTE + " INTEGER, "
                + END_HOUR + " INTEGER, "
                + END_MINUTE + " INTEGER, "
                + DAYS_OF_WEEK + " TEXT, "
                + HABITS_SEEN + " INTEGER) ";

               // + NO_OF_HABITS + " INTEGER, "
              //  + END_DATE + " TEXT, "
                //+ TARGET + " INTEGER, "
                //+ DIRECTION + " TEXT, "
                //+ STATUS + " INTEGER, "
              //+ FREQUENCY + " TEXT, "
               // + ON_DATE_NEXT + " TEXT, "
               // + ROUTINE_IMG_ID + " INTEGER)";

        db.execSQL(query);
    }

    public void addRoutine(RoutineModel routineModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ROUTINE, routineModel.getRoutine());
        values.put(START_HOUR, routineModel.getStartHour());
        values.put(START_MINUTE, routineModel.getStartMinute());
        values.put(END_HOUR, routineModel.getEndHour());
        values.put(END_MINUTE, routineModel.getEndMinute());
        values.put(DAYS_OF_WEEK, routineModel.getDaysOfWeek());
        values.put(HABITS_SEEN, routineModel.getOpenClosed());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public void updateRoutine(RoutineModel routineModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ROUTINE, routineModel.getRoutine());
        values.put(START_HOUR, routineModel.getStartHour());
        values.put(START_MINUTE, routineModel.getStartMinute());
        values.put(END_HOUR, routineModel.getEndHour());
        values.put(END_MINUTE, routineModel.getEndMinute());
        values.put(DAYS_OF_WEEK, routineModel.getDaysOfWeek());
        values.put(HABITS_SEEN, routineModel.getOpenClosed());

        db.update(TABLE_NAME, values, ROUTINE_ID + " = ?", new String[]{String.valueOf(routineModel.getId())});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //sees if table already exists (?)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<RoutineModel> listRoutinesInDay() {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<RoutineModel> storeRoutines = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String daysOfWeek = cursor.getString(6);
                String routine = cursor.getString(1);
               // Log.i("day of week is ", String.valueOf(CalendarUtils.selectedDate.getDayOfWeek()));
           //     Log.i("day of Week Routine ",daysOfWeek);
                int id = Integer.parseInt(cursor.getString(0));
                int startHour = Integer.parseInt(cursor.getString(2));
                int startMinute = Integer.parseInt(cursor.getString(3));
                int endHour = Integer.parseInt(cursor.getString(4));
                int endMinute = Integer.parseInt(cursor.getString(5));
                int openClosed = Integer.parseInt(cursor.getString(7));
                RoutineModel newRoutine = new RoutineModel(id, routine, startHour, startMinute, endHour, endMinute, daysOfWeek, openClosed);

                if(daysOfWeek.contains(CalendarUtils.selectedDate.getDayOfWeek().toString()) &! storeRoutines.contains(newRoutine)){
                    storeRoutines.add(newRoutine);
                    //Log.i("routine DB","day: "+CalendarUtils.selectedDate.getDayOfWeek().toString() +" routine: "+newRoutine.getRoutine());
                  //  Log.i("list Routine for "+routine,"start hr is "+startHour+" openClosed is "+openClosed+" id is "+id);
                    }
                }
                while (cursor.moveToNext());
        }
        cursor.close();
        return storeRoutines;
    }


    public void updateOpenClosed(RoutineModel routineModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HABITS_SEEN, routineModel.getOpenClosed());
      //  Log.i("update Open Closed","openCl is "+routineModel.getOpenClosed());

        db.update(TABLE_NAME, values, ROUTINE_ID + " = ?", new String[]{String.valueOf(routineModel.getId())});
      //  Log.i("update Open Closed","id is "+routineModel.getId());
        //Log.i("updated Habit","true, "+"habit Date "+habit.getDate()+" status "+habit.getStatus()+" HABIT_NAME "+habit.getHABIT_NAME()+" detail "+habit.getDetail()+" id "+habit.getId());
        //Log.i("ID is",Integer.toString(habit.getId()));
        db.close();
    }

    public void deleteRoutine(RoutineModel routineModel) {
        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, ROUTINE_ID + " = ?", new String[]{String.valueOf(routineModel.getId())});
        db.close();
    }


}
