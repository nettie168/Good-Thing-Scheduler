package com.example.goodthingscheduler.scheduleHabits;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.XPCount.XPCountModel;
import com.example.goodthingscheduler.XPCount.XPDayDBHandler;
import com.example.goodthingscheduler.XPCount.XPUtils;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder> {

    private final ArrayList<HabitModel> habitListDBArray;
    private final Context context;
    private final ArrayList<HabitModel> dailyHabitsDBArray;
  //  private HabitDBHandler habitDBHandler;
   // private HabitListDBHandler habitListDBHandler;
    private DailyHabitsDBHandler dailyHabitsDBHandler;
    private XPDayDBHandler xpDayDBHandler;

    //TODO have swipe right to move task to tomorrow
    //swipe left to "pause, get rid, move to future
    //icons to represent habits (not just sun)

    public HabitAdapter(ArrayList<HabitModel> habitListDBArray, ArrayList<HabitModel> dailyHabitsDBArray, Context context) {
        this.habitListDBArray = habitListDBArray;
        this.context = context;
        this.dailyHabitsDBArray = dailyHabitsDBArray;
   //     habitDBHandler = new HabitDBHandler(context);
     //   habitListDBHandler = new HabitListDBHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_row,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
       // habitDBHandler = new HabitDBHandler(context);
       // Log.i("Habit Adapter","size of habitListDBArray: "+habitListDBArray.size());
        //Log.i("Habit Adapter","size of dailyHabitDBArray: "+dailyHabitsDBArray.size());

        HabitModel habitFromList = habitListDBArray.get(position);
        HabitModel dailyHabitDB = dailyHabitsDBArray.get(position);

        dailyHabitsDBHandler = new DailyHabitsDBHandler(context);
        xpDayDBHandler = new XPDayDBHandler(context);

        holder.taskText.setText(habitFromList.getTask());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(ToBoolean(dailyHabitDB.getStatus()));
        holder.habitImg.setImageResource(habitFromList.getHabitImgId());

        if(habitFromList.getDetail()==null || habitFromList.getDetail().isEmpty()){
            holder.detailIcon.setVisibility(View.GONE);
        }else{
            holder.detailIcon.setVisibility(View.VISIBLE);
        }

     //   if(habit.getStatus()==0){
        if(dailyHabitDB.getStatus()==0){
            //Log.i("habits DB status is ", String.valueOf(habitDB.getStatus()));
            holder.cardView.setCardBackgroundColor(Color.WHITE);
            holder.taskText.setTextColor(Color.BLUE);
        }else{
            holder.cardView.setCardBackgroundColor(Color.parseColor("#6352a9"));
            holder.taskText.setTextColor(Color.WHITE);
        }

        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(dailyHabitDB.getStatus()==0){
                //update habit status
                //Log.i("habit adapter checked before",dailyHabitDB.getTask()+" status is: "+dailyHabitDB.getStatus());
                dailyHabitDB.setStatus(1);
                dailyHabitsDBHandler.updateHabit(new HabitModel(dailyHabitDB.getId(), habitFromList.getRoutine(), habitFromList.getTask(), CalendarUtils.selectedDate.toString(),1));
                holder.checkBox.setChecked(true);
                holder.cardView.setCardBackgroundColor(Color.parseColor("#6352a9"));
                holder.taskText.setTextColor(Color.WHITE);

                //update XP
                XPUtils.dayXP = new XPCountModel(CalendarUtils.selectedDate.toString(),XPUtils.dayXP.getXp()+5);
                xpDayDBHandler.updateDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(), XPUtils.dayXP.getXp()));
                Log.i("Habit Adapter, on tick","xp is: "+XPUtils.dayXP.getXp()+5);
                //start TaskTicked Activity
                RoutineUtils.habitName = habitFromList.getTask();
                RoutineUtils.habitImgId = habitFromList.getHabitImgId();
                Intent taskTickActivity = new Intent(context.getApplicationContext(), TaskTickedActivity.class);
                taskTickActivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(taskTickActivity);

            }else{
                dailyHabitDB.setStatus(0);
                dailyHabitsDBHandler.updateHabit(new HabitModel(dailyHabitDB.getId(), habitFromList.getRoutine(), habitFromList.getTask(), CalendarUtils.selectedDate.toString(),0));

                holder.checkBox.setChecked(false);
                holder.cardView.setCardBackgroundColor(Color.WHITE);
                holder.taskText.setTextColor(Color.BLUE);
                //update XP
                XPUtils.dayXP = new XPCountModel(CalendarUtils.selectedDate.toString(),XPUtils.dayXP.getXp()-5);
                xpDayDBHandler.updateDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(),XPUtils.dayXP.getXp()));
                Log.i("Habit Adapter, untick","xp is: "+XPUtils.dayXP.getXp()+"-5");
            }
        });

   /*     holder.cardView.setOnClickListener(view -> {
            RoutineUtils.habitSelId = dailyHabitDB.getId();
            RoutineUtils.habitImgId = dailyHabitDB.getHabitImgId();
            RoutineUtils.habitName = dailyHabitDB.getTask();

            Intent habitDetailsActivity = new Intent(context.getApplicationContext(), HabitEditDetailsActivity.class);
            habitDetailsActivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(habitDetailsActivity);
        });*/
    }

    private boolean ToBoolean(int n){
        return n!= 0;
    }

    public int getItemCount(){
        return habitListDBArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView taskText;
        CheckBox checkBox;
        public ImageView detailIcon;
        public ImageView habitImg;

        public ViewHolder(View view){
            super(view);
            taskText = view.findViewById(R.id.habit);
            checkBox = view.findViewById(R.id.checkbox);
            detailIcon = view.findViewById(R.id.habitDetailIcon);
            cardView = view.findViewById(R.id.cardView);
            habitImg = view.findViewById(R.id.habitImg);
        }
    }

}
