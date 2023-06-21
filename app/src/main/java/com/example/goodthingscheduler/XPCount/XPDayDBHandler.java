package com.example.goodthingscheduler.XPCount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.time.LocalDate;
import java.util.ArrayList;

public class XPDayDBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "XPDayDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "XPDay";
    private static final String ID_COL = "id";
    private static final String DATE = "date";
    private static final String XP = "xp";

    public XPDayDBHandler(Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String query = "CREATE TABLE "+ TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATE + " TEXT, "
                + XP + " INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addDayXP(XPCountModel xpCount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DATE,xpCount.getDate());
        values.put(XP,xpCount.getXp());

      //  db.insert(TABLE_NAME,null,values);
        db.insertWithOnConflict(TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public XPCountModel todayXP(String todayDate){
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        XPCountModel todayXP = new XPCountModel(-1, "no xp for date", 0);

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            String date = cursor.getString(1);
            do {
                if(date.equals(todayDate)){
                    int id = Integer.parseInt(cursor.getString(0));
                    int xp = Integer.parseInt(cursor.getString(2));
                    todayXP = new XPCountModel(id, date, xp);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return todayXP;
    }

    public void updateDayXP(XPCountModel xpCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DATE,xpCount.getDate());
        values.put(XP,xpCount.getXp());

        db.update(TABLE_NAME, values, ID_COL + " = ?", new String[]{String.valueOf(1)});
    //    Log.i("updated Habit","true, "+"habit Date "+habit.getDate()+" status "+habit.getStatus()+" task "+habit.getTask()+" detail "+habit.getDetail()+" id "+habit.getId());
        Log.i("XP ID is",Integer.toString(xpCount.getId()));
        db.close();
    }


    public ArrayList<XPCountModel> DaysOfXP() {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<XPCountModel> storeStates = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String date = cursor.getString(1);
                int xp = Integer.parseInt(cursor.getString(2));
                storeStates.add(new XPCountModel(id,date,xp));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeStates;
    }


}
