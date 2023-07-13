package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Objects;

public class SceneLocationActivity extends AppCompatActivity {

    Boolean playing;
    MediaPlayer frogsMP;

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

        findViewById(R.id.mountainSelBtn).setOnClickListener(this::onSceneThemeChangeClick);
        findViewById(R.id.underwaterSelBtn).setOnClickListener(this::onSceneThemeChangeClick);
        findViewById(R.id.snowMountainsSelBtn).setOnClickListener(this::onSceneThemeChangeClick);

        //sceneBackground = findViewById(R.id.sceneBackground);
        setSoundScapeMusic();
    }

    public void onSceneThemeChangeClick(View v){
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
                playFrogsBtn.setBackgroundResource(R.drawable.baseline_stop_24); //view.gone
            }
        });
    }

}