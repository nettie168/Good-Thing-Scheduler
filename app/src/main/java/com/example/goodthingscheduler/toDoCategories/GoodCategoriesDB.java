package com.example.goodthingscheduler.toDoCategories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GoodCategoriesDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "GoodCategoriesDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "GoodCategories";
    private static final String ID_COL = "id";
    private static final String CATEGORY = "category";
    private static final String IMG_ID = "imgId";
    private static final String LOGO_ID = "logoId";

    public GoodCategoriesDB(Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String query = "CREATE TABLE "+ TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY + " TEXT, "
                + IMG_ID + " INTEGER, "
                + LOGO_ID + " INTEGER)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addGoodCategory(GoodCategoryModel goodCategoryModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //values.put(ID_COL,goodCategoryModel.getId());
        values.put(CATEGORY,goodCategoryModel.getCategoryName());
        values.put(IMG_ID, goodCategoryModel.getImgId());
        values.put(LOGO_ID, goodCategoryModel.getLogoId());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public ArrayList<GoodCategoryModel> listAllGoodCatsDB() {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<GoodCategoryModel> storeCats = new ArrayList<>();
        //  ArrayList<GoodStatesModel> storeAllThingsInAllState = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String category = cursor.getString(1);
                int imgId = Integer.parseInt(cursor.getString(2));
                int logoId = Integer.parseInt(cursor.getString(3));

                storeCats.add(new GoodCategoryModel(id, category, imgId, logoId));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeCats;
    }

    public ArrayList<String> listCatsDB() {
        String sql = "select * from "+ TABLE_NAME;

        //creates a database for reading our database
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String > storeCats = new ArrayList<>();
        //  ArrayList<GoodStatesModel> storeAllThingsInAllState = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(1);
                storeCats.add(category);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeCats;
    }

}
