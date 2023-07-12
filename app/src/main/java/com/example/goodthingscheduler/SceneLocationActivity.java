package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;

import java.util.Objects;

public class SceneLocationActivity extends AppCompatActivity {

    Boolean playing;
    MediaPlayer frogsMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_location);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);


        setSoundScapeMusic();
    }

    private void setSoundScapeMusic() {
        ImageButton playFrogsBtn = findViewById(R.id.playFrogsBtn);
        frogsMP = MediaPlayer.create(this, R.raw.nikitralalafrogsandbirds);
        playing = false;

        playFrogsBtn.setOnClickListener(view -> {
            if(playing){
                frogsMP.stop();
                frogsMP = MediaPlayer.create(this, R.raw.nikitralalafrogsandbirds);
                //frogsMP.release();
                playing = false;
                playFrogsBtn.setBackgroundResource(R.drawable.baseline_play_arrow_24);
            }else{
                // play!
                frogsMP.start();
                playing = true;
                //playFrogsBtn.setBackgroundTint(Color.parseColor("#70ccfd"));
                playFrogsBtn.setBackgroundResource(R.drawable.baseline_stop_24);
            }
        });
    }
}