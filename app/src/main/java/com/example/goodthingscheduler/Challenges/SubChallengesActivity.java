package com.example.goodthingscheduler.Challenges;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.scheduleHabits.HabitModel;

import java.util.ArrayList;

public class SubChallengesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_challenges);
    }

    private void setChallengeList(){
        ArrayList<ChallengeModel> ChallengeList = new ArrayList<>();

        //ChallengeModel challengeModel = new ChallengeModel("Morning Bird", "Get up before noon", "Morning Routines", )

    }


}