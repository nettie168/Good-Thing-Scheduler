package com.example.goodthingscheduler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodthingscheduler.XPCount.XPCountModel;
import com.example.goodthingscheduler.XPCount.XPDayDBHandler;
import com.example.goodthingscheduler.XPCount.XPUtils;
import com.example.goodthingscheduler.XPTarget.XPGoalActivity;
import com.example.goodthingscheduler.scheduleAddRoutineHabits.AddRoutineActivity;
import com.example.goodthingscheduler.scheduleHabits.RoutineUtils;
import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.scheduleHabits.DailyHabitsDBHandler;
import com.example.goodthingscheduler.scheduleHabits.HabitListDBHandler;
import com.example.goodthingscheduler.scheduleHabits.HabitModel;
import com.example.goodthingscheduler.scheduleHabits.RoutineAdapter;
import com.example.goodthingscheduler.scheduleHabits.RoutineListDBHandler;
import com.example.goodthingscheduler.scheduleHabits.RoutineModel;
import com.example.goodthingscheduler.toDoAdd.ToDoAddThingActivity;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.example.goodthingscheduler.toDoCategories.GoodCategoriesDB;
import com.example.goodthingscheduler.toDoCategories.GoodCategoryModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingAdapter;
import com.example.goodthingscheduler.toDoThings.ToDoThingModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingsDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;

public class SchedulerActivity extends AppCompatActivity {

    //To do
    //1. Get clicking more button in Routine adapter to a) show correct days of week - done 23/05/23
    //  b) show pre-chosen habits - 25/05
    //  c) update the routine if days of week, start time changed - 25/05
    //  c ii)(have pop-up for just that day or all routines in future)
    //  d) update the routine for habits change ALL - 25/05
    //  d ii)(delete habit from certain day or all future (or all past) habits)
    // 2. Get static Utils for day XP, and update on recreate
    // 3. a) Put in more correct icons b) tidy up "rooms" c) have onboarding with choose character d) have side menu to change character (or click on "world")

    //init UI views & booleans
    private AnimationDrawable jumpingAnimation;
    Button dateView;
    Boolean isAllFabsVisible;
    Boolean isWaving;

    public Button xpCountBtn;



    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;


    //init Databases

    public RoutineListDBHandler routineListDBHandler;
    public HabitListDBHandler habitListDBHandler;
    private DailyHabitsDBHandler dailyHabitsDBHandler;
    GoodCategoriesDB goodCategoriesDB;
    ToDoThingsDB toDoThingsDB;
    public XPDayDBHandler xpDayDBHandler;

    @Override
    public void onRestart() {
        //When BACK BUTTON is pressed (or a new sub-activity finishes),
        // the activity on the stack is restarted
        //the refresh procedure is here
        super.onRestart();
        //re-set today's XP to XP of day in DB
        XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());

        //re-set XP menu button to today's XP
        xpCountBtn.setText(String.valueOf(XPUtils.dayXP.getXp()));
        //Log.i("Schedule Activity on Restart","Xp is "+XPUtils.dayXP.getXp());

        //refresh goals/to-dos, routines&habits
        setDaysGoalsRecyclerView();
        setRoutineRecyclerView();
        //re-set Categories and Calendar
        setCategoryUtils();
        setCalendarUtils();
    }



    //Action Bar Menu with XP button
    // shows XP of that day (button opens activity with month XP graph)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu
        getMenuInflater().inflate(R.menu.main, menu);

        //get XP button in menu
        MenuItem item = menu.findItem(R.id.xp_item);
        MenuItemCompat.setActionView(item, R.layout.xp_action_bar_button);
        xpCountBtn = (Button)MenuItemCompat.getActionView(item);

//        Log.i("xp in menu 1",XPUtils.dayXP.getDate()+" "+XPUtils.dayXP.getXp());
        setXPUtils();
        xpCountBtn.setOnClickListener(view ->  startActivity(new Intent(getApplicationContext(), XPGoalActivity.class))
        );
      //  Log.i("xp in menu 2",XPUtils.dayXP.getDate()+" "+XPUtils.dayXP.getXp());


        //set today's XP to XP of day in DB
        //XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());

        //if no XP for that date in DB, XP set to 0
      /*  if(Objects.equals(XPUtils.dayXP.getDate(), "no xp for date")){
            xpDayDBHandler.addDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(),0));
            XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());
        }*/

        //set today's XP to XP of day in DB
        //XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());

        //set XP menu button to today's XP
        //xpCountBtn.setText(String.valueOf(XPUtils.dayXP.getXp())); //xpCount where is it initialised?

        //XP Goal Button
        MenuItem item1 = menu.findItem(R.id.xp_goal);
        MenuItemCompat.setActionView(item1, R.layout.xp_goal_action_bar_button);
        ImageButton xpGoalBtn;
        xpGoalBtn = (ImageButton) MenuItemCompat.getActionView(item1);
        //xpGoalBtn = findViewById(R.id.xpGoalActionBarBtn);

        xpGoalBtn.setOnClickListener(view ->  startActivity(new Intent(getApplicationContext(), ChallengesActivity.class)));

        return super.onCreateOptionsMenu(menu);
    }

 /*   public void onResume() {
        super.onResume();
        //re-set today's XP to XP of day in DB
        XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());

        //re-set XP menu button to today's XP
        // xpCountBtn.setText(String.valueOf(XPUtils.dayXP.getXp()));
        Log.i("Schedule Activity on Resume","Xp is "+XPUtils.dayXP.getXp());

        //refresh goals/to-dos
        setDaysGoalsRecyclerView();
        //refresh routines&habits
        setRoutineRecyclerView();
        //re-set Categories and Calendar
        setCategoryUtils();
        setCalendarUtils();
        setXPUtils();
    }*/

   // public void invalidateOptionsMenu() {

    //}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        //set Action Bar, turns colour to skyblue/#70ccfd, removes title and elevation
        ActionBar bar = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd"))); //"#2f004d"
        Objects.requireNonNull(bar).setElevation(0);
        bar.setTitle("");

        //set Databases (DBs)
        goodCategoriesDB = new GoodCategoriesDB(this);
        xpDayDBHandler = new XPDayDBHandler(this);
        habitListDBHandler = new HabitListDBHandler(this);
        dailyHabitsDBHandler = new DailyHabitsDBHandler(this);
        routineListDBHandler = new RoutineListDBHandler(this);
        toDoThingsDB = new ToDoThingsDB(this);

        //set Calendar Values (eg. the day and category selected)
        setCalendarUtils();
        setCategoryUtils();

        //set UI Views
        setDateSelector();
        setCharacter();
        setDaysGoalsRecyclerView();
        setRoutineRecyclerView();

        //set Buttons
        setBottomNavMenu();
        isAllFabsVisible = false;
        setFabButtons();

        //work-in-progress
        //setReflections();

        ImageButton sceneLocaterBtn = findViewById(R.id.locateBtn);
        sceneLocaterBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SceneLocationActivity.class)));
    }

    private void setCategoryUtils(){

        //if no categories in DB, adds one
        if(goodCategoriesDB.listAllGoodCatsDB().isEmpty()){
            goodCategoriesDB.addGoodCategory(new GoodCategoryModel(0, "All Good Things (To Do)", R.drawable.bookswide160dpi, R.drawable.ic_baseline_favorite_24));
        }

        //sets category utils needed for the categories in adding to-dos (in To Do Activity)
        CategoriesUtil.categorySelected = "All Good Things (To Do)";
        CategoriesUtil.stateSelected = "To Do";
        CategoriesUtil.goodThingId = -1;
        CategoriesUtil.goodThing="";
        CategoriesUtil.categoryImgId = R.drawable.bookswide160dpi;
        CategoriesUtil.categoryLogoId = R.drawable.ic_baseline_favorite_24;
    }

    private void setCalendarUtils(){
        //sets date if no date (used for this/Schedule Activity, To-Dos, Calendar and XP utils)
        if(CalendarUtils.selectedDate==null){// || LocalTime.now()>LocalTime.of(4,0)){ //Add an "Or is midnight"
            CalendarUtils.selectedDate = LocalDate.now();
        }
        RoutineUtils.routineSel = "";
        RoutineUtils.daysOfWeekSelected = new ArrayList<>();
        RoutineUtils.routineHabitList = new ArrayList<>();
    }

    private void setXPUtils(){
        //set day's XP to XP from DB for selected date
        XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());
       // Log.i("xp in setXPUtils start",XPUtils.dayXP.getDate()+" "+XPUtils.dayXP.getXp());

        //if today's XP date isn't the same as the selected date, add new XP to DB
//        if(XPUtils.dayXP.equals(null)){
  //          xpDayDBHandler.addDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(),0));
    //        XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());
      //  }

        //if there's no DB entry for selected date
        //sets XP for selected date
        if(XPUtils.dayXP.getDate().equals("no xp for date")){
            //add new XP day with that date, xp=0;
            xpDayDBHandler.addDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(),0));
            XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());
            //Log.i("xp in setXPUtils if statement",XPUtils.dayXP.getDate()+" "+XPUtils.dayXP.getXp());
        }

     /*   if(!XPUtils.dayXP.getDate().equals(CalendarUtils.selectedDate.toString())){
            xpDayDBHandler.addDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(),0));
            XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());
            Log.i("xp in setXPUtils if statement",XPUtils.dayXP.getDate()+" "+XPUtils.dayXP.getXp());
        }*/

        //set XP menu button to today's XP
        xpCountBtn.setText(String.valueOf(XPUtils.dayXP.getXp())); //xpCount where is it initialised?
        //Log.i("xp in setXPUtils end",XPUtils.dayXP.getDate()+" "+XPUtils.dayXP.getXp());
    }

    private void setCharacter(){
        //sets waving dragon character
        ImageView jumpImage = findViewById(R.id.dragonChar);
        jumpImage.setImageResource(R.drawable.wave_item);
        jumpingAnimation = (AnimationDrawable) jumpImage.getDrawable();
        isWaving = false;
        jumpImage.setOnClickListener(view -> {
            if(isWaving){
                jumpingAnimation.stop();
                isWaving = false;
            }else{
                jumpingAnimation.start();
                isWaving = true;
            }
        });
    }


    private void setReflections(){
        Button reflectionBtn = findViewById(R.id.reflectionBtn);
        reflectionBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ReflectionActivity.class)));

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {
            notes.add("Example note");
        } else {
            notes = new ArrayList(set);
        }
    }

    public void setDateSelector(){
        dateView = findViewById(R.id.todayDateTV);
        Button backBtn = findViewById(R.id.scheduleBackBtn);
        Button nextBtn = findViewById(R.id.scheduleNextBtn);

        backBtn.setOnClickListener(view -> previousDay());
        nextBtn.setOnClickListener(view -> nextDay());

        dateView.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));

      /*  if(CalendarUtils.selectedDate.equals(LocalDate.now())){
            dateView.setBackgroundColor(Color.parseColor("#70ccfd"));
        }*/
            dateView.setOnClickListener(view -> {
            //getting the instance of the calendar
            final Calendar c = Calendar.getInstance();

            //day, month, year
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SchedulerActivity.this,
                    (datePicker, year1, month1, dayOfMonth) -> {
                        String date;
                        if(dayOfMonth<10 && month1 +1<10){
                            date = year1 + "-" + "0" + (month1 + 1) + "-" + "0" + dayOfMonth;
                        }else if(dayOfMonth<10){
                            date = year1 + "-" + month1 + 1 + "-" + "0" + dayOfMonth;
                        }else if(month1 +1<10){
                            date = year1 + "-" + "0" + (month1 + 1) + "-" + dayOfMonth;
                        }else{
                            date = year1 + "-" + month1 + 1 + "-" + dayOfMonth;
                        }
                        Log.i("date",date);
                        CalendarUtils.selectedDate = LocalDate.parse(date);
                        recreate();
                        //dateView.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
                    },
                    year, month, day);
            datePickerDialog.show();
        });
    }

    private void setDaysGoalsRecyclerView(){
        RecyclerView daysGoalsRecyclerView = findViewById(R.id.daysGoalsRV);
        TextView noToDoTV = findViewById(R.id.noToDoTV);
        CardView noToDoCard = findViewById(R.id.noToDoCard);

        //Add one-off to do to DB including 1. download my app 2. Add 1 routine 3. Add 1 challenge
        if(toDoThingsDB.isEmpty()){
            toDoThingsDB.addGoodThing(new ToDoThingModel(0, "All Good Things (To Do)", "Download Esk App", "", R.drawable.ic_baseline_favorite_24,"#FFFFFF","Done", LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString()));
            toDoThingsDB.addGoodThing(new ToDoThingModel(0, "All Good Things (To Do)", "Add 1 To Do", "", R.drawable.ic_baseline_favorite_24,"#FFFFFF", "To Do", LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString(), ""));
            toDoThingsDB.addGoodThing(new ToDoThingModel(0, "All Good Things (To Do)", "Add 1 Routine", "", R.drawable.ic_baseline_favorite_24,"#FFFFFF", "To Do", LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString(), ""));
        }

        ArrayList<ToDoThingModel> daysTodos = toDoThingsDB.listToDoInDay(CalendarUtils.selectedDate);

        if(daysTodos.isEmpty()){
           /* ArrayList<ToDoThingModel> emptyList = new ArrayList<>();
            ToDoThingModel toDoThingModel = new ToDoThingModel(0,"No Cat","No To-Dos Today","Happy it exists","date");
            emptyList.add(toDoThingModel);

            ToDoThingAdapter toDoThingAdapter = new ToDoThingAdapter(emptyList, this);
            daysGoalsRecyclerView.setAdapter(toDoThingAdapter);*/
            noToDoTV.setVisibility(View.VISIBLE);
            noToDoCard.setVisibility(View.VISIBLE);
        }else{
            noToDoTV.setVisibility(View.GONE);
            noToDoCard.setVisibility(View.GONE);
        }

        ToDoThingAdapter toDoThingAdapter = new ToDoThingAdapter(daysTodos, this);
        daysGoalsRecyclerView.setAdapter(toDoThingAdapter);

        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),400);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);
        daysGoalsRecyclerView.setLayoutManager(layoutManager);

    }

    private void setRoutineRecyclerView(){
        RecyclerView routineRecyclerView = findViewById(R.id.RoutinesRecyclerView);

        //List Routines in Day from RoutinesDB (can probably use listRoutinesInDay)
        ArrayList<RoutineModel> routinesInDay = routineListDBHandler.listRoutinesInDay();
        //Log.i("schedule Act","routines in day size: "+routinesInDay.size());
        //Create routine array sorted by time
        Comparator <RoutineModel> comparator = new TimeComparator();
        routinesInDay.sort( comparator );

        if(!routinesInDay.isEmpty()) { //and date is within 2 weeks of now (else - do "look back" - for later)
            for(int i = 0; i < routinesInDay.size(); i++) {
                String routine = routinesInDay.get(i).getRoutine();
                ArrayList<RoutineModel> list = new ArrayList<>();
                list.add(new RoutineModel(routine));
                //if dailyHabitsList and habitsList for a routine are not the same size,
                // then check if habits match, add missing habits
                  if(dailyHabitsDBHandler.listHabits(routine).size()!=habitListDBHandler.listHabitsInRoutinesInDay(list).get(0).getHabitArrayList().size()){
                    ArrayList<HabitModel> habitsArray = habitListDBHandler.listHabitsInRoutinesInDay(routinesInDay).get(i).getHabitArrayList();
                    for (int j = 0; j < habitsArray.size(); j++) {
                        HabitModel habitModel = habitsArray.get(j);
                        dailyHabitsDBHandler.addHabit(new HabitModel(0, CalendarUtils.selectedDate.toString(), habitModel.getRoutine(), habitModel.getTask(), habitModel.getStatus()));
                    }
                }
            }
            RoutineAdapter routineAdapter = new RoutineAdapter(habitListDBHandler.listHabitsInRoutinesInDay(routinesInDay), this);
            routineRecyclerView.setAdapter(routineAdapter);
        }else{
            ArrayList<RoutineModel> emptyList = new ArrayList<>();
            ArrayList<HabitModel> emptyHabitList = new ArrayList<>();
            RoutineModel routineModel = new RoutineModel("No Routines", emptyHabitList);
            emptyList.add(routineModel);

            RoutineAdapter routineAdapter = new RoutineAdapter(emptyList, this);
            routineRecyclerView.setAdapter(routineAdapter);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        routineRecyclerView.setLayoutManager(layoutManager);
    }

    public static class TimeComparator implements Comparator<RoutineModel> {
        @Override
        public int compare (RoutineModel o1 , RoutineModel o2)
        {
            // Compare the date portion first. If equal, then look at time-of-day.
            LocalTime startTimeO1 = LocalTime.of(o1.getStartHour(),o1.getStartMinute());
            LocalTime startTimeO2 = LocalTime.of(o2.getStartHour(),o2.getStartMinute());

            //int result = o1.toLocalDate().compareTo( o2.toLocalDate() ); // Consider only the date portion first.
            //result = ( ( - 1 ) * result ); // Flip the positive/negative sign of the int, to get ascending order. Or more simply: `= - result ;`.
            //if ( 0 == result ) // If dates are equal, look at the time-of-day.
          //  {
        //        System.out.println( "reversing " );
            //    result = o1.toLocalTime().compareTo( o2.toLocalTime() );
           // }
            return startTimeO1.compareTo(startTimeO2);
        }
    }

    public void setFabButtons(){
        ExtendedFloatingActionButton addFab = findViewById(R.id.fab);
        FloatingActionButton addRoutine = findViewById(R.id.addRoutineFab);
        FloatingActionButton addToDo = findViewById(R.id.addToDoFab);

        addFab.shrink();
        addRoutine.setVisibility(View.GONE);
        addToDo.setVisibility(View.GONE);
        isAllFabsVisible = false;

        addFab.setOnClickListener(
                view -> {
                    if (!isAllFabsVisible) {
                        addToDo.show();
                        addRoutine.show();
                        addFab.extend();
                        isAllFabsVisible = true;
                    } else {
                        addToDo.hide();
                        addRoutine.hide();
                        addFab.shrink();
                        isAllFabsVisible = false;
                    }
                });

        addToDo.setOnClickListener(
                view -> {
                    CategoriesUtil.goodThingId=-1;
                    CategoriesUtil.goodThing="";
                    CategoriesUtil.categorySelected="All Good Things (To Do)";
                    CategoriesUtil.stateSelected="To Do";
                    addToDo.hide();
                    addRoutine.hide();
                    addFab.shrink();
                    isAllFabsVisible = false;
                    startActivity(new Intent(getApplicationContext(), ToDoAddThingActivity.class));
                });

        addRoutine.setOnClickListener(
                view -> startActivity(new Intent(getApplicationContext(), AddRoutineActivity.class)));


        NestedScrollView scrollView = findViewById(R.id.scheduleScrollView);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // the delay of the extension of the FAB is set for 12 items
            if (scrollY > oldScrollY + 4 && addFab.isShown()){ // && addFab.isExtended()) {
                //addFab.shrink();
                addRoutine.hide();
                addToDo.hide();
                addFab.shrink();
                addFab.hide();
                isAllFabsVisible = false;
            }

            // the delay of the extension of the FAB is set for 12 items
            if (scrollY < oldScrollY - 4){ // && !addFab.isExtended()) {
                //addFab.extend();
                addFab.show();
                isAllFabsVisible = false;
            }

            // if the nestedScrollView is at the first item of the list then the
            // extended floating action should be in extended state
            if (scrollY == 0) {
                //addFab.extend();
                addFab.show();
                isAllFabsVisible = false;
            }
        });
    }

    public void previousDay(){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        dateView.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        setRoutineRecyclerView();
        setDaysGoalsRecyclerView();
        setXPUtils();
    }

    public void nextDay(){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        dateView.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        setRoutineRecyclerView();
        setDaysGoalsRecyclerView();
        setXPUtils();
    }

    public void setBottomNavMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.schedule);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch(item.getItemId()) {
                case R.id.calendar:
                    finish();
                    startActivity(new Intent(getApplicationContext(),CalendarActivity.class));
                    //overridePendingTransition(0,0);
                    return true;
                case R.id.goodThings:
                    finish();
                    startActivity(new Intent(getApplicationContext(),ToDoListActivity.class));
                    //overridePendingTransition(0,0);
                    return true;
                case R.id.schedule:
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(habitListDBHandler != null){
            habitListDBHandler.close();
        }
        if(routineListDBHandler != null){
            routineListDBHandler.close();
        }
        if(dailyHabitsDBHandler != null){
            dailyHabitsDBHandler.close();
        }
        if(goodCategoriesDB != null){
            goodCategoriesDB.close();
        }
        if(toDoThingsDB != null){
            toDoThingsDB.close();
        }
        if(xpDayDBHandler != null) xpDayDBHandler.close();
    }
}