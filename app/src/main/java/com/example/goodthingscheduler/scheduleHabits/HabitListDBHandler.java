package com.example.goodthingscheduler.scheduleHabits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.goodthingscheduler.Calendar.CalendarUtils;

import java.util.ArrayList;

public class HabitListDBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "HabitsListDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "HabitListTable";
    private static final String HABIT_ID = "id";
    private static final String ROUTINE = "routine";
    private static final String HABIT_NAME = "habitName";
    private static final String DETAIL = "detail";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String TARGET = "target";
    private static final String DIRECTION = "direction";
    private static final String STATUS = "status";
    private static final String DAYS_OF_WEEK = "days";
    private static final String FREQUENCY = "frequency";
    private static final String ON_DATE_NEXT = "onDateNext";

    //    private static final String TIME_TIL_NEXT = "countdown";
    private static final String HABIT_IMG_ID = "habitImgId";


    public HabitListDBHandler(Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }


    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + HABIT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROUTINE + " TEXT, "
                + HABIT_NAME + " TEXT, "
                + DETAIL + " TEXT, "
                + START_DATE + " TEXT, "
                + END_DATE + " TEXT, "
                + TARGET + " INTEGER, "
                + DIRECTION + " TEXT, "
                + STATUS + " INTEGER, "
                + DAYS_OF_WEEK + " TEXT, "
                + FREQUENCY + " INTEGER, "
                + ON_DATE_NEXT + " TEXT, "
                + HABIT_IMG_ID + " INTEGER)";

            db.execSQL(query);
    }

    public void addHabit(HabitModel habit){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ROUTINE, habit.getRoutine());
        values.put(HABIT_NAME, habit.getTask());
        values.put(DETAIL, habit.getDetail());
        values.put(START_DATE, habit.getStartDate());
        values.put(END_DATE, habit.getEndDate());
        values.put(TARGET, habit.getTarget());
        values.put(DIRECTION, habit.getDirection());
        values.put(STATUS, habit.getStatus());
        values.put(DAYS_OF_WEEK, habit.getDaysOfWeek());
        values.put(FREQUENCY, habit.getFrequency());
        values.put(ON_DATE_NEXT, habit.getOnDateNext());
        values.put(HABIT_IMG_ID, habit.getHabitImgId());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //sees if table already exists (?)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

  /*  public ArrayList<String> listRoutinesInDay() {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> storeRoutines = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String routine = cursor.getString(2);
                String date = cursor.getString(1);
                    if(date.equals(CalendarUtils.selectedDate.toString()) &! storeRoutines.contains(routine)){
                        storeRoutines.add(routine);
                    }
                }
                while (cursor.moveToNext());
        }
        cursor.close();
        return storeRoutines;
    }*/

    public ArrayList<RoutineModel> listHabitsInRoutinesInDay(ArrayList<RoutineModel> routineName) {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<RoutineModel> storeRoutines = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);

        for(int i = 0; i < routineName.size(); i++){
            ArrayList<HabitModel> habitsInRoutine = new ArrayList<>();
          //  Log.i("isInLoop","yup");

            if (cursor.moveToFirst()) {
            do {
                String onDateNext = cursor.getString(11);
                String routine = cursor.getString(1);
                String daysOfWeek = cursor.getString(9);

                if(routine.equals(routineName.get(i).getRoutine())){
                    int id = Integer.parseInt(cursor.getString(0));
                    String task = cursor.getString(2);
                    String detail = cursor.getString(3);
                    String startDate = cursor.getString(4);
                    String endDate = cursor.getString(5);
                    int target = Integer.parseInt(cursor.getString(6));
                    String direction = cursor.getString(7);
                    int status = Integer.parseInt(cursor.getString(8));
                    int frequency = Integer.parseInt(cursor.getString(10));
                    int habitImgId = Integer.parseInt(cursor.getString(12));

                    habitsInRoutine.add(new HabitModel(id, routine, task, detail, startDate, endDate, target, direction, status, daysOfWeek, frequency, onDateNext, habitImgId));
                }
            }
            while (cursor.moveToNext());
        }
            if(!habitsInRoutine.isEmpty()){
          //      Log.i("routine2",habitsInRoutine.get(0).routine);
            //    String routine, int startHour, int startMinute, int endHour, int endMinute, String daysOfWeek, int openClosed, ArrayList<HabitModel> habitArrayList
              //  storeRoutines.add(new RoutineModel(habitsInRoutine.get(0).routine, habitsInRoutine));
              //  for(int j = 0; j < routineName.size(); j++){
                    if(routineName.get(i).getRoutine().equals(habitsInRoutine.get(0).getRoutine())){
                        //String routine, int startHour, int startMinute, int endHour, int endMinute, String daysOfWeek, int openClosed, ArrayList<HabitModel> habitArrayList
                        storeRoutines.add(new RoutineModel(routineName.get(i).getId(),routineName.get(i).getRoutine(), routineName.get(i).getStartHour(), routineName.get(i).getStartMinute(),
                                routineName.get(i).getEndHour(), routineName.get(i).getEndMinute(),
                                routineName.get(i).getDaysOfWeek(), routineName.get(i).getOpenClosed(), habitsInRoutine));
                    }
                //}


            }
        }
        cursor.close();
        return storeRoutines;
    }

    public void updateHabit(HabitModel habit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ROUTINE, habit.getRoutine());
        values.put(HABIT_NAME, habit.getTask());
        values.put(DETAIL, habit.getDetail());
        values.put(START_DATE, habit.getStartDate());
        values.put(END_DATE, habit.getEndDate());
        values.put(TARGET, habit.getTarget());
        values.put(DIRECTION, habit.getDirection());
        values.put(STATUS, habit.getStatus());
        values.put(DAYS_OF_WEEK, habit.getDaysOfWeek());
        values.put(FREQUENCY, habit.getFrequency());
        values.put(ON_DATE_NEXT, habit.getOnDateNext());
        values.put(HABIT_IMG_ID, habit.getHabitImgId());

        db.update(TABLE_NAME, values, HABIT_ID + " = ?", new String[]{String.valueOf(habit.getId())});
     //   Log.i("updated Habit","true, "+"habit Date "+habit.getDate()+" status "+habit.getStatus()+" task "+habit.getTask()+" detail "+habit.getDetail()+" id "+habit.getId());
       // Log.i("ID is",Integer.toString(habit.getId()));
        db.close();
    }

    public void deleteHabit(HabitModel habit) {
        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, HABIT_ID + " = ?", new String[]{String.valueOf(habit.getId())});
        db.close();
    }

    public boolean isEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select * from "+ TABLE_NAME, null);

        return !mCursor.moveToFirst();
    }

}
