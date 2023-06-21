package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Objects;

public class ReflectionActivity extends AppCompatActivity {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);
        Objects.requireNonNull(getSupportActionBar()).hide();

        EditText editText = findViewById(R.id.editText);
        TextView countView = findViewById(R.id.countView);

        editText.setHint("My morning is going...");

        //Fetch data that is passed from Main Activity
        Intent intent = getIntent();

        noteId = intent.getIntExtra("noteId",-1);
        if(noteId != -1){
            editText.setText(SchedulerActivity.notes.get(noteId));
        }else{
            SchedulerActivity.notes.add("");
            noteId = SchedulerActivity.notes.size() - 1;
            //SchedulerActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                countView.setText(String.valueOf(charSequence.length()));
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SchedulerActivity.notes.set(noteId, String.valueOf(charSequence));
//                SchedulerActivity.arrayAdapter.notifyDataSetChanged();
                //Creating Object of SharedPreferences to store data in the phone
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(SchedulerActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();
                countView.setText(String.valueOf(charSequence.length()));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void save(View view){
        finish();
        startActivity(new Intent(getApplicationContext(), GoodThingsActivity.class));
    }

}