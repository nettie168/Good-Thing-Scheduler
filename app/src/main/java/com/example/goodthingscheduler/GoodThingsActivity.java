package com.example.goodthingscheduler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;

public class GoodThingsActivity extends AppCompatActivity {

 //   static ArrayList<String> notes = new ArrayList<>();
   // static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_things);
     //   Objects.requireNonNull(getSupportActionBar()).setTitle("My App");

        setBottomNavMenu();

       // initViews();
     //   setAddNoteFAB();

        ListView listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if (set == null) {
            SchedulerActivity.notes.add("Example note");
        } else {
            SchedulerActivity.notes = new ArrayList(set);
        }

        // Using custom listView Provided by Android Studio
        SchedulerActivity.arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, SchedulerActivity.notes);

        listView.setAdapter(SchedulerActivity.arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Going from MainActivity to NotesEditorActivity
                Intent intent = new Intent(getApplicationContext(), ReflectionActivity.class);
                intent.putExtra("noteId", i);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {

            final int itemToDelete = i;
            // To delete the data from the App
            new AlertDialog.Builder(GoodThingsActivity.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SchedulerActivity.notes.remove(itemToDelete);
                            SchedulerActivity.arrayAdapter.notifyDataSetChanged();
                            SharedPreferences sharedPreferences1 = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                            HashSet<String> set1 = new HashSet(SchedulerActivity.notes);
                            sharedPreferences1.edit().putStringSet("notes", set1).apply();
                        }
                    }).setNegativeButton("No", null).show();
            return true;
        });
    }

 /*   public void initViews(){
        //noteRecyclerView = (RecyclerView) findViewById(R.id.notesRecyclerView);
        addNoteFAB = (FloatingActionButton) findViewById(R.id.addNoteFAB);
    }

    public void setAddNoteFAB(){
        addNoteFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),
                        NoteEditorActivity.class));
            }
        });
    }*/

    public void setBottomNavMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.goodThings);


        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
             //       case R.id.todo:
               //         startActivity(new Intent(getApplicationContext(),ToDoActivity.class));
                 //       overridePendingTransition(0,0);
                   //     return true;
                    case R.id.goodThings:
                        return true;
                    case R.id.calendar:
                        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}