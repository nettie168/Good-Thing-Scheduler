package com.example.goodthingscheduler.scheduleHabits;

import static android.graphics.Color.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.ReflectionActivity;
import com.example.goodthingscheduler.SchedulerActivity;
import com.example.goodthingscheduler.toDoCategories.CategoryAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class TaskTickedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_ticked);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button nextTaskBtn = findViewById(R.id.nextTaskBtn);
       // @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView jumpingDragon = findViewById(R.id.dragonJump);
        jumpingDragon.setImageResource(R.drawable.jump_item);
        AnimationDrawable jumpingAnimation = (AnimationDrawable) jumpingDragon.getDrawable();
       // jumpingAnimation.getDuration(0);
       // jumpingAnimation.setOneShot(true);
     //   for(int i = 0; i <5; i++){
            jumpingAnimation.start();
       //     jumpingAnimation.setOneShot(true);
       // }

        //remember to give attribute to mixkit
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.mixkitbellachievement);
        mp.start();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                mp.release();
               // jumpingAnimation.stop();
            }
        });

        final KonfettiView konfettiView = findViewById(R.id.konfettiView);
        EmitterConfig emitterConfig = new Emitter(5L, TimeUnit.SECONDS).perSecond(50);
        Party party = new PartyFactory(emitterConfig)
                .angle(270)
                .spread(90)
                .setSpeedBetween(1f, 5f)
                .timeToLive(2000L)
               // .shapes(new Shape.Rectangle(0.2f), drawableShape)
           //     .shapes(new Shape.Circle(0.2f))
       // new ColorDrawable(Color.parseColor("#70ccfd"))
                .colors(Collections.singletonList(BLUE))
                // .colors(WHITE,GREEN)
                .sizes(new Size(12, 5f, 0.2f))
                .position(0.0, 0.0, 1.0, 0.0)
                .build();

        konfettiView.start(party);

        setExtendedToDosRV();
        nextTaskBtn.setOnClickListener(view -> {
            mp.release();
            finish();
            //startActivity(new Intent(getApplicationContext(), SchedulerActivity.class));
        });

    }

    private void setExtendedToDosRV(){
        RecyclerView extendedToDosRV = findViewById(R.id.extendedToDoRV);

        ArrayList<HabitModel> extendedToDosArray = new ArrayList<>();
        extendedToDosArray.add(new HabitModel("How did "+RoutineUtils.habitName+" go?",1,RoutineUtils.habitImgId));
        extendedToDosArray.add(new HabitModel("What went well about "+RoutineUtils.habitName+"?",1,RoutineUtils.habitImgId));
        //extendedToDosArray.add(new HabitModel("What's three things you're thankful for?",1,RoutineUtils.habitImgId));
        extendedToDosArray.add(new HabitModel("What's made you smile today?",1,RoutineUtils.habitImgId));
        //extendedToDosArray.add(new HabitModel("If your life were a story, how would it go?",1,RoutineUtils.habitImgId));

        ExtendedToDosAdapter extendedToDosAdapter = new ExtendedToDosAdapter(extendedToDosArray, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        extendedToDosRV.setLayoutManager(layoutManager);
        extendedToDosRV.setAdapter(extendedToDosAdapter);
    }

}