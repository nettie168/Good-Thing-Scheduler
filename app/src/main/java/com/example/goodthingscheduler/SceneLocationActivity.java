package com.example.goodthingscheduler;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class SceneLocationActivity extends AppCompatActivity {

    Boolean playing;
    MediaPlayer frogsMP;
    MediaPlayer oceanMP;
    MediaPlayer forestMP;
    MediaPlayer motivateMP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_scene_location);
        Objects.requireNonNull(getSupportActionBar()).hide();
       // Objects.requireNonNull(getSupportActionBar()).setTitle("");
      //  Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        //setCustomizedThemes(this,getThemeColor(this));
        //themeUtils.onActivityCreateSetTheme(this);

        /*findViewById(R.id.mountainSelBtn).setOnClickListener(this::onSceneThemeChangeClick);
        findViewById(R.id.underwaterSelBtn).setOnClickListener(this::onSceneThemeChangeClick);
        findViewById(R.id.snowMountainsSelBtn).setOnClickListener(this::onSceneThemeChangeClick);
        */

        //sceneBackground = findViewById(R.id.sceneBackground);

/*        int currentNightMode = Configuration.UI_MODE_NIGHT_MASK;

    //    int currentNightMode = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active on device
                findViewById(R.id.goodThingTextView).setBackgroundColor(Color.WHITE);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active on device
                findViewById(R.id.goodThingTextView).setBackgroundColor(Color.BLACK);
                break;
        }*/

        setSoundScapeMusic();
        setReflections();
    }

    /*public void onSceneThemeChangeClick(View v){
        findViewById(R.id.mountainSelBtn).setOnClickListener(this::onSceneThemeChangeClick);
        findViewById(R.id.underwaterSelBtn).setOnClickListener(this::onSceneThemeChangeClick);
        findViewById(R.id.snowMountainsSelBtn).setOnClickListener(this::onSceneThemeChangeClick);


        switch (v.getId()){
            case R.id.mountainSelBtn:
                ThemeUtils.changeToTheme(this, ThemeUtils.THEME_MOUNTAIN_GREEN);
                Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd"))); //"#2f004d"
               // sceneBackground.setBackgroundColor((Color.parseColor("#70ccfd")));
              //  sceneBackground.setImageResource(R.drawable.mountain_scene_rect_blue);

                break;
            case R.id.underwaterSelBtn:
            //    sceneBackground.setBackgroundColor((Color.BLACK));
                Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.BLACK)); //"#2f004d"

                ThemeUtils.changeToTheme(this, ThemeUtils.THEME_UNDERWATER);

              //  sceneBackground.setBackgroundColor((Color.MAGENTA));
            //    sceneBackground.setImageResource(R.drawable.mountain_water_scene_sky);
                Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.MAGENTA)); //"#2f004d"
                break;
            case R.id.snowMountainsSelBtn:
                ThemeUtils.changeToTheme(this, ThemeUtils.THEME_SNOWY_MOUNTAIN);
            //    sceneBackground.setImageResource(R.drawable.mountain_water_scene_sky);
                break;
        }
    }*/

    private void setReflections(){
        Button reflectionBtn = findViewById(R.id.reflectionBtn);
        reflectionBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ReflectionActivity.class)));

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

       /* if (set == null) {
            notes.add("Example note");
        } else {
            notes = new ArrayList(set);
        }*/
    }

    private void setSoundScapeMusic() {
        ImageButton playFrogsBtn = findViewById(R.id.playFrogsBtn);
        frogsMP = MediaPlayer.create(this, R.raw.nikitralalafrogsandbirds);
        playing = false;

        ImageButton playOceanBtn = findViewById(R.id.playOceanBtn);
        oceanMP = MediaPlayer.create(this, R.raw.calmoceanwaves);

        ImageButton motivateBtn = findViewById(R.id.playMotivateBtn);
        motivateMP = MediaPlayer.create(this, R.raw.buddy);

        ImageButton playForestBtn = findViewById(R.id.playForestBtn);
        forestMP = MediaPlayer.create(this, R.raw.mixkit_spring_forest_with_woodpeckers_1217);

        playFrogsBtn.setOnClickListener(view -> setMediaPlayer(frogsMP, playFrogsBtn));
        playOceanBtn.setOnClickListener(view -> setMediaPlayer(oceanMP, playOceanBtn));
        playForestBtn.setOnClickListener(view -> setMediaPlayer(forestMP, playForestBtn));
        motivateBtn.setOnClickListener(view -> setMediaPlayer(motivateMP, motivateBtn));

    }

    private void setMediaPlayer(MediaPlayer mediaPlayer, ImageButton imageButton) {
      //  Boolean isPlaying = false;
        if (playing) {
            //stop sound playing
            mediaPlayer.pause();
            playing = false;
            imageButton.setBackgroundResource(R.drawable.baseline_play_arrow_24);
        } else {
            // play! (and loop)
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            playing = true;
            imageButton.setBackgroundResource(R.drawable.baseline_stop_24);
        }
    }





}