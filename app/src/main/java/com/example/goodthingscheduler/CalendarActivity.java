package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.goodthingscheduler.Calendar.CalendarAdapter;
import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.toDoAdd.ToDoAddThingActivity;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class CalendarActivity extends AppCompatActivity {

//    XPDayDBHandler xpDayDBHandler = new XPDayDBHandler(this);
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.xp_item);
        MenuItemCompat.setActionView(item, R.layout.xp_action_bar_button);
        SchedulerActivity.xpCountBtn = (Button)MenuItemCompat.getActionView(item);

        SchedulerActivity.xpCount = xpDayDBHandler.DayXP().get(0).getXp();
        SchedulerActivity.xpCountBtn.setText(String.valueOf(SchedulerActivity.xpCount));

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public void onRestart() {
        super.onRestart();
        setViews();//When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
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
        //setXPCount(0);
    }

 //   @Override
  //  public boolean onCreateOptionsMenu(Menu menu ) {
//
  //      getMenuInflater().inflate(R.menu.main, menu);
    //    return super.onCreateOptionsMenu(menu);
   // }

   // @SuppressLint("ClickableViewAccessibility")
    private void setViews() {
        RecyclerView calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        TextView monthYearText = findViewById(R.id.monthYearTV);
        FloatingActionButton addNewTask = findViewById(R.id.calendarFAB);

        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        CategoriesUtil.goodThingId=-1;
        CategoriesUtil.goodThing="";
        CategoriesUtil.stateSelected="To Do";

        addNewTask.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ToDoAddThingActivity.class)));
    }

    private String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yy");
        return date.format(formatter);
    }

    public void setBottomNavMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.calendar);


        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

         //   switch(item.getItemId()) {
            if(item.getItemId()==R.id.schedule) {
                //   case R.id.schedule:
                finish();
                startActivity(new Intent(getApplicationContext(), SchedulerActivity.class));
                overridePendingTransition(0, 0);
                //      return true;
                //  case R.id.goodThings:
            }else if(item.getItemId()==R.id.goodThings){
                    finish();
                    startActivity(new Intent(getApplicationContext(),ToDoActivity.class));
                    overridePendingTransition(0,0);
                 //   return true;
            //    case R.id.calendar:
              //      return true;
            }
            return false;
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