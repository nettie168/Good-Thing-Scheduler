package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.goodthingscheduler.Calendar.CalendarAdapter;
import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.toDoAdd.ToDoAddThingActivity;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class CalendarActivity extends AppCompatActivity {

    @Override
    public void onRestart() {
        super.onRestart();
        setViews();//When BACK BUTTON is pressed, the activity on the stack is restarted
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Objects.requireNonNull(getSupportActionBar()).hide();
       // ActionBar actionBar = getSupportActionBar();

        if(CalendarUtils.selectedDate==null){
            CalendarUtils.selectedDate = LocalDate.now();
         }

        if(CategoriesUtil.filteredCategories.isEmpty()){
            CategoriesUtil.filteredCategories.add("All Good Things (To Do)");
        }

        ///ConstraintLayout calendarLayout = findViewById(R.id.calendarLayout);

     /*   calendarLayout.setOnTouchListener(new OnSwipeTouchListener(CalendarActivity.this){
            public void onSwipeLeft() {
                //Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
                Toast.makeText(CalendarActivity.this, "swipe one way", Toast.LENGTH_SHORT).show();
                previousMonthAction();
            }
            public void onSwipeBottom() {
                //.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                Toast.makeText(CalendarActivity.this, "swipe other way", Toast.LENGTH_SHORT).show();
                nextMonthAction();
            }
        });*/


        setViews();
        setBottomNavMenu();
        previousMonthAction();
        nextMonthAction();
    }


    private void setViews() {
        RecyclerView calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        TextView monthYearText = findViewById(R.id.monthYearTV);
        FloatingActionButton addNewTask = findViewById(R.id.calendarFAB);

        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));

        CalendarAdapter calendarAdapter = new CalendarAdapter(new ArrayList<>(), this);
        calendarRecyclerView.setAdapter(calendarAdapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);

        CategoriesUtil.goodThingId=-1;
        CategoriesUtil.goodThing="";
        CategoriesUtil.stateSelected="To Do";

        new CalendarAsyncTask(calendarAdapter).execute();

        addNewTask.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ToDoAddThingActivity.class)));
    }

    private static class CalendarAsyncTask extends AsyncTask<Void, Void, ArrayList<LocalDate>> {
        private final WeakReference<CalendarAdapter> adapterReference;

        CalendarAsyncTask(CalendarAdapter adapter) {
            adapterReference = new WeakReference<>(adapter);
        }

        @Override
        protected ArrayList<LocalDate> doInBackground(Void... voids) {
            // Perform database list on a background thread
            return CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate);
        }

        @Override
        protected void onPostExecute(ArrayList<LocalDate> data) {
            // Update the RecyclerView adapter with the retrieved data
            CalendarAdapter adapter = adapterReference.get();

            if (adapter != null) {
                adapter.setData(data);
            }
        }
    }



    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy");
        return date.format(formatter);
    }

    public void setBottomNavMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.calendar);

        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(item -> {});

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.goodThings) {
                finish();
                startActivity(new Intent(getApplicationContext(), ToDoListActivity.class));
                return true;
            } else if (itemId == R.id.schedule) {
                finish();
                startActivity(new Intent(getApplicationContext(),SchedulerActivity.class));
                return true;
            } else return itemId == R.id.calendar;
        });
    }

    public void previousMonthAction(){
        ImageButton backBtn = findViewById(R.id.calendarBackBtn);

        backBtn.setOnClickListener(view -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
            setViews();
        });
    }

    public void nextMonthAction(){
        ImageButton nextBtn = findViewById(R.id.calendarNextBtn);

        nextBtn.setOnClickListener(view -> {
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
            setViews();
        });
    }

}