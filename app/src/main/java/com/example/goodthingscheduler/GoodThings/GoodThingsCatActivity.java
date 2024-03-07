package com.example.goodthingscheduler.GoodThings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.goodthingscheduler.Categories.CategoriesUtil;
import com.example.goodthingscheduler.Categories.CategoryAdapter;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.SchedulerActivity;
import com.example.goodthingscheduler.ToDoListActivity;
import com.example.goodthingscheduler.XPCount.XPDayDBHandler;
import com.example.goodthingscheduler.toDoAdd.AddNewCategoryActivity;
import com.example.goodthingscheduler.Categories.GoodCategoriesDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class GoodThingsCatActivity extends AppCompatActivity {

    XPDayDBHandler xpDayDBHandler = new XPDayDBHandler(this);
    GoodCategoriesDB goodCategoriesDB;

    @Override
    public void onRestart() {
        super.onRestart();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        setCategoriesRecyclerView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Good Things");

        setBottomNavMenu();

        goodCategoriesDB = new GoodCategoriesDB(this);

        FloatingActionButton addCategoriesFAB = findViewById(R.id.addCategoriesFAB);
        addCategoriesFAB.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddNewGoodCategoryActivity.class)));

        setCategoriesRecyclerView();

    }

    private void setCategoriesRecyclerView(){
        RecyclerView goodCategoryRV = findViewById(R.id.goodCategoryRV);

        CategoryAdapter categoryAdapter = new CategoryAdapter(goodCategoriesDB.listAllGoodCatsDB(),this);
        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),400);  //400
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
        goodCategoryRV.setLayoutManager(layoutManager);
        goodCategoryRV.setAdapter(categoryAdapter);
    }

    public void setBottomNavMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.goodThings);

        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(item -> {});

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.toDos) {
                finish();
                startActivity(new Intent(getApplicationContext(), ToDoListActivity.class));
                return true;
            } else if (itemId == R.id.schedule) {
                finish();
                startActivity(new Intent(getApplicationContext(), SchedulerActivity.class));
                return true;
            } else return itemId == R.id.goodThings;
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