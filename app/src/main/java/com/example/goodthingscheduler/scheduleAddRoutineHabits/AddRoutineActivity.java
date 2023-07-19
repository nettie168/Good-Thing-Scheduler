package com.example.goodthingscheduler.scheduleAddRoutineHabits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goodthingscheduler.scheduleHabits.RoutineUtils;
import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.scheduleHabits.HabitDBHandler;
import com.example.goodthingscheduler.scheduleHabits.HabitModel;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;

import java.util.ArrayList;
import java.util.Objects;

public class AddRoutineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_routine);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Routine");

        setRoutineList();
    }

   /* public void saveThing(View view){
        HabitDBHandler habitDBHandler = new HabitDBHandler(this);

        String thingString = thingET.getText().toString();

        if(!thingString.isEmpty()){
            habitDBHandler.addHabit(new HabitModel(0, CalendarUtils.selectedDate.toString(),  "Anytime",  thingString, "detail", 0, 0));
            Toast.makeText(this, "saving...", Toast.LENGTH_SHORT).show();
            finish();
            }else{
            Toast.makeText(this, "Nothing entered. Cancelling", Toast.LENGTH_SHORT).show();
            finish();
        }*

    }*/

    public void cancelThing(View view){
        finish();
        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
    }

    public void setRoutineList(){

        ArrayList<HabitModel> routineList = new ArrayList<>();

      //  if(routineList.isEmpty()) {
           // AddroutineList.add(new HabitModel("Custom", R.drawable.ic_baseline_add_24));

            routineList.add(new HabitModel("Wake Up", R.drawable.ic_baseline_wb_twilight_24));
            routineList.add(new HabitModel("Exercise", R.drawable.ic_baseline_water_drop_24));
            routineList.add(new HabitModel("Self-care", R.drawable.ic_baseline_spa_24));
            routineList.add(new HabitModel("Prepare for the Day", R.drawable.ic_baseline_wb_sunny_24));
            //  AddroutineList.add(new HabitModel("Practice Courage (Feel the Fear - and do it anyway)", R.drawable.ic_baseline_wb_sunny_24));
            routineList.add(new HabitModel("Work that works for me", R.drawable.ic_baseline_assured_workload_24));
            routineList.add(new HabitModel("Lunchtime", R.drawable.ic_baseline_ramen_dining_24));
            //   routineList.add(new HabitModel("Food shop", R.drawable.ic_baseline_water_drop_24));
            routineList.add(new HabitModel("Check Finances", R.drawable.ic_baseline_credit_score_24));
            routineList.add(new HabitModel("Do Something I Love", R.drawable.ic_baseline_self_improvement_24));
            routineList.add(new HabitModel("Time in Nature", R.drawable.baseline_air_24));
            routineList.add(new HabitModel("Growing Nature", R.drawable.baseline_yard_24));
            routineList.add(new HabitModel("Magnificent Mealtime", R.drawable.ic_baseline_local_pizza_24));
            routineList.add(new HabitModel("Spend time with Loved Ones", R.drawable.ic_baseline_diversity_3_24));
            routineList.add(new HabitModel("Spend time with Yourself", R.drawable.ic_baseline_favorite_24));
            routineList.add(new HabitModel("Tidy Spaces", R.drawable.ic_baseline_countertops_24));
            routineList.add(new HabitModel("Expand Your Horizons", R.drawable.ic_baseline_travel_explore_24));
            // AddroutineList.add(new HabitModel("Evening Self-care", R.drawable.ic_baseline_spa_24));
            routineList.add(new HabitModel("Prepare for Tomorrow", R.drawable.ic_baseline_calendar_month_24));
            //  AddroutineList.add(new HabitModel("Rest & Recharge", R.drawable.ic_baseline_battery_charging_full_24));
            //    AddroutineList.add(new HabitModel("Wind Down", R.drawable.ic_baseline_nights_stay_24));
      //  }

        RecyclerView recyclerView = findViewById(R.id.routinesToAddRV);

        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),140);

        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns); //3
       // LinearLayout layoutManager = new LinearLayout(this);
        AddRoutineAdapter addRoutineAdapter = new AddRoutineAdapter(routineList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(addRoutineAdapter);

    }
}