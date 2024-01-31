package com.example.goodthingscheduler.scheduleHabits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.goodthingscheduler.Calendar.CalendarUtils;

import java.util.ArrayList;

public class DailyHabitsDBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "DailyHabitsDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "DailyHabitsTable";
    private static final String UNIQUE_ID = "id";
   // private static final String ID_HABITDB = "habitId";
    private static final String ROUTINE = "routine";
    private static final String HABIT_NAME = "habitName";
    private static final String DATE = "date";
    private static final String STATUS = "status";
    private static final String HABIT_IMG_ID = "habitImgId";


    public DailyHabitsDBHandler(Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }


    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + UNIQUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
             //   + ID_HABITDB + " INTEGER, "
                + ROUTINE + " TEXT, "
                + HABIT_NAME + " TEXT, "
                + DATE + " TEXT, "
                + STATUS + " INTEGER, "
                + HABIT_IMG_ID + " INTEGER)";

            db.execSQL(query);
    }

    public void addHabit(HabitModel habit){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ROUTINE, habit.getRoutine());
        values.put(HABIT_NAME, habit.getTask());
        values.put(DATE, habit.getDate());
        values.put(STATUS, habit.getStatus());
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

    public ArrayList<HabitModel> listHabits(String routineName) {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<HabitModel> habitsInRoutine = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String routine = cursor.getString(1);
                String date = cursor.getString(3);
                if(routine.equals(routineName) && date.equals(CalendarUtils.selectedDate.toString())){
                    int id = Integer.parseInt(cursor.getString(0));
                    String habitName = cursor.getString(2);
                    int status = Integer.parseInt(cursor.getString(4));
                    int habitImgId = Integer.parseInt(cursor.getString(5));

                    //habitsInRoutine.add(new HabitModel(id, routine, HABIT_NAME, detail, startDate, endDate, target, direction, status, frequency, onDateNext, habitImgId));
                 //   habitsInRoutine.add(new HabitModel(id, routine, task, detail, startDate, endDate, target, direction, status, daysOfWeek, frequency, onDateNext, habitImgId));
                    habitsInRoutine.add(new HabitModel(id, routine, habitName, date, status));
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return habitsInRoutine;
    }

   /* public boolean daysDailyHabitsExist(){
        String sql = "select * from "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        boolean exists = false;

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(3);
                if(date.equals(CalendarUtils.selectedDate.toString())){
                 /*   int id = Integer.parseInt(cursor.getString(0));
                    String routine = cursor.getString(1);
                    String habitName = cursor.getString(2);
                    int status = Integer.parseInt(cursor.getString(4));
                    int habitImgId = Integer.parseInt(cursor.getString(5));
                    exists = true;
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return exists;
    }*/

    public void updateHabit(HabitModel habit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //   values.put(DATE, habit.getDate());
        // values.put(TASK, habit.getTask());
      //  values.put(DETAIL, habit.getDetail());
        values.put(STATUS,habit.getStatus());
        db.update(TABLE_NAME, values, UNIQUE_ID + " = ?", new String[]{String.valueOf(habit.getId())});
    //    Log.i("updated Habit","true, "+"habit Date "+habit.getDate()+" status "+habit.getStatus()+" task "+habit.getTask()+ " id "+habit.getId());
        // Log.i("ID is",Integer.toString(habit.getId()));
        db.close();
    }

    public void deleteHabit(HabitModel habit) {
        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, UNIQUE_ID + " = ?", new String[]{String.valueOf(habit.getId())});
        db.close();
    }


}
