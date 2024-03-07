package com.example.goodthingscheduler.Categories;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.ArrayList;

public class CategoriesUtil {
    public static String categorySelected;
    public static int categoryImgId;
    public static int categoryLogoId;
    public static String stateSelected;
    public static String goodThing;
    public static String inspiredBy;
    public static int goodThingId;
    public static int glassesSelected;
    public static int bowSelected;
    public static int beltSelected;
    public static ArrayList<GoodCategoryModel> categoryList = new ArrayList<>();

    public static ArrayList<String> filteredCategories = new ArrayList<>();

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (screenWidthDp / columnWidthDp + 0.5);
    }


    ///public static Boolean isCatSelected;

  /*  public static ArrayList<GoodCategoryModel> categoriesList(){
        ArrayList<GoodCategoryModel> categoryList = new ArrayList<>();
        categoryList.add(new GoodCategoryModel(0, "books", R.drawable.books, R.drawable.ic_baseline_menu_book_24));
        categoryList.add(new GoodCategoryModel(1, "films and tv", R.drawable.music, R.drawable.ic_baseline_local_movies_24));
        categoryList.add(new GoodCategoryModel(2, "tasty food", R.drawable.nature, R.drawable.ic_baseline_ramen_dining_24));
        categoryList.add(new GoodCategoryModel(3, "great bakes", R.drawable.books, R.drawable.ic_baseline_bakery_dining_24));
        categoryList.add(new GoodCategoryModel(2, "walks and hikes", R.drawable.nature, R.drawable.ic_baseline_hiking_24));
        categoryList.add(new GoodCategoryModel(2, "places to go", R.drawable.music, R.drawable.ic_baseline_attractions_24));
        categoryList.add(new GoodCategoryModel(2, "drawing", R.drawable.books, R.drawable.ic_baseline_draw_24));
        categoryList.add(new GoodCategoryModel(2, "houseplants & gardening", R.drawable.nature, R.drawable.ic_baseline_local_florist_24));
        categoryList.add(new GoodCategoryModel(2, "Miscellaneous", R.drawable.nature, R.drawable.ic_baseline_local_florist_24));

        return categoryList;
    }*/
}
