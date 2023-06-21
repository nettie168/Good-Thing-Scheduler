package com.example.goodthingscheduler.scheduleHabits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.goodthingscheduler.Calendar.CalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class HabitDBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "HabitsDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "HabitTable";
    private static final String ID_COL = "id";
    private static final String ROUTINE = "routine";
    private static final String TASK = "task";
    private static final String DETAIL = "detail";
    private static final String DATE = "date";
    private static final String STATUS = "status";
    private static final String HABIT_IMG_ID = "habitImgId";


    public HabitDBHandler(Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }


    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT, "
                + ROUTINE + " TEXT, "
                + TASK + " TEXT, "
                + DETAIL + " TEXT, "
                + STATUS + " INTEGER, "
                + HABIT_IMG_ID + " INTEGER)";

        db.execSQL(query);
    }

    public void addHabit(HabitModel habit){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

     //   values.put(ID_COL, habit.getId());
        values.put(DATE, habit.getDate());
        values.put(ROUTINE, habit.getRoutine());
        values.put(TASK, habit.getTask());
        values.put(DETAIL, habit.getDetail());
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

    public ArrayList<String> listRoutinesInDay(LocalDate dateSel) {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> storeRoutines = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String routine = cursor.getString(2);
                String date = cursor.getString(1);
                    if(date.equals(dateSel.toString()) &! storeRoutines.contains(routine)){
                        storeRoutines.add(routine);
                    }
                }
                while (cursor.moveToNext());
        }
        cursor.close();
        return storeRoutines;
    }

    public ArrayList<RoutineModel> listHabitsInRoutinesInDay(ArrayList<String> routineName) {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<RoutineModel> storeRoutines = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);

        for(int i = 0; i < routineName.size(); i++){
            ArrayList<HabitModel> habitsInRoutine = new ArrayList<>();

            if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(1);
                String routine = cursor.getString(2);

                if(routine.equals(routineName.get(i)) && date.equals(CalendarUtils.selectedDate.toString())){
                        int id = Integer.parseInt(cursor.getString(0));
                        String task = cursor.getString(3);
                        String detail = cursor.getString(4);
                        int status = Integer.parseInt(cursor.getString(5));
                        int imgId = Integer.parseInt(cursor.getString(6));
                        habitsInRoutine.add(new HabitModel(id, date, routine, task, detail, status, imgId));
                    }
            }
            while (cursor.moveToNext());
        }
            if(!habitsInRoutine.isEmpty()){
                storeRoutines.add(new RoutineModel(habitsInRoutine.get(0).routine, habitsInRoutine));
            }
        }
        cursor.close();
        return storeRoutines;
    }

    public void updateHabit(HabitModel habit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DATE, habit.getDate());
        values.put(TASK, habit.getTask());
        values.put(DETAIL, habit.getDetail());
        values.put(STATUS,habit.getStatus());

        db.update(TABLE_NAME, values, ID_COL + " = ?", new String[]{String.valueOf(habit.getId())});
        //Log.i("updated Habit","true, "+"habit Date "+habit.getDate()+" status "+habit.getStatus()+" task "+habit.getTask()+" detail "+habit.getDetail()+" id "+habit.getId());
        db.close();
    }

    public void deleteHabit(HabitModel habit) {
        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{String.valueOf(habit.getId())});
        db.close();
    }


}
