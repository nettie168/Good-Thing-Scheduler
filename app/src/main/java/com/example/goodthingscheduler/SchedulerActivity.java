package com.example.goodthingscheduler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

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

import java.lang.ref.WeakReference;
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

    public static Button xpCountBtn;



    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;


    //init Databases
    public RoutineListDBHandler routineListDBHandler;
    public HabitListDBHandler habitListDBHandler;
    private DailyHabitsDBHandler dailyHabitsDBHandler;
    private GoodCategoriesDB goodCategoriesDB;
    private ToDoThingsDB toDoThingsDB;
    public XPDayDBHandler xpDayDBHandler;

    private RecyclerView daysGoalsRecyclerView;
    ToDoThingAdapter toDoThingAdapter;

    RoutineAdapter routineAdapter;


    @Override
    public void onRestart() {
        //When BACK BUTTON is pressed (or a new sub-activity finishes),
        // the activity on the stack is restarted
        //the refresh procedure is here
        super.onRestart();
        //re-set today's XP to XP of day in DB
        new XPUtilsTask(xpDayDBHandler).execute();

        //refresh goals/to-dos

        //refresh routines&habits
        //Execute AsyncTask to update Routines and Habits from DB
        new RoutineHabitsAsyncTask(routineAdapter, routineListDBHandler, habitListDBHandler, dailyHabitsDBHandler).execute();
        //re-set Categories and Calendar
        //setCategoryUtils();
        new CategoryUtilsTask(goodCategoriesDB).execute();
        setCalendarUtils();
    }

    //Action Bar Menu with XP button
    // shows XP of that day (button opens activity with month XP graph)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.xp_item);
        item.setActionView(R.layout.xp_action_bar_button);
        xpCountBtn = item.getActionView().findViewById(R.id.xpCountActionBarBtn);

        xpCountBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), XPGoalActivity.class)));

        //setXPUtils();
        setCalendarUtils();
        new XPUtilsTask(xpDayDBHandler).execute();

        //XP Goal Button//
        MenuItem item1 = menu.findItem(R.id.xp_goal);
        item1.setActionView(R.layout.xp_goal_action_bar_button);
        ImageButton xpGoalBtn = item1.getActionView().findViewById(R.id.xpGoalActionBarBtn);

        xpGoalBtn.setOnClickListener(view ->  startActivity(new Intent(getApplicationContext(), ChallengesActivity.class)));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        //set Action Bar, turns colour to skyblue/#70ccfd, removes title and elevation
        setActionBar();

        //set/initialise Databases (DBs)
        initDB();

        //set Calendar Values (eg. the day and category selected)
        setCalendarUtils();
        //add first category
        new CategoryUtilsTask(goodCategoriesDB).execute();

        //set Selector for Date
        setDateSelector();
        //set Character Animation
        setCharacter();

        //set Buttons
        setBottomNavMenu();
        isAllFabsVisible = false;
        setFabButtons();

        //work-in-progress
        //setReflections();

        //==SET UP DAILY TO DOS==//
        //Recycler View and Empty Placeholder
        daysGoalsRecyclerView = findViewById(R.id.daysGoalsRV);

        //To Dos Adapter
        toDoThingAdapter = new ToDoThingAdapter(new ArrayList<>(), this);
        daysGoalsRecyclerView.setAdapter(toDoThingAdapter);

        //Create Grid of To Dos for Large Screens
        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),400);
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);
        daysGoalsRecyclerView.setLayoutManager(layoutManager);

        //Execute AsyncTask to retrieve To Dos from the database and update adapter
        //Think there's a resource failing to close in DatabaseAsync
        new ToDoAsyncTask(this, toDoThingAdapter, toDoThingsDB).execute();


        //==Set UP DAILY ROUTINES==//
        //Recycler View and Empty Placeholder
        RecyclerView routineRecyclerView = findViewById(R.id.RoutinesRecyclerView);

        //Routines Adapter
        routineAdapter = new RoutineAdapter(new ArrayList<>(), this);
        routineRecyclerView.setAdapter(routineAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        routineRecyclerView.setLayoutManager(linearLayoutManager);

        //Execute AsyncTask to retrieve Routines from the database
        //And update adapter
        new RoutineHabitsAsyncTask(routineAdapter, routineListDBHandler, habitListDBHandler, dailyHabitsDBHandler).execute();


        //ImageButton sceneLocaterBtn = findViewById(R.id.locateBtn);
        //sceneLocaterBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), SceneLocationActivity.class)));
    }

    private void setActionBar(){
        ActionBar bar = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd"))); //"#2f004d"
        Objects.requireNonNull(bar).setElevation(0);
        bar.setTitle("");
    }

    private void initDB(){
        goodCategoriesDB = new GoodCategoriesDB(this);
        xpDayDBHandler = new XPDayDBHandler(this);
        routineListDBHandler = new RoutineListDBHandler(this);
        habitListDBHandler = new HabitListDBHandler(this);
        dailyHabitsDBHandler = new DailyHabitsDBHandler(this);
        toDoThingsDB = new ToDoThingsDB(this);
    }

    private void setCalendarUtils(){
        //sets date if no date (used for this/Schedule Activity, To-Dos, Calendar and XP utils)
        if(CalendarUtils.selectedDate==null){// || LocalTime.now()>LocalTime.of(4,0)){ //Add an "Or is midnight"
            CalendarUtils.selectedDate = LocalDate.now();
        }
        RoutineUtils.routineSel = "";
        RoutineUtils.daysOfWeekSelected = new ArrayList<>();
        RoutineUtils.routineHabitList = new ArrayList<>();

        CategoriesUtil.categorySelected = "All Good Things (To Do)";
        CategoriesUtil.stateSelected = "To Do";
        CategoriesUtil.goodThingId = -1;
        CategoriesUtil.goodThing="";
        CategoriesUtil.categoryImgId = R.drawable.snowy_mountain;
        CategoriesUtil.categoryLogoId = R.drawable.ic_baseline_favorite_24;
    }

    private static class CategoryUtilsTask extends AsyncTask<Void, Void, Void> {
        private final GoodCategoriesDB goodCategoriesDB;

        CategoryUtilsTask(GoodCategoriesDB dbHelper) {
            goodCategoriesDB = dbHelper;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            setCategoryUtils();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Handle any post-execution tasks if needed
        }

        private void setCategoryUtils(){
            //if no categories in DB, adds one
            if(goodCategoriesDB.listAllGoodCatsDB().isEmpty()){
                goodCategoriesDB.addGoodCategory(new GoodCategoryModel(0, "All Good Things (To Do)", R.drawable.snowy_mountain, R.drawable.ic_baseline_favorite_24));
            }
        }

    }


    private static class XPUtilsTask extends AsyncTask<Void, Void, Void> {
        private final XPDayDBHandler xpDayDBHandler;

        XPUtilsTask(XPDayDBHandler dbHelper) {
            xpDayDBHandler = dbHelper;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            setXPUtils();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Handle any post-execution tasks if needed
            //set XP menu button to today's XP
            xpCountBtn.setText(String.valueOf(XPUtils.dayXP.getXp())); //xpCount where is it initialised?
        }

        private synchronized void setXPUtils() {
            //set day's XP to XP from DB for selected date
            XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());

            //if there's no DB entry for selected date
            //sets XP for selected date
            if(XPUtils.dayXP.getDate().equals("no xp for date")){
                //add new XP day with that date, xp=0;
                xpDayDBHandler.addDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(),0));
                XPUtils.dayXP = xpDayDBHandler.todayXP(CalendarUtils.selectedDate.toString());
            }
        }
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

        //Not Main Thread
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

        dateView.setOnClickListener(view -> {

            //not Main Thread
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
                    },
                    year, month, day);
            datePickerDialog.show();
        });
    }


    private static class ToDoAsyncTask extends AsyncTask<Void, Void, ArrayList<ToDoThingModel>> {
        private final WeakReference<SchedulerActivity> activityReference;
        private final WeakReference<ToDoThingAdapter> adapterReference;
        private final ToDoThingsDB toDoThingsDB;

        Boolean daysToDosEmpty = false;
        private ArrayList<ToDoThingModel> daysTodos;



        ToDoAsyncTask(SchedulerActivity activity, ToDoThingAdapter adapter, ToDoThingsDB dbHelper) {
            activityReference = new WeakReference<>(activity);
            adapterReference = new WeakReference<>(adapter);
            toDoThingsDB = dbHelper;
        }

        @Override
        protected ArrayList<ToDoThingModel> doInBackground(Void... voids) {
            // Perform database insert on a background thread
            performBackground1();
            // Perform database query on a background thread
            return daysTodos;
        }

        @Override
        protected void onPostExecute(ArrayList<ToDoThingModel> data) {
            SchedulerActivity activity = activityReference.get();
            // Update the RecyclerView adapter with the retrieved data
            ToDoThingAdapter adapter = adapterReference.get();


            if (activity != null && adapter != null) {
                activity.runOnUiThread(() -> {
                    if (daysToDosEmpty) {
                       // activity.noToDoPlaceHolderLayout.setVisibility(View.VISIBLE);
                        activity.daysGoalsRecyclerView.setVisibility(View.GONE);
                    } else {
                       // activity.noToDoPlaceHolderLayout.setVisibility(View.GONE);
                        activity.daysGoalsRecyclerView.setVisibility(View.VISIBLE);
                    }
                    adapter.setData(data);
                });
            }
        }

        private void performBackground1(){
            //Add one-off to do to DB when user first downloads app
            // including 1. download my app 2. Add 1 routine 3. Add 1 challenge
            ArrayList<ToDoThingModel> firstToDosList = new ArrayList<>();
            ToDoThingModel toDoThingModel1 = new ToDoThingModel(0, "All Good Things (To Do)", "Download Esk App", "", R.drawable.ic_baseline_favorite_24,"#FFFFFF","Done", LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString());
            ToDoThingModel toDoThingModel2 = new ToDoThingModel(0, "All Good Things (To Do)", "Add 1 To Do", "", R.drawable.ic_baseline_favorite_24,"#FFFFFF", "To Do", LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString(), "");
            ToDoThingModel toDoThingModel3 = new ToDoThingModel(0, "All Good Things (To Do)", "Add 1 Routine", "", R.drawable.ic_baseline_favorite_24,"#FFFFFF", "To Do", LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString(), "");

            firstToDosList.add(toDoThingModel1);
            firstToDosList.add(toDoThingModel2);
            firstToDosList.add(toDoThingModel3);

            if(toDoThingsDB.isEmpty()){
                for(int i =0; i < firstToDosList.size(); i++){
                    toDoThingsDB.addGoodThing(firstToDosList.get(i));
                }
            }

            //to dos for selected date
            daysTodos =  toDoThingsDB.listToDoInDay(CalendarUtils.selectedDate);

            if(daysTodos.isEmpty()){
                daysToDosEmpty = true;
                ArrayList<ToDoThingModel> noToDoList = new ArrayList<>();
                ToDoThingModel noToDo = new ToDoThingModel(0, "All Good Things (To Do)", "No To Dos", "", R.drawable.ic_baseline_favorite_24,"#FFFFFF","Done", LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString(), LocalDate.now().toString());
                noToDoList.add(noToDo);
                daysTodos = noToDoList;
            }
        }
    }


    private static class RoutineHabitsAsyncTask extends AsyncTask<Void, Void, ArrayList<RoutineModel>> {
        private final WeakReference<RoutineAdapter> adapterReference;
        private final RoutineListDBHandler routineListDBHandler;
        private final DailyHabitsDBHandler dailyHabitsDBHandler;
        private final HabitListDBHandler habitListDBHandler;
        private ArrayList<RoutineModel> routineHabitList = new ArrayList<>();


        RoutineHabitsAsyncTask(RoutineAdapter adapter, RoutineListDBHandler dbHelper, HabitListDBHandler dbHelper2, DailyHabitsDBHandler dbHelper3) {
            adapterReference = new WeakReference<>(adapter);
            //DB for saved routines
            routineListDBHandler = dbHelper;
            //DB for saved habits
            habitListDBHandler = dbHelper2;
            //DB for daily habit tracking
            dailyHabitsDBHandler = dbHelper3;
        }

        @Override
        protected ArrayList<RoutineModel> doInBackground(Void... voids) {
            // Perform database query on a background thread
            performBackground1();
            return routineHabitList;
        }

        @Override
        protected void onPostExecute(ArrayList<RoutineModel> data) {
            // Update the RecyclerView adapter with the retrieved data
            RoutineAdapter adapter = adapterReference.get();
            if (adapter != null) {
                adapter.setData(data);
            }
        }

        private void performBackground1(){
            // retrieve the saved routines
            // that include selected day's day
            ArrayList<RoutineModel> routinesInDay = routineListDBHandler.listRoutinesInDay();

            //Reorder routines ArrayList by time
            Comparator <RoutineModel> comparator = new TimeComparator();
            routinesInDay.sort( comparator );

            //if there are routines for that day
            if(!routinesInDay.isEmpty()) { //and date is within 2 weeks of now (else - do "look back" - for later)
                for(int i = 0; i < routinesInDay.size(); i++) {
                    //Create a list of all routines for today
                    String routine = routinesInDay.get(i).getRoutine();
                    ArrayList<RoutineModel> list = new ArrayList<>();
                    list.add(new RoutineModel(routine));

                    //a resource failed to close
                    //find habits for this routine
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
                //declare routines and habits for that day
                routineHabitList = habitListDBHandler.listHabitsInRoutinesInDay(routinesInDay);

            //if no routines for today
            }else{
                //make routineHabit list
                //display No Routines
                ArrayList<HabitModel> emptyHabitList = new ArrayList<>();
                RoutineModel routineModel = new RoutineModel("No Routines", emptyHabitList);
                routineHabitList.add(routineModel);
            }
        }
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
                //not Main Thread
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
                //not Main Thread
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
                //not Main Thread
                view -> startActivity(new Intent(getApplicationContext(), AddRoutineActivity.class)));


        NestedScrollView scrollView = findViewById(R.id.ScheduleScrollView);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // the delay of the extension of the FAB is set for 12 items
            if (scrollY > oldScrollY + 4 && addFab.isShown()){ // && addFab.isExtended()) {
                //addFab.shrink();
                addRoutine.hide();
                addToDo.hide();
                //addFab.shrink();
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
        //Execute AsyncTask to update new To Dos from DB
        new ToDoAsyncTask(this, toDoThingAdapter, toDoThingsDB).execute();
        //Execute AsyncTask to update Routines and Habits from DB
        new RoutineHabitsAsyncTask(routineAdapter, routineListDBHandler, habitListDBHandler, dailyHabitsDBHandler).execute();
        //setXPUtils();
        new XPUtilsTask(xpDayDBHandler).execute();
    }

    public void nextDay(){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        dateView.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        //Execute AsyncTask to update new To Dos from DB
        new ToDoAsyncTask(this, toDoThingAdapter, toDoThingsDB).execute();
        //Execute AsyncTask to update Routines and Habits from DB
        new RoutineHabitsAsyncTask(routineAdapter, routineListDBHandler, habitListDBHandler, dailyHabitsDBHandler).execute();
        //setXPUtils();
        new XPUtilsTask(xpDayDBHandler).execute();
    }

    public void setBottomNavMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.schedule);

        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(item -> {});

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.calendar) {
                finish();
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                return true;
            } else if (itemId == R.id.goodThings) {
                finish();
                startActivity(new Intent(getApplicationContext(), ToDoListActivity.class));
                return true;
            } else return itemId == R.id.schedule;
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(xpDayDBHandler != null)
            xpDayDBHandler.close();
        if(goodCategoriesDB != null){
            goodCategoriesDB.close();
        }
        if(routineListDBHandler != null){
            routineListDBHandler.close();
        }
        if(habitListDBHandler != null){
            habitListDBHandler.close();
        }
        if(dailyHabitsDBHandler != null){
            dailyHabitsDBHandler.close();
        }
        if(toDoThingsDB != null){
            toDoThingsDB.close();
        }
    }
}