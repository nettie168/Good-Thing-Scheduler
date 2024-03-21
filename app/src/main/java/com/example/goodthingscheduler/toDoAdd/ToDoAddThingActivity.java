package com.example.goodthingscheduler.toDoAdd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.Categories.CategoriesUtil;
import com.example.goodthingscheduler.Categories.GoodCategoriesDB;
import com.example.goodthingscheduler.toDoThings.ToDoThingModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingsDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class ToDoAddThingActivity extends AppCompatActivity {

    ItemClickListener itemClickListener;
  //  StatesTagsAdapter statesTagsAdapter;
    ToDoTimesTagAdapter toDoTimesTagAdapter;
    CategoryTagsAdapter categoryTagsAdapter;
    private EditText goodThingEditText;
    private EditText inspiredByET;

    private boolean addThingTrue;

    private GoodCategoriesDB goodCategoriesDB;
    private ToDoThingsDB goodThingsDB;


    @Override
    public void onRestart() {
        super.onRestart();
        Button categoryBtn = findViewById(R.id.categoryBtn);
        categoryBtn.setText(CategoriesUtil.categorySelected);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_bar, menu);

        MenuItem item = menu.findItem(R.id.saveActionBarBtn);

        item.setOnMenuItemClickListener(menuItem -> {
            if(addThingTrue){
                save();
            }else{
                onUpdate();
            }
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_add);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);
    //    getSupportActionBar().setTitle("String"); //string is custom name you want

       // goodTitle = findViewById(R.id.goodThingTitleTV);
        goodThingEditText = findViewById(R.id.addGoodThingEditText);
        inspiredByET = findViewById(R.id.inspiredByEditText);

     //   if(!CategoriesUtil.stateSelected.equals("Hello")){
      //  goodThingEditText.setCompoundDrawables(0,0,0,0);
            goodThingEditText.setText(CategoriesUtil.goodThing);
            inspiredByET.setText(CategoriesUtil.inspiredBy);
       //     Log.i("Edit Text is","here");
       // }

        //private TextView goodTitle;
        //String addThing = "Add Good Thing";
        //String editThing = "Edit Good Thing";

        if(goodThingEditText.getText().toString().isEmpty()){
            addThingTrue = true;
            getSupportActionBar().setTitle("Add To Do"); //string is custom name you want
            //goodTitle.setText(addThing);
        }else{
         //   goodTitle.setText(editThing);
            addThingTrue = false;
            getSupportActionBar().setTitle("Edit To Do"); //string is custom name you want
        }

        goodCategoriesDB = new GoodCategoriesDB(this);
        goodThingsDB = new ToDoThingsDB(this);

        //initCategoriesView();
        initButtons();
        initStates();
        initDatePickers();
        //initTimePickers();
    }

 /*   private void initCategoriesView(){
        goodThingsDB = new ToDoThingsDB(this);
        GoodCategoriesDB goodCategoriesDB = new GoodCategoriesDB(this);

        RecyclerView categoryTagsRV = findViewById(R.id.categoryTagsRV);
        TextView categoriesSelectedTV = findViewById(R.id.categoriesSelectedTV);
        categoriesSelectedTV.setText(CategoriesUtil.categorySelected);

        CategoryTagsAdapter categoryTagsAdapter = new CategoryTagsAdapter(goodCategoriesDB.listAllGoodCatsDB(),this); //, thisActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        categoryTagsRV.setLayoutManager(layoutManager);
        categoryTagsRV.setAdapter(categoryTagsAdapter);
    }*/

    private void initButtons(){
        Button saveBtn = findViewById(R.id.saveBtnBottom);
        Button categoryBtn = findViewById(R.id.categoryBtn);
        TextView dateAddedTV = findViewById(R.id.dateAddedTV);
        ImageButton addCategoryBtn = findViewById(R.id.addCategoryBtn);
 //       Button saveTopBtn = findViewById(R.id.saveBtnTop);

        if(CategoriesUtil.categorySelected == null){
            CategoriesUtil.categorySelected = goodCategoriesDB.listAllGoodCatsDB().get(0).getCategoryName();
        }

        categoryBtn.setText(CategoriesUtil.categorySelected);
        dateAddedTV.setText(CalendarUtils.formattedDate(LocalDate.now()));

        categoryBtn.setOnClickListener(view -> addToDoDialog());

      //  if(goodTitle.getText().toString().equals(addThing)){
        if(addThingTrue){ //is Adding new
           // saveBtn.setOnClickListener(this::onClick);
            saveBtn.setOnClickListener(view -> save());
//            saveTopBtn.setOnClickListener(this::onClick);
            Log.i("AddGoodThing","adding");
        }else{ //is Edit
            saveBtn.setOnClickListener(view -> onUpdate());
//            saveTopBtn.setOnClickListener(this::onUpdate);
            Log.i("AddGoodThing","updating");
        }

        if(Objects.equals(CategoriesUtil.categorySelected, "") || CategoriesUtil.categorySelected ==null){
            CategoriesUtil.categorySelected = "To Do";
        }

        addCategoryBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddNewCategoryActivity.class)));

    }

    /*private void initTimePickers(){
        TextView startTimeTextView = findViewById(R.id.startTimeTV);

        startTimeTextView.setOnClickListener(view -> {
                    // on below line we are getting the
                    // instance of our calendar.
                    final Calendar c = Calendar.getInstance();

                    // on below line we are getting our hour, minute.
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);

                    // on below line we are initializing our Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(ToDoAddThingActivity.this,
                            (view1, hourOfDay, minute1) -> {
                                // on below line we are setting selected time
                                // in our text view.
                                startTimeTextView.setText(hourOfDay + ":" + minute1);
                            }, hour, minute, true);
                    // at last we are calling show to
                    // display our time picker dialog.
                    timePickerDialog.show();
        });
    }*/

    private void initDatePickers() {
        TextView dateToStartTV = findViewById(R.id.DateToStartTV);
        TextView dateToEndTV = findViewById(R.id.DateToEndTV);

        if(CalendarUtils.dateToStart == null){
            CalendarUtils.dateToStart = String.valueOf(CalendarUtils.selectedDate);
        }

        if (CalendarUtils.dateToEnd == null){
            CalendarUtils.dateToEnd = String.valueOf(CalendarUtils.selectedDate);
        }

        if(CalendarUtils.dateToStart == null || CalendarUtils.dateToStart.equals("date not set")){
            dateToStartTV.setText(CalendarUtils.formattedDate(LocalDate.parse(CalendarUtils.selectedDate.toString())));

        }else{
            dateToStartTV.setText(CalendarUtils.formattedDate(LocalDate.parse(CalendarUtils.dateToStart)));
        }

        if(CalendarUtils.dateToEnd == null || CalendarUtils.dateToEnd.equals("date not set")){
            dateToEndTV.setText(CalendarUtils.formattedDate(LocalDate.parse(CalendarUtils.selectedDate.toString())));

        }else{
            dateToEndTV.setText(CalendarUtils.formattedDate(LocalDate.parse(CalendarUtils.dateToEnd)));
        }


        //dateToEndTV.setText(CalendarUtils.formattedDate(LocalDate.parse(CalendarUtils.dateToEnd)));

        dateToStartTV.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();

            //day, month, year
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ToDoAddThingActivity.this,
                    (datePicker, year1, month1, dayOfMonth) -> {
                        LocalDate date1 = LocalDate.of(year1, month1 + 1, dayOfMonth);
                        //recreate();
                        dateToStartTV.setText(CalendarUtils.formattedDate(date1));
                        CalendarUtils.dateToStart = date1.toString();

                        CalendarUtils.dateToEnd = date1.toString();
                        dateToEndTV.setText(CalendarUtils.formattedDate(date1));
                     //   if(CalendarUtils.dateToEnd.equals("date not set")){
                       //     CalendarUtils.dateToEnd = date1.toString();
                        //}
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        dateToEndTV.setOnClickListener(view -> {
            int setYear = LocalDate.now().getYear();
            int setMonth = LocalDate.now().getMonthValue()-1;
            int setDay = LocalDate.now().getDayOfMonth();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ToDoAddThingActivity.this,
                    (datePicker, year, month, dayOfMonth) -> {
                        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                        dateToEndTV.setText(CalendarUtils.formattedDate(date));
                        CalendarUtils.dateToEnd = date.toString();
                    },
                    setYear, setMonth, setDay);
            datePickerDialog.show();
        });
    }


    private void initStates(){
        RecyclerView statesRadioRV = findViewById(R.id.statesRadioRecyclerview);
        ArrayList<String> arrayList = new ArrayList<>();
    //    arrayList.add("New Additions");
        arrayList.add("To Do");
        arrayList.add("Done");
        arrayList.add("Doing");
        arrayList.add("Not Doing");
        arrayList.add("Happy it exists");
        //arrayList.add("To Do Again");
     //   arrayList.add("Loved");

        // Initialize listener
       itemClickListener = s -> {
           // Notify adapter
           statesRadioRV.post(() -> toDoTimesTagAdapter.notifyDataSetChanged());
           //CategoriesUtil.stateSelected = s;
           CategoriesUtil.categorySelected = s.getCategoryName();
           CategoriesUtil.categoryImgId = s.getImgId();
           CategoriesUtil.categoryLogoId = s.getLogoId();
           //Log.i("State radio",CategoriesUtil.stateSelected);
       };
        // Set layout manager
        //   statesRadioRV.setLayoutManager(new LinearLayoutManager(this));
        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),140);

        //mGridLayoutManager = new GridLayoutManager(this, mNoOfColumns);
        statesRadioRV.setLayoutManager(new GridLayoutManager(this,mNoOfColumns)); //4
        // Initialize adapter
        toDoTimesTagAdapter = new ToDoTimesTagAdapter(arrayList, itemClickListener);
        // set adapter
        statesRadioRV.setAdapter(toDoTimesTagAdapter);
    }

    public void addToDoDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View subView = layoutInflater.inflate(R.layout.good_things_states_card, null);

        TextView goodThingState = subView.findViewById(R.id.stateTextView);
        RecyclerView goodThingsRV = subView.findViewById(R.id.goodThingsRV);

        goodThingState.setVisibility(View.GONE);


        // Initialize listener
        itemClickListener = s -> {
            // Notify adapter
            goodThingsRV.post(() -> categoryTagsAdapter.notifyDataSetChanged());
            //CategoriesUtil.categorySelected = s;
            CategoriesUtil.categorySelected = s.getCategoryName();
            CategoriesUtil.categoryImgId = s.getImgId();
            CategoriesUtil.categoryLogoId = s.getLogoId();
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        goodThingsRV.setLayoutManager(layoutManager);
        categoryTagsAdapter = new CategoryTagsAdapter(goodCategoriesDB.listAllGoodCatsDB(), itemClickListener, this); //, thisActivity);
        goodThingsRV.setAdapter(categoryTagsAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Category");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Confirm", (dialogInterface, i) -> initButtons());
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> Toast.makeText(ToDoAddThingActivity.this, "cancelled", Toast.LENGTH_SHORT).show());
        builder.show();
    }

    public void save(){
        String goodThingString = goodThingEditText.getText().toString();

        String inspiredByString = inspiredByET.getText().toString();
        if(inspiredByString.isEmpty()){
            inspiredByString = "";
        }

        CategoriesUtil.timeFrame = "day";

        if(!goodThingString.isEmpty() && CategoriesUtil.categorySelected!=null && CategoriesUtil.stateSelected!=null){
            //Log.i("good Thing","category is "+CategoriesUtil.categorySelected+", state is "+CategoriesUtil.stateSelected+", dates: "+CalendarUtils.dateToStart+" "+CalendarUtils.dateToEnd);
            goodThingsDB.addGoodThing(new ToDoThingModel(0, CategoriesUtil.categorySelected,goodThingString,inspiredByString,CategoriesUtil.categoryLogoId, "#000000",CategoriesUtil.stateSelected,LocalDate.now().toString(),CategoriesUtil.timeFrame,CalendarUtils.dateToStart,CalendarUtils.dateToEnd,"date"));
            Toast.makeText(ToDoAddThingActivity.this, "Added "+goodThingString + " in "+ CategoriesUtil.categorySelected, Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "nothing entered...cancelling", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void onUpdate(){
        String goodThingString = goodThingEditText.getText().toString();

        String inspiredByString = inspiredByET.getText().toString();
        if(inspiredByString.isEmpty()){
            inspiredByString = "";
        }

        if(!goodThingString.isEmpty()){
            goodThingsDB.updateGoodThing(new ToDoThingModel(CategoriesUtil.goodThingId, CategoriesUtil.categorySelected,goodThingString,inspiredByString,CategoriesUtil.categoryLogoId, "#000000",CategoriesUtil.stateSelected,"",CategoriesUtil.timeFrame,CalendarUtils.dateToStart,CalendarUtils.dateToEnd,"date"));
            Toast.makeText(ToDoAddThingActivity.this, "Updating... "+goodThingString, Toast.LENGTH_SHORT).show();
        }else{
            goodThingsDB.deleteGoodThing(new ToDoThingModel(CategoriesUtil.goodThingId, CategoriesUtil.categorySelected,goodThingString,CategoriesUtil.stateSelected,"date"));
            Toast.makeText(ToDoAddThingActivity.this, "deleting... "+CategoriesUtil.goodThing, Toast.LENGTH_SHORT).show();
        }
        finish();
    }


    public void cancelThing(View view){
        finish();
        Toast.makeText(this, "Cancelling...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(goodThingsDB != null){
            goodThingsDB.close();
        }
        if(goodCategoriesDB != null){
            goodCategoriesDB.close();
        }
    }
}