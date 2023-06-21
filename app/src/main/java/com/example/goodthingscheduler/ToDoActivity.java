package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.XPCount.XPCountModel;
import com.example.goodthingscheduler.XPCount.XPDayDBHandler;
import com.example.goodthingscheduler.toDoAdd.AddNewCategoryActivity;
import com.example.goodthingscheduler.toDoCategories.CategoryAdapter;
import com.example.goodthingscheduler.toDoCategories.GoodCategoriesDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class ToDoActivity extends AppCompatActivity {

    XPDayDBHandler xpDayDBHandler = new XPDayDBHandler(this);
    GoodCategoriesDB goodCategoriesDB;

    @Override
    public void onRestart() {
        super.onRestart();
        setCategoriesRecyclerView();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.xp_item);
        MenuItemCompat.setActionView(item, R.layout.xp_action_bar_button);
        SchedulerActivity.xpCountBtn = (Button)MenuItemCompat.getActionView(item);

        //SchedulerActivity.xpCount = 0;
        //XPDayDBHandler xpDayDBHandler = new XPDayDBHandler(this);
        if(xpDayDBHandler.DayXP().size()==0){
            Log.i("size at Cal 1 ", String.valueOf(xpDayDBHandler.DayXP().size()));
            xpDayDBHandler.addDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(),0));
        }
        Log.i("size at Cal 2 ", String.valueOf(xpDayDBHandler.DayXP().size()));
        SchedulerActivity.xpCount = xpDayDBHandler.DayXP().get(0).getXp();
        SchedulerActivity.xpCountBtn.setText(String.valueOf(SchedulerActivity.xpCount));

        //  MenuItem goodThings = (MenuItem) MenuItemCompat.setActionView(item, R.id.goodThingsItem);
       /* goodThings.setOnMenuItemClickListener(menuItem -> {
                startActivity(new Intent(getApplicationContext(),ToDoActivity.class));
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
      //  ActionBar actionBar = getSupportActionBar();
        //Objects.requireNonNull(actionBar).hide();
        //Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));
        //getSupportActionBar().setElevation(0);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Good Things");

        setBottomNavMenu();

        goodCategoriesDB = new GoodCategoriesDB(this);

        FloatingActionButton addCategoriesFAB = findViewById(R.id.addCategoriesFAB);
        addCategoriesFAB.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddNewCategoryActivity.class)));

        setCategoriesRecyclerView();
     /*   if(goodCategoriesDB.listAllGoodCatsDB().isEmpty()){
            goodCategoriesDB.addGoodCategory(new GoodCategoryModel(0, "New Category", R.drawable.naturewide160dpibag, R.drawable.ic_baseline_add_24));
            goodCategoriesDB.addGoodCategory(new GoodCategoryModel(0, "All Good Things (To Do)", R.drawable.bookswide160dpi, R.drawable.ic_baseline_favorite_24));
        }

        CategoriesUtil.categorySelected = "All Good Things (To Do)";
        CategoriesUtil.stateSelected = "To Do";
        CategoriesUtil.goodThingId = -1;
        CategoriesUtil.goodThing="";
        CategoriesUtil.categoryImgId = R.drawable.bookswide160dpi;
        CategoriesUtil.categoryLogoId =  R.drawable.ic_baseline_favorite_24;*/

        //   CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList, this);

    }

    private void setCategoriesRecyclerView(){
        RecyclerView goodCategoryRV = findViewById(R.id.goodCategoryRV);

        CategoryAdapter categoryAdapter = new CategoryAdapter(goodCategoriesDB.listAllGoodCatsDB(),this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        goodCategoryRV.setLayoutManager(layoutManager);
        goodCategoryRV.setAdapter(categoryAdapter);
    }

    public void setBottomNavMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.goodThings);


        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch(item.getItemId()) {
                case R.id.goodThings:
                    return true;
                case R.id.schedule:
                    finish();
                    startActivity(new Intent(getApplicationContext(),SchedulerActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.calendar:
                    finish();
                    startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(goodCategoriesDB != null){
            goodCategoriesDB.close();
        }
    }
}