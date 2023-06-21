package com.example.goodthingscheduler.scheduleAddRoutineHabits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.scheduleHabits.HabitListDBHandler;
import com.example.goodthingscheduler.scheduleHabits.RoutineUtils;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.scheduleHabits.HabitModel;
import com.example.goodthingscheduler.scheduleHabits.RoutineListDBHandler;
import com.example.goodthingscheduler.scheduleHabits.RoutineModel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class AddHabitsActivity extends AppCompatActivity {

    private RoutineListDBHandler routineListDBHandler;
    private HabitListDBHandler habitListDBHandler;
    private int startHour;
    private int startMinute;
    private int endHour;
//    private Boolean addRoutineTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habits);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit "+RoutineUtils.routineSel);

        habitListDBHandler = new HabitListDBHandler(this);
        setDaysOfWeek();

        Button saveHabitsBtn = findViewById(R.id.saveHabitsBtn);

        //Log.i("addHabitsActivty","RoutineUtilsDaysOfWeek:"+RoutineUtils.daysOfWeekSelected.toString());

        if(RoutineUtils.routineHabitList.isEmpty()){
            String start = "Start Routine";
            saveHabitsBtn.setText(start);
            saveHabitsBtn.setOnClickListener(view -> saveHabits());
        }else{
            String save = "Save Routine";
            saveHabitsBtn.setText(save);
            saveHabitsBtn.setOnClickListener(view -> updateHabits());
        }

        RecyclerView recyclerView = findViewById(R.id.addHabitRV);
        CardView routineStartTimeCard = findViewById(R.id.routineStartTimeCard);
        TextView startTimeTV = findViewById(R.id.routineStartTimeTV);

        startHour = RoutineUtils.routineSelStartHour;
        startMinute = RoutineUtils.routineSelStartMinute;
        startTimeTV.setText(LocalTime.of(startHour,startMinute).toString());

        routineStartTimeCard.setOnClickListener(view -> {
            // on below line we are getting the
            // instance of our calendar.
         //   final Calendar c = Calendar.getInstance();

            // on below line we are getting our hour, minute.
          //  int hour = c.get(Calendar.HOUR_OF_DAY);
           // int minute = c.get(Calendar.MINUTE);

            Log.i("start hour 1", String.valueOf(startHour));

            // on below line we are initializing our Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(AddHabitsActivity.this,
                    (view1, hourOfDay, minute1) -> {
                        // on below line we are setting selected time
                        // in our text view.
                        startTimeTV.setText(LocalTime.of(hourOfDay, minute1).toString());
                        startHour = hourOfDay;
                        startMinute = minute1;
                        //Toast.makeText(this, hourOfDay+":"+minute1, Toast.LENGTH_SHORT).show();
                    }, startHour, startMinute, true);
            // at last we are calling show to
            // display our time picker dialog.
            timePickerDialog.show();
        });

        routineListDBHandler = new RoutineListDBHandler(this);

        ArrayList<HabitModel> habitsList = new ArrayList<>();
       // ArrayList<RoutineModel> routineHabitsList = new ArrayList<>();

        //if(AddRoutineUtils.routineSel.equals("Wake Up")){
        switch (RoutineUtils.routineSel) {
            case "Wake Up":
                //ArrayList<HabitModel> Morning = new ArrayList<>(); //startMorning
                //RoutineUtils.routineHabitList.add(new HabitModel(habit.getId(), RoutineUtils.routineSel, habit.getTask(), "", CalendarUtils.selectedDate.toString(), "ongoing", 1, "constant", 0, "Mon,Tue,Wed,Thur,Fri,Sat,Sun", 0, "", habit.getHabitImgId()));

                habitsList.add(new HabitModel("wake up", 0, R.drawable.ic_baseline_wb_sunny_24));
                habitsList.add(new HabitModel("wake up before alarm", 0, R.drawable.ic_baseline_wb_sunny_24));
                habitsList.add(new HabitModel("get up", 0, R.drawable.ic_baseline_arrow_upward_24));
                habitsList.add(new HabitModel("make bed", 0, R.drawable.ic_baseline_bed_24));
                habitsList.add(new HabitModel("reflect on sleep", 0, R.drawable.ic_baseline_wb_sunny_24));
                habitsList.add(new HabitModel("drink water", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("put on calming music", 0, R.drawable.ic_baseline_wb_sunny_24));
                habitsList.add(new HabitModel("10 min meditation", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("feed pets", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("make breakfast", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("eat breakfast", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("swap coffee for water", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("swap coffee for tea", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("eat 1 fruit", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("eat 1 veg", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("eat handful of nuts", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("stretch or yoga", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("fold night clothes", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("put night clothes on bed", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("play motivating music", 0, R.drawable.ic_baseline_wb_sunny_24));

                break;
            case "Exercise":
                habitsList.add(new HabitModel("wear sports clothes", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("plank for 10-30s", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("10 press ups", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("10 leg raises", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("10 squats", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("10 mins of HITT", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("raise heart rate", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("drink water", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("have some nuts", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("5 min cold water shower", 0, R.drawable.ic_baseline_electric_bolt_24));

                break;
         case "Self-care":
                habitsList.add(new HabitModel("brush hair", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("shower", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("wash face", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("wash hair", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("bath", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("trim my nails", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("brush teeth", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("floss", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("gargle", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("moisturise", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("put on clean clothes", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("put dirty clothes in wash", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Prepare for the Day":
                habitsList.add(new HabitModel("play motivating music", 0, R.drawable.ic_baseline_wb_sunny_24));
                habitsList.add(new HabitModel("play calming music", 0, R.drawable.ic_baseline_wb_sunny_24));
                habitsList.add(new HabitModel("choose confident outfit", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("affirmations", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("get dressed", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("do hair", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Feel the Fear - and do it anyway":
                habitsList.add(new HabitModel("have a cold shower", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Work that works for me":
                habitsList.add(new HabitModel("check emails at work only", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("work only in workspace", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("work only during work hours", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("have lunch away from desk", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("take a 15min break every few hours", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("chat with a coworker", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("apply for annual leave", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("say no to extra work", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("discuss work-life balance with supervisor", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("discuss work-life balance with colleague", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("cigarettes smoked", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("swap cigarette for gum", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("swap coffee for water", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("swap coffee for tea", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("cups of coffee", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("stop working by 6pm", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down my core values", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down my preferred work conditions", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("reflect on today's work", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("reflect on job", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("create a current CV", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("create CV for ideal job", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("put CV on job sites", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write job application", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("apply for new job", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("20 min work focus", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("set goals for job", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("set goals for tomorrow", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Lunchtime":
                habitsList.add(new HabitModel("go for a walk",0,R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("leave work area",0,R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("listen to birds",0,R.drawable.ic_baseline_checkroom_24));

                break;
            case "Check Finances":
                habitsList.add(new HabitModel("check bank balance", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("create monthly budget", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("create daily budget", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("stick to budget", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("put money in pension", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("put money in savings", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("pay off 1 credit card", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("stay above overdraft", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("use food I already have", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("use food I already have", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("bulk buy food", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("buy only what is needed", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("fix or do-up old outfit", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Tidy Spaces":
                habitsList.add(new HabitModel("put away 3 items", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("tidy for 10 minutes", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("reset room for 10 minutes", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("reset 1 room", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("vacuum 1 floor", 0, R.drawable.ic_baseline_checkroom_24));

                habitsList.add(new HabitModel("put all dirty dishes in kitchen", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wash all the cutlery", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wash 2 plates", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wash 1 pan", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("put away clean dishes", 0, R.drawable.ic_baseline_checkroom_24));

                habitsList.add(new HabitModel("wipe 1 surface", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wipe kitchen sink", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("clean kitchen sink", 0, R.drawable.ic_baseline_checkroom_24));

                habitsList.add(new HabitModel("clean bathroom sink", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("clean the toilet seat", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wipe down 1 side of the shower", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wipe one side of the bath", 0, R.drawable.ic_baseline_checkroom_24));

                habitsList.add(new HabitModel("put clothes in the wash", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wash clothes", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("hang up wet clothes", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("put away dry clothes", 0, R.drawable.ic_baseline_checkroom_24));

                habitsList.add(new HabitModel("wash pillow covers", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wash bed sheets", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wash duvet cover", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wash linen", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Do Something I Love":
                habitsList.add(new HabitModel("draw", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("listen to music", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("practice music", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("D&D", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("paint", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("read", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("dance", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("take photos", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Self-Development":
                habitsList.add(new HabitModel("listen to 5 things in another language", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("speak 5 things in another language", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("watch something in another language", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("watch a documentary", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("practice maths", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("read a complex book", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("learn something new", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Spend time with Loved Ones":
                habitsList.add(new HabitModel("call a family member", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("call a friend", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("do a hobby with a loved one", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("do a puzzle with a loved one", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("jigsaw with a loved one", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("read a book with a loved one", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("play a game together", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("read a story to loved one", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("make a meal for loved one", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("tell one person you love them", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("listen to a loved one", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("tell one person a good thing about them", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("watch the sun set with someone", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("bake something together", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("cook a meal together", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write a letter to someone", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("post letter", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("draw a picture of them", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("draw a picture for them", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Magnificent Mealtime":
                habitsList.add(new HabitModel("choose a magnificent meal", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("find recipe", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("have 1 vegetable in meal", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("have plant-based meal", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("put out utensils needed", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("put out ingredients needed", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("wash up plates after", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("give thanks for your food", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("act when I feel hungry", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("act when I feel full", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("take a moment to enjoy meal", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("reflect on mealtime", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down a food I want to try", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down a veg I enjoy", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("describe the taste of the meal", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("describe the smell of the meal", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("describe the texture of the meal", 0, R.drawable.ic_baseline_checkroom_24));


                break;
            case "Spend time with Yourself":
                habitsList.add(new HabitModel("play your favourite songs", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("stretch", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("reflect on how your day was", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("reflect on your feelings", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("check in with your body", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down any troubles as a story", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write a happy memory", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write a letter to a loved one", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("brain dump", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down what's on your mind", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down 3 things your body can do", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down 5 things you can do", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down 3 things you're good at", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down 3 things that make you smile", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("write down 3 things you're grateful for", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("listen to the birds", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("watch the sun set", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("sit with your emotions", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("allow yourself to cry", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("expressive drawing", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("expressive painting", 0, R.drawable.ic_baseline_checkroom_24));

                break;
            case "Expand Your Horizons":
                habitsList.add(new HabitModel("read a book", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("try a new food", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("cook a new food", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("bake something new", 0, R.drawable.ic_baseline_checkroom_24));
                habitsList.add(new HabitModel("watch a documentary", 0, R.drawable.ic_baseline_checkroom_24));


                break;
            case "Prepare for Tomorrow":
                habitsList.add(new HabitModel("wash water bottle", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("wash coffee cup", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("wash lunch container", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("tidy away 5 things", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("check schedule for tomorrow", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("set goals for tomorrow", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("write down things to do tomorrow", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("put out clean clothes", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("plan meals for tomorrow", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("update shopping list", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("prepare meals for the week", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("make lunch for tomorrow", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("pack bags", 0, R.drawable.ic_baseline_water_drop_24));

                break;
            case "Time in Nature":
                habitsList.add(new HabitModel("listen to a bird", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("watch an animal", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("feel the weather", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("feel the wind", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("touch a tree", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("touch water", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("touch the ground", 0, R.drawable.ic_baseline_water_drop_24));


                break;
            case "Growing nature":
                habitsList.add(new HabitModel("check on 1 plant", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("water 1 plant", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("re-pot 1 plant", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("move plant to better place", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("create cuttings", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("put cuttings in water", 0, R.drawable.ic_baseline_water_drop_24));
                habitsList.add(new HabitModel("put cuttings in soil", 0, R.drawable.ic_baseline_water_drop_24));

                break;
        /*    case "Self-care":
                habitsList.add(new HabitModel("brush teeth", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("floss", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("gargle", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("wash face", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("face mask", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("shower", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("bath", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("wash hair", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("trim my nails", 0, R.drawable.ic_baseline_shower_24));
                habitsList.add(new HabitModel("moisturise", 0, R.drawable.ic_baseline_electric_bolt_24));
                habitsList.add(new HabitModel("put dirty clothes in wash", 0, R.drawable.ic_baseline_checkroom_24));
                break;*/
        }

      /*  GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        HabitAdapter habitAdapter = new HabitAdapter(startMorning, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(habitAdapter);*/

    /*    routineModelArrayList.add(new RoutineModel("Morning",startMorning));
        routineModelArrayList.add(new RoutineModel("Looking Fine",cleanMorning));
        routineModelArrayList.add(new RoutineModel("Up and Energise",energiseMorning));
        routineModelArrayList.add(new RoutineModel("Calm and Relaxation",calmMorning));

        RoutineAdapter routineAdapter = new RoutineAdapter(routineModelArrayList, this);
        recyclerView.setAdapter(routineAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);*/


        //AddHabitsAdapter habitAdapter = new AddHabitsAdapter(Morning, this);
       // AddHabitsAdapter habitAdapter = new AddHabitsAdapter(routineHabitsList, this);
        AddHabitsAdapter habitAdapter = new AddHabitsAdapter(habitsList, this);

        recyclerView.setAdapter(habitAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setDaysOfWeek(){
        RecyclerView daysOfWeekRV = findViewById(R.id.dayOfWeekRV);

        ArrayList<String> daysOfWeekArray = new ArrayList<>();

        daysOfWeekArray.add("MONDAY");
        daysOfWeekArray.add("TUESDAY");
        daysOfWeekArray.add("WEDNESDAY");
        daysOfWeekArray.add("THURSDAY");
        daysOfWeekArray.add("FRIDAY");
        daysOfWeekArray.add("SATURDAY");
        daysOfWeekArray.add("SUNDAY");

        if(RoutineUtils.daysOfWeekSelected.isEmpty()){
            RoutineUtils.daysOfWeekSelected.add("MONDAY");
            RoutineUtils.daysOfWeekSelected.add("TUESDAY");
            RoutineUtils.daysOfWeekSelected.add("WEDNESDAY");
            RoutineUtils.daysOfWeekSelected.add("THURSDAY");
            RoutineUtils.daysOfWeekSelected.add("FRIDAY");
            RoutineUtils.daysOfWeekSelected.add("SATURDAY");
            RoutineUtils.daysOfWeekSelected.add("SUNDAY");
        }

        SelectButtonsAdapter selectButtonsAdapter = new SelectButtonsAdapter(daysOfWeekArray,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        daysOfWeekRV.setAdapter(selectButtonsAdapter);
        daysOfWeekRV.setLayoutManager(layoutManager);

    }

    public void saveHabits(){
     //   if(RoutineUtils.routineList.isEmpty()){
       //     Toast.makeText(this, "You haven't added any habits yet", Toast.LENGTH_LONG).show();
       // }else{
        if(RoutineUtils.daysOfWeekSelected.isEmpty()){
            Toast.makeText(this, "Missing a day for routine", Toast.LENGTH_LONG).show();
        }else{
           // Log.i("save Habits daysOfWeek 1",RoutineUtils.daysOfWeekSelected.toString());
            String days = RoutineUtils.daysOfWeekSelected.toString();
            String daysTrim =days.replace("[","").replace("]","").replace(" ","");
         //   Log.i("save Habits daysOfWeek 2", daysTrim);
            routineListDBHandler.addRoutine(new RoutineModel(RoutineUtils.routineSel, startHour,startMinute,endHour,endHour, daysTrim,1));
            for(int i = 0; i < RoutineUtils.routineHabitList.size(); i++){
                habitListDBHandler.addHabit(new HabitModel(RoutineUtils.routineHabitList.get(i).getId(), RoutineUtils.routineSel, RoutineUtils.routineHabitList.get(i).getTask(), "", CalendarUtils.selectedDate.toString(), RoutineUtils.routineHabitList.get(i).getEndDate(), RoutineUtils.routineHabitList.get(i).getTarget(), RoutineUtils.routineHabitList.get(i).getDirection(), RoutineUtils.routineHabitList.get(i).getStatus(), RoutineUtils.routineHabitList.get(i).getDaysOfWeek(), RoutineUtils.routineHabitList.get(i).getFrequency(), RoutineUtils.routineHabitList.get(i).getOnDateNext(), RoutineUtils.routineHabitList.get(i).getHabitImgId()));
            }
            Toast.makeText(this, "Saving "+RoutineUtils.routineSel, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateHabits(){
        String days = RoutineUtils.daysOfWeekSelected.toString();
        String daysTrim =days.replace("[","").replace("]","").replace(" ","");
        RoutineModel routineModel = new RoutineModel(RoutineUtils.routineSelId, RoutineUtils.routineSel, startHour,startMinute,endHour,endHour, daysTrim,1);
        routineListDBHandler.updateRoutine(routineModel);

       // ArrayList<RoutineModel> routineList = new ArrayList<>();
       // routineList.add(routineModel);

        for(int i = 0; i < RoutineUtils.routineHabitList.size(); i++){
         //   Log.i("Add Habits Activity, updateHabits","i: "+i+", id: "+RoutineUtils.routineHabitList.get(i).getId());
            HabitModel habitModel = new HabitModel(RoutineUtils.routineHabitList.get(i).getId(), RoutineUtils.routineSel, RoutineUtils.routineHabitList.get(i).getTask(), "", CalendarUtils.selectedDate.toString(), RoutineUtils.routineHabitList.get(i).getEndDate(), RoutineUtils.routineHabitList.get(i).getTarget(), RoutineUtils.routineHabitList.get(i).getDirection(), RoutineUtils.routineHabitList.get(i).getStatus(), RoutineUtils.routineHabitList.get(i).getDaysOfWeek(), RoutineUtils.routineHabitList.get(i).getFrequency(), RoutineUtils.routineHabitList.get(i).getOnDateNext(), RoutineUtils.routineHabitList.get(i).getHabitImgId());
            if(habitModel.getId()!=0){ //if it isn't already in DB
                habitListDBHandler.updateHabit(habitModel);
            }else {
                habitListDBHandler.addHabit(habitModel);
            }
        }

        Toast.makeText(this, "Saving "+RoutineUtils.routineSel, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(routineListDBHandler != null){
            routineListDBHandler.close();
        }
        if(habitListDBHandler != null){
            habitListDBHandler.close();
        }
    }
}