package com.example.goodthingscheduler.toDoThings;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.Categories.CategoriesUtil;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.SchedulerActivity;
import com.example.goodthingscheduler.scheduleHabits.RoutineUtils;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class FragmentDay extends Fragment {

    Button dateView;
    Button backBtn;
    Button nextBtn;
    ToDoThingsDB toDoThingsDB;
    ToDoStateAdapter adapter;


    public FragmentDay() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment, container, false);

        //init the DB
        toDoThingsDB = new ToDoThingsDB(getActivity());

        //declare views
        RecyclerView goodThingsRV = view.findViewById(R.id.goodThingsRV);

        setCalendarUtils();

        dateView = view.findViewById(R.id.todayDateTV);
        backBtn = view.findViewById(R.id.scheduleBackBtn);
        nextBtn = view.findViewById(R.id.scheduleNextBtn);

        setDateSelector();

        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(requireActivity(),400);
        adapter = new ToDoStateAdapter(new ArrayList<>(), getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), mNoOfColumns);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        goodThingsRV.setLayoutManager(layoutManager);
        goodThingsRV.setAdapter(adapter);
        new GoodThingsAsyncTask(adapter, toDoThingsDB).execute();

        return view;
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

    public void setDateSelector(){
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
                    getActivity(),
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
                        //recreate();
                    },
                    year, month, day);
            datePickerDialog.show();
        });
    }

    public void setGoodThingsList(){
        Log.i("Fragment Day, goodthingslist","is working");
        //new GoodThingsAsyncTask(adapter, toDoThingsDB).execute();
    }

    public void previousDay(){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        dateView.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        //Execute AsyncTask to update new To Dos from DB
        new GoodThingsAsyncTask(adapter, toDoThingsDB).execute();
        //setXPUtils();
        //new SchedulerActivity.XPUtilsTask(xpDayDBHandler).execute();
    }

    public void nextDay(){
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        dateView.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        //Execute AsyncTask to update new To Dos from DB
        new GoodThingsAsyncTask(adapter, toDoThingsDB).execute();
        //setXPUtils();
        //new SchedulerActivity.XPUtilsTask(xpDayDBHandler).execute();
    }


    private static class GoodThingsAsyncTask extends AsyncTask<Void, Void, ArrayList<ToDoStatesModel>> {
        private final WeakReference<ToDoStateAdapter> adapterReference;
        private final ToDoThingsDB toDoThingsDB;

        private ArrayList<ToDoStatesModel> thingsInState;

        GoodThingsAsyncTask(ToDoStateAdapter adapter, ToDoThingsDB dbHelper) {
            adapterReference = new WeakReference<>(adapter);
            toDoThingsDB = dbHelper;
        }

        @Override
        protected ArrayList<ToDoStatesModel> doInBackground(Void... voids) {
            // Perform database insert on a background thread
            performBackground1();
            return thingsInState;
        }

        @Override
        protected void onPostExecute(ArrayList<ToDoStatesModel> data) {
            // Update the RecyclerView adapter with the retrieved data
            ToDoStateAdapter adapter = adapterReference.get();

            if (adapter != null) {
                adapter.setData(data);
            }
        }

        private void performBackground1(){
            //Lists all the categories currently saved
            ArrayList<String> categoryList = toDoThingsDB.listGoodCatDB(); //seems to be working
            //Lists the states of these categories (eg. To Do or Done)
            ArrayList<String> stateList = toDoThingsDB.listGoodStatesInCatDB(categoryList);

            if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
                thingsInState = toDoThingsDB.listAllGoodThings(stateList);
            }else {
                thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
                //thingsInState = toDoThingsDB.listToDoInDay(CalendarUtils.selectedDate);
            }

            if(thingsInState.isEmpty()){
                String helloMessage = "Add "+CategoriesUtil.categorySelected+" good thing or to do";
                toDoThingsDB.addGoodThing(new ToDoThingModel(0, CategoriesUtil.categorySelected,helloMessage,"",CategoriesUtil.categoryLogoId,"#FFFFFF","To Do", LocalDate.now().toString(), "day","date not set","date not set","date"));
            }

            if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
                thingsInState = toDoThingsDB.listAllGoodThings(stateList);
            }else {
                thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
            }
        }
    }


}
