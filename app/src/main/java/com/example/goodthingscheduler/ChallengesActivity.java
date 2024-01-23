package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.goodthingscheduler.Challenges.ChallengeCategoryAdapter;
import com.example.goodthingscheduler.scheduleHabits.HabitModel;

import java.util.ArrayList;
import java.util.Objects;

public class ChallengesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Challenges");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setLookAfterSelfChallenges();
        setLookOthersChallenges();
    }


    public void setLookAfterSelfChallenges(){

        //Look after yourself (Internal challenges)
//Level 1 challenges (Basic/In the woods/I'm not OK)
//Good things challenges (stop and smell the roses)
        //--3 good things each day, 3 things you're thankful for, 3 things that went well today
//Check-in (reflections) challenges (also express yourself)
        //--how do you feel right now?, if there's something troubling you write it down, future fantastic, letters to loved ones, write down your life as if it was a story
//Re-charge/Things I enjoy challenges (find your stories, read, music, paint, run, baths)
        //--write a list of things you enjoy, do one of these things each day, write about how it went after
//Talking to others
        //--see a person, say good morning to someone, message someone

        ArrayList<HabitModel> challengeCatList = new ArrayList<>();

        //Level 1 (Basic/I'm not ok/In the woods)
        challengeCatList.add(new HabitModel("Good Things", R.drawable.ic_baseline_favorite_24));
        challengeCatList.add(new HabitModel("Check-in", R.drawable.ic_baseline_self_improvement_24));
        challengeCatList.add(new HabitModel("Rest & Recharge", R.drawable.ic_baseline_spa_24));
        challengeCatList.add(new HabitModel("Social", R.drawable.ic_baseline_diversity_3_24));

        //Level 2 (Basic/I'm starting to be OK)
        challengeCatList.add(new HabitModel("Sleep Better", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Reduce screen time", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Walking", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Nature", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Squeaky Clean", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Feel the music", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Laugh On", R.drawable.ic_baseline_diversity_3_24));


        RecyclerView recyclerView = findViewById(R.id.selfcareRV);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        ChallengeCategoryAdapter challengeAdapter = new ChallengeCategoryAdapter(challengeCatList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(challengeAdapter);

    }

    public void setLookOthersChallenges(){

        //Look after yourself (Internal challenges)
//Level 1 challenges (Basic/In the woods/I'm not OK)
//Good things challenges (stop and smell the roses)
        //--3 good things each day, 3 things you're thankful for, 3 things that went well today
//Check-in (reflections) challenges (also express yourself)
        //--how do you feel right now?, if there's something troubling you write it down, future fantastic, letters to loved ones, write down your life as if it was a story
//Re-charge/Things I enjoy challenges (find your stories, read, music, paint, run, baths)
        //--write a list of things you enjoy, do one of these things each day, write about how it went after
//Talking to others
        //--see a person, say good morning to someone, message someone

        ArrayList<HabitModel> challengeCatList = new ArrayList<>();

        //Level 3 (Intermediate/I'm ok)
        challengeCatList.add(new HabitModel("Clean & Tidy", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Finances", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Fitness I", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Loved Ones", R.drawable.ic_baseline_diversity_3_24));


        //Level 4 Look after others/Beast Mode/Give back
        challengeCatList.add(new HabitModel("Beat better/Fitness II", R.drawable.ic_baseline_favorite_24));
        challengeCatList.add(new HabitModel("Resilience", R.drawable.ic_baseline_self_improvement_24));
        challengeCatList.add(new HabitModel("Travel & Adventure", R.drawable.ic_baseline_travel_explore_24));

        //Level 5 (Creating a legacy/Taking care of others)
        challengeCatList.add(new HabitModel("Helping the Environment", R.drawable.ic_baseline_diversity_3_24));
        challengeCatList.add(new HabitModel("Your Community", R.drawable.ic_baseline_diversity_3_24));

        RecyclerView recyclerView = findViewById(R.id.beastRV);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        ChallengeCategoryAdapter challengeAdapter = new ChallengeCategoryAdapter(challengeCatList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(challengeAdapter);

    }
}





//Level 2 (Basic/I'm starting to be OK)
//Sleep and wake up challenges
    //--use a non-phone alarm eg. watch, sunrise alarm, alarm clock, get up at the same time each day, go to bed at the same time (or when tired), make your bed, switch off screens or leave out of bedroom, get dressed for day, get undressed for sleep
//Reduce screen time challenges
    //--turn off phone in the evening, have 1 hour no screen time/doing something you enjoy, turn off screens to eat, no screen wake up, emails only at work,
//Walking challenges
    //--go outside every day, walk, cycle or roll for 10 mins a day, walk, cycle or roll to shops/work,
//Nature challenges
//Cleaning-self challenges
//Start the day challenges (getting dressed)
//Music and dance
//Laughter

//Level 3 (Intermediate/I'm ok)
//Cleaning house challenges
    //--wash 2 plates/have 2 clean plates
//Finances
//Explore challenges (books, food, new things)
//Strength, balance and flexibility
//Giving back (family & friends)



//Look after others/Beast Mode/Give back
//Level 4 (I'm grand)
//Cardio challenges
//Travel and Adventure
//Building resilience/failure

//Level 5 (Creating a legacy/Taking care of others)
//Giving back to your community
//Protecting the Environment







