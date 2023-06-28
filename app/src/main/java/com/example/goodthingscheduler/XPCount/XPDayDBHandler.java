package com.example.goodthingscheduler.XPCount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.goodthingscheduler.Calendar.CalendarUtils;

import java.util.ArrayList;
import java.util.Objects;

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
        //Log.i("XP DB adding",xpCount.getDate()+" "+xpCount.getXp());
        Log.i("XPDB addDayXP","date: "+xpCount.getDate()+" xp: "+xpCount.getXp());

        db.insert(TABLE_NAME,null,values);
        //db.insertWithOnConflict(TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public XPCountModel todayXP(String dayDate){
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        XPCountModel todayXP = new XPCountModel(-1, "no xp for date", 1);
       // XPCountModel todayXP = null;
        //Log.i("xp in XPDB todayXP 1",dayDate+" "+todayXP.getDate());

        if (cursor.moveToFirst()) {
            ///Log.i("XP DB","dayDate: "+dayDate+" date in DB: "+date);
            do {
                String date = cursor.getString(1);
                if(date.equals(dayDate)){
                    int id = Integer.parseInt(cursor.getString(0));
                    int xp = Integer.parseInt(cursor.getString(2));
                    todayXP = new XPCountModel(id, date, xp);
                    //Log.i("xp in if statement, XPDB todayXP","selected day: "+dayDate+" DB entry date: "+date+" DB entry XP: "+xp);
                    //Log.i("xp in if statement, XPDB todayXP","selected day: "+dayDate+" DB entry date: "+todayXP.getDate()+" DB entry XP: "+todayXP.getXp());
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        //Log.i("xp in XPDB todayXP","selected day: "+dayDate+" DB entry date: "+todayXP.getDate()+" DB entry XP: "+todayXP.getXp());
        return todayXP;
    }

    public void updateDayXP(XPCountModel xpCount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(DATE,xpCount.getDate());
        values.put(XP,xpCount.getXp());

        db.update(TABLE_NAME, values, DATE + " = ?", new String[]{String.valueOf(xpCount.getDate())});

        //db.update(TABLE_NAME, values, ID_COL + " = ?", new String[]{String.valueOf(xpCount.getId())});
    //    Log.i("updated Habit","true, "+"habit Date "+habit.getDate()+" status "+habit.getStatus()+" task "+habit.getTask()+" detail "+habit.getDetail()+" id "+habit.getId());
        //Log.i("XP ID is",Integer.toString(xpCount.getId()));
        db.close();
    }


    public Integer[] TotalXPInMonth() {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> storeStates = new ArrayList<>();

        int daysSoFar = CalendarUtils.selectedDate.getDayOfMonth();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
              //  for(int i=0; i < daysInMonth.size(); i++) {
                    String date = cursor.getString(1);
                 //   if (date.equals(daysInMonth.get(i).toString())){
                        int id = Integer.parseInt(cursor.getString(0));
                        int xp = Integer.parseInt(cursor.getString(2));
                        if(id==1){
                            storeStates.add(xp);
                        }else{
                            storeStates.add(xp+storeStates.get(storeStates.size()-1));
                        }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        Integer[] xpList = new Integer[storeStates.size()];

        // ArrayList to Array Conversion
        for (int i = 0; i < storeStates.size(); i++)
            xpList[i] = storeStates.get(i);
        return xpList;
    }


}
