package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodthingscheduler.toDoAdd.CategoryTagsAdapter;
import com.example.goodthingscheduler.toDoAdd.ItemClickListener;
import com.example.goodthingscheduler.toDoAdd.ToDoAddThingActivity;
import com.example.goodthingscheduler.toDoAdd.ToDoTimesTagAdapter;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.example.goodthingscheduler.toDoCategories.GoodCategoriesDB;
import com.example.goodthingscheduler.toDoCategories.GoodCategoryModel;
import com.example.goodthingscheduler.toDoThings.CategorySelectorAdapter;
import com.example.goodthingscheduler.toDoThings.ToDoStateAdapter;
import com.example.goodthingscheduler.toDoThings.ToDoStatesModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingsDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ToDoListActivity extends AppCompatActivity {

    ToDoThingsDB toDoThingsDB;
    ArrayList<ToDoStatesModel> thingsInState;
    ItemClickListener itemClickListener;
    CategorySelectorAdapter categorySelectorAdapter;
    private GoodCategoriesDB goodCategoriesDB;

    @Override
    public void onRestart() {
        super.onRestart();
        setCategorySelector();
        setGoodTitleView();
        setGoodThingsRecyclerView();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_chevron_left_24);
        //getSupportActionBar().setTitle("To Dos -"+" "+CategoriesUtil.categorySelected); //string is custom name you want
       // getSupportActionBar().setTitle("My Good Things");
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        //getSupportActionBar().setBackgroundDrawable(null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));
        getSupportActionBar().setElevation(0);
        //setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));

        toDoThingsDB = new ToDoThingsDB(this);
        goodCategoriesDB = new GoodCategoriesDB(this);

        CategoriesUtil.goodThingId = 1;
        CategoriesUtil.goodThing = "";

        setGoodTitleView();
        setBottomNavMenu();
        setAddThingFab();
        setGoodThingsRecyclerView();
        setCategorySelector();
    }

    private void setGoodTitleView(){
     //   ImageView categoryImgView = findViewById(R.id.categoryImage);
        TextView categoryTV = findViewById(R.id.categoryTextView);

        categoryTV.setText(CategoriesUtil.categorySelected);
      //  categoryImgView.setImageResource(CategoriesUtil.categoryImgId);
      //  categoryImgView.setBackgroundColor(Color.parseColor("#70ccfd"));
        categoryTV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, CategoriesUtil.categoryLogoId, 0);
    }

    private void setCategorySelector(){
        RecyclerView categorySelectorRV = findViewById(R.id.categoryRVSelector);

        // Initialize listener
        itemClickListener = s -> {
            // Notify adapter
            categorySelectorRV.post(() -> categorySelectorAdapter.notifyDataSetChanged());
            CategoriesUtil.categorySelected = s;
            setGoodTitleView();
            setGoodThingsRecyclerView();
        };

        ArrayList<GoodCategoryModel> categoryList = goodCategoriesDB.listAllGoodCatsDB();
        categoryList.add(new GoodCategoryModel(0, "add category", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_24));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        //layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
        categorySelectorRV.setLayoutManager(layoutManager);
        categorySelectorAdapter = new CategorySelectorAdapter(categoryList, itemClickListener, this); //, thisActivity);
        categorySelectorRV.setAdapter(categorySelectorAdapter);
    }

    private void setGoodThingsRecyclerView(){
        RecyclerView goodThingsRV = findViewById(R.id.goodThingsRV);

        ArrayList<String> categoryList = toDoThingsDB.listGoodCatDB(); //seems to be working
        ArrayList<String> stateList = toDoThingsDB.listGoodStatesInCatDB(categoryList);

        if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
            thingsInState = toDoThingsDB.listAllGoodThings(stateList);
          //  Log.i("ToDoListAct","is not all things");
        }else {
            thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
        //    Log.i("ToDoListAct","is all things");
        }

        if(thingsInState.isEmpty()){
            String helloMessage = "Add "+CategoriesUtil.categorySelected+" good thing or to do";
            toDoThingsDB.addGoodThing(new ToDoThingModel(0, CategoriesUtil.categorySelected,helloMessage,"",CategoriesUtil.categoryLogoId,"#FFFFFF","To Do", LocalDate.now().toString(), "date not set","date not set","date"));
        }

        if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
            thingsInState = toDoThingsDB.listAllGoodThings(stateList);
        }else {
            thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
        }

        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),400);

        ToDoStateAdapter goodThingsAdapter = new ToDoStateAdapter(thingsInState, this);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);
        goodThingsRV.setLayoutManager(layoutManager);
        goodThingsRV.setAdapter(goodThingsAdapter);
    }

    private void setAddThingFab(){
        FloatingActionButton addNewGoodThing = findViewById(R.id.addGoodThingFab);

        addNewGoodThing.setOnClickListener(view -> {
            CategoriesUtil.goodThingId=-1;
            CategoriesUtil.goodThing="";
            startActivity(new Intent(getApplicationContext(), ToDoAddThingActivity.class));
        });

        NestedScrollView scrollView = findViewById(R.id.toDoListNestedScrollView);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // the delay of the extension of the FAB is set for 12 items
            if (scrollY > oldScrollY + 8 && addNewGoodThing.isShown()){ // && addFab.isExtended()) {
                //addFab.shrink();
                addNewGoodThing.hide();
            }

            // the delay of the extension of the FAB is set for 12 items
            if (scrollY < oldScrollY - 8){ // && !addFab.isExtended()) {
                //addFab.extend();
                addNewGoodThing.show();
            }
            // if the nestedScrollView is at the first item of the list then the
            // extended floating action should be in extended state
            if (scrollY == 0) {
                //addFab.extend();
                addNewGoodThing.show();
            }
        });

    }

    public void setBottomNavMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.goodThings);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch(item.getItemId()) {
                case R.id.calendar:
                    finish();
                    startActivity(new Intent(getApplicationContext(),CalendarActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.goodThings:
                    return true;
                case R.id.schedule:
                    finish();
                    startActivity(new Intent(getApplicationContext(),SchedulerActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(toDoThingsDB != null){
            toDoThingsDB.close();
        }
        if(goodCategoriesDB != null){
            goodCategoriesDB.close();
        }
    }
}