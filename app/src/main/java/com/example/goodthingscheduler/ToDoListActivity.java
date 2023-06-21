package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodthingscheduler.toDoAdd.ToDoAddThingActivity;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
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

    @Override
    public void onRestart() {
        super.onRestart();
        setGoodThingsRecyclerView();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_chevron_left_24);
        //getSupportActionBar().setTitle("To Dos -"+" "+CategoriesUtil.categorySelected); //string is custom name you want
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(null);
        getSupportActionBar().setElevation(0);
        //setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));

        toDoThingsDB = new ToDoThingsDB(this);

        CategoriesUtil.goodThingId = 1;
        CategoriesUtil.goodThing = "";

        setGoodTitleView();
        setBottomNavMenu();
        setAddThingFab();
        setGoodThingsRecyclerView();
    }

    private void setGoodTitleView(){
        ImageView categoryImgView = findViewById(R.id.categoryImage);
        TextView categoryTV = findViewById(R.id.categoryTextView);

        categoryTV.setText(CategoriesUtil.categorySelected);
        categoryImgView.setImageResource(CategoriesUtil.categoryImgId);
        categoryTV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, CategoriesUtil.categoryLogoId, 0);
    }

    private void setGoodThingsRecyclerView(){
        RecyclerView goodThingsRV = findViewById(R.id.goodThingsRV);

        ArrayList<String> categoryList = toDoThingsDB.listGoodCatDB();
        ArrayList<String> stateList = toDoThingsDB.listGoodStatesInCatDB(categoryList);

        if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
            thingsInState = toDoThingsDB.listAllGoodThings(stateList);
        }else {
            thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
        }

        if(thingsInState.isEmpty()){
            //ArrayList<ToDoThingModel> isEmptyList = new ArrayList<>();
            String helloMessage = "Add "+CategoriesUtil.categorySelected+" good thing or to do";
            //isEmptyList.add(new ToDoThingModel(0,"all",helloMessage,"Hello",""));
            toDoThingsDB.addGoodThing(new ToDoThingModel(0, CategoriesUtil.categorySelected,helloMessage,"","To Do", LocalDate.now().toString(), "date not set","date not set","date"));


            /*ArrayList<ToDoStatesModel> isEmptyStateList = new ArrayList<>();

            isEmptyStateList.add(new ToDoStatesModel("all","To Do",isEmptyList));

            ToDoStateAdapter goodThingsAdapter = new ToDoStateAdapter(isEmptyStateList, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            goodThingsRV.setLayoutManager(layoutManager);
            goodThingsRV.setAdapter(goodThingsAdapter);*/
        }

        if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
            thingsInState = toDoThingsDB.listAllGoodThings(stateList);
        }else {
            thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
        }

        ToDoStateAdapter goodThingsAdapter = new ToDoStateAdapter(thingsInState, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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
    }
}