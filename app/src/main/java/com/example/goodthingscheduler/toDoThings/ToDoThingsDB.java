package com.example.goodthingscheduler.toDoThings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;

import java.time.LocalDate;
import java.util.ArrayList;


public class ToDoThingsDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "ToThingsDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "ToDoTable";
    private static final String ID_COL = "id";
    private static final String CATEGORY = "category";
    private static final String GOOD_THING = "goodThing";
    private static final String INSPIRED_BY = "inspiredBy";
    private static final String LOGO_ID = "logoID";
    private static final String COLOUR = "colour";

    private static final String STATE = "state";
    private static final String DATE_ADDED = "dateAdded";
    private static final String DATE_TO_START = "dateToStart";
    private static final String DATE_TO_END = "dateToEnd";
    private static final String DATES_DONE = "datesDone";


    public ToDoThingsDB(Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String query = "CREATE TABLE "+ TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY + " TEXT, "
                + GOOD_THING + " TEXT, "
                + INSPIRED_BY + " TEXT, "
                + LOGO_ID + " INTEGER, "
                + COLOUR + " TEXT, "
                + STATE + " TEXT, "
                + DATE_ADDED + " TEXT, "
                + DATE_TO_START + " TEXT, "
                + DATE_TO_END + " TEXT, "
                + DATES_DONE + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addGoodThing(ToDoThingModel goodThingModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

      //  values.put(ID_COL,goodThingModel.getId());
        values.put(CATEGORY,goodThingModel.getCategory());
        values.put(GOOD_THING, goodThingModel.getGoodThing());
        values.put(INSPIRED_BY, goodThingModel.getInspiredBy());
        values.put(LOGO_ID, goodThingModel.getLogoId());
        values.put(COLOUR, goodThingModel.getColour());
        values.put(STATE, goodThingModel.getState());
        values.put(DATE_ADDED, goodThingModel.getDateAdded());
        values.put(DATE_TO_START, goodThingModel.getDateToStart());
        values.put(DATE_TO_END, goodThingModel.getDateToEnd());
        values.put(DATES_DONE,goodThingModel.getDatesDone());

      //  db.insert(TABLE_NAME,null,values);
        db.insertWithOnConflict(TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void updateGoodThing(ToDoThingModel goodThingModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CATEGORY,goodThingModel.getCategory());
        values.put(GOOD_THING, goodThingModel.getGoodThing());
        values.put(INSPIRED_BY, goodThingModel.getInspiredBy());
        values.put(LOGO_ID, goodThingModel.getLogoId());
        values.put(COLOUR, goodThingModel.getColour());
        values.put(STATE, goodThingModel.getState());
        //values.put(DATE_ADDED, goodThingModel.getDateAdded());
        values.put(DATE_TO_START, goodThingModel.getDateToStart());
        values.put(DATE_TO_END, goodThingModel.getDateToEnd());
        values.put(DATES_DONE,goodThingModel.getDatesDone());

        db.update(TABLE_NAME, values, ID_COL + " = ?", new String[]{String.valueOf(goodThingModel.getId())});
        db.close();
    }

    public void deleteGoodThing(ToDoThingModel goodThingModel) {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(TABLE_NAME, ID_COL + " = ?", new String[]{String.valueOf(goodThingModel.getId())});
        db.close();
    }


    public ArrayList<String> listGoodCatDB() {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> storeCats = new ArrayList<>();
        //  ArrayList<GoodStatesModel> storeAllThingsInAllState = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(1);
                if(!storeCats.contains(category)){
                    storeCats.add(category);
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeCats;
    }

    public ArrayList<String> listGoodStatesInCatDB(ArrayList<String> categoryName) {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> storeStates = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(1);
                String state = cursor.getString(6);
                for (int i=0; i < categoryName.size(); i++){
                    if (category.equals(categoryName.get(i)) &! storeStates.contains(state)){
                        storeStates.add(state);
                    }
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeStates;
    }

    public ArrayList<ToDoStatesModel> listGoodThingsInStateInCatDB(String categoryName, ArrayList<String> stateName) {

        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ToDoStatesModel> storeAllThingsInAllState = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);

        //loop over the number of different "states"
        for (int i=0; i < stateName.size(); i++) {
            ArrayList<ToDoThingModel> storeThingsInState = new ArrayList<>();

            //move through SQLite db line by line
            if (cursor.moveToFirst()) {
                do {
                    String category = cursor.getString(1);
                    String state = cursor.getString(6);

                    if (category.equals(categoryName) && state.equals(stateName.get(i))) {
                        int id = Integer.parseInt(cursor.getString(0));
                        String goodThing = cursor.getString(2);
                        String inspiredBy = cursor.getString(3);
                        int logoId = Integer.parseInt(cursor.getString(4));
                        String colour = cursor.getString(5);
                        String dateAdded = cursor.getString(7);
                        String dateToStart = cursor.getString(8);
                        String dateToEnd = cursor.getString(9);
                        String datesDone = cursor.getString(10);

                        storeThingsInState.add(new ToDoThingModel(id, category, goodThing, inspiredBy, logoId, colour, state, dateAdded, dateToStart, dateToEnd, datesDone));
                    }
                }
                while (cursor.moveToNext());
            }
            if(!storeThingsInState.isEmpty()){
                storeAllThingsInAllState.add(new ToDoStatesModel(storeThingsInState.get(0).category, storeThingsInState.get(0).state, storeThingsInState));
            }
          }
        cursor.close();
        return storeAllThingsInAllState;
    }

    public ArrayList<ToDoStatesModel> listAllGoodThings(ArrayList<String> stateName) {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ToDoStatesModel> storeAllThingsInAllState = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        for (int i=0; i < stateName.size(); i++) {
            ArrayList<ToDoThingModel> storeThingsInState = new ArrayList<>();

            //move through SQLite db line by line
            if (cursor.moveToFirst()) {
                do {
                    String state = cursor.getString(6);

                    if (state.equals(stateName.get(i))) {
                        int id = Integer.parseInt(cursor.getString(0));
                        String category = cursor.getString(1);
                        String goodThing = cursor.getString(2);
                        String inspiredBy = cursor.getString(3);
                        int logoId = Integer.parseInt(cursor.getString(4));
                        String colour = cursor.getString(5);
                        String dateAdded = cursor.getString(7);
                        String dateToStart = cursor.getString(8);
                        String dateToEnd = cursor.getString(9);
                        String datesDone = cursor.getString(10);
                        storeThingsInState.add(new ToDoThingModel(id, category, goodThing, inspiredBy, logoId, colour, state, dateAdded, dateToStart, dateToEnd, datesDone));
                    }
                }
                while (cursor.moveToNext());
            }
            if (!storeThingsInState.isEmpty()) {
                storeAllThingsInAllState.add(new ToDoStatesModel(storeThingsInState.get(0).category, storeThingsInState.get(0).state, storeThingsInState));
            }
        }
        cursor.close();
            return storeAllThingsInAllState;
    }

    public ArrayList<ToDoThingModel> listToDoInDayCatFilter(LocalDate date) {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ToDoThingModel> storeTasks = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String dateToStart = cursor.getString(8);
                String dateToEnd = cursor.getString(9);
                String goodThing = cursor.getString(2);
                String category = cursor.getString(1);

                if(dateToStart!=null && dateToEnd != null) {
                    if (!dateToStart.equals("date not set") && !dateToEnd.equals("date not set")) {
                        if (date.isEqual(CalendarUtils.toLocalDate(dateToStart)) || date.isAfter(CalendarUtils.toLocalDate(dateToStart))) {
                            if (date.isEqual(CalendarUtils.toLocalDate(dateToEnd)) || date.isBefore(CalendarUtils.toLocalDate(dateToEnd))) {
                                if(CategoriesUtil.filteredCategories.contains(category) | CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
                                    int id = Integer.parseInt(cursor.getString(0));
                                    //String goodThing = cursor.getString(2);
                                    String inspiredBy = cursor.getString(3);
                                    int logoId = Integer.parseInt(cursor.getString(4));
                                    String colour = cursor.getString(5);
                                    String state = cursor.getString(6);
                                    String dateAdded = cursor.getString(7);
                                    String datesDone = cursor.getString(10);
                                    storeTasks.add(new ToDoThingModel(id, category, goodThing, inspiredBy, logoId, colour, state, dateAdded, dateToStart, dateToEnd, datesDone));
                                }
                            }
                        }
                    }
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
       /* for(int i =0; i<storeTasks.size();i++){
            String thing = storeTasks.get(i).getGoodThing();
            String dateStart = storeTasks.get(i).getDateToStart();
        }*/
        return storeTasks;
    }

    public ArrayList<ToDoThingModel> listToDoInDay(LocalDate date) {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ToDoThingModel> storeTasks = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String dateToStart = cursor.getString(8);
                String dateToEnd = cursor.getString(9);
                String goodThing = cursor.getString(2);
                String category = cursor.getString(1);
                String state = cursor.getString(6);


                if(dateToStart!=null && dateToEnd != null &! state.equals("Happy it exists")) {
                    if (!dateToStart.equals("date not set") && !dateToEnd.equals("date not set")) {
                        if (date.isEqual(CalendarUtils.toLocalDate(dateToStart)) || date.isAfter(CalendarUtils.toLocalDate(dateToStart))) {
                            if (date.isEqual(CalendarUtils.toLocalDate(dateToEnd)) || date.isBefore(CalendarUtils.toLocalDate(dateToEnd))) {
                                    int id = Integer.parseInt(cursor.getString(0));
                                    //String goodThing = cursor.getString(2);
                                    String inspiredBy = cursor.getString(3);
                                    int logoId = Integer.parseInt(cursor.getString(4));
                                    String colour = cursor.getString(5);
                                    String dateAdded = cursor.getString(7);
                                    String datesDone = cursor.getString(10);
                                    storeTasks.add(new ToDoThingModel(id, category, goodThing, inspiredBy, logoId, colour, state, dateAdded, dateToStart, dateToEnd, datesDone));
                            }
                        }
                    }
                }
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        for(int i =0; i<storeTasks.size();i++){
            String thing = storeTasks.get(i).getGoodThing();
            String dateStart = storeTasks.get(i).getDateToStart();
        }
        return storeTasks;
    }

    public boolean isEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select * from "+ TABLE_NAME, null);

        return !mCursor.moveToFirst();
    }


}
