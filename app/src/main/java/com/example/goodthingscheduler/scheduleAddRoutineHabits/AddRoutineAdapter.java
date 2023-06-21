package com.example.goodthingscheduler.scheduleAddRoutineHabits;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.scheduleHabits.RoutineUtils;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.scheduleHabits.HabitDBHandler;
import com.example.goodthingscheduler.scheduleHabits.HabitModel;

import java.util.ArrayList;

public class AddRoutineAdapter extends RecyclerView.Adapter<AddRoutineAdapter.ViewHolder> {

    private final ArrayList<HabitModel> habitArrayList;
    private final Context context;
    private HabitDBHandler habitDBHandler;
    //TODO have swipe right to move task to tomorrow
    //swipe left to "pause, get rid, move to future
    //icons to represent habits (not just sun)

    public AddRoutineAdapter(ArrayList<HabitModel> habitArrayList, Context context) {
        this.habitArrayList = habitArrayList;
        this.context=context;
        habitDBHandler = new HabitDBHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_row,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_routine_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        habitDBHandler = new HabitDBHandler(context);
        HabitModel habit = habitArrayList.get(position);

        holder.routineText.setText(habit.getRoutine());
        holder.routineImg.setImageResource(habit.getHabitImgId());

        holder.cardView.setOnClickListener(view -> {
            //start Add Habits Activity
            RoutineUtils.routineSel = habit.getRoutine();
            RoutineUtils.routineSelStartHour = 9;
            RoutineUtils.routineSelStartMinute = 0;
            Intent intent = new Intent(context, AddHabitsActivity.class);
            ((AddRoutineActivity)context).finish();
            context.startActivity(intent);
        });

    }

    public int getItemCount(){
        return habitArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView routineText;
        public ImageView routineImg;

        public ViewHolder(View view){
            super(view);
            routineText = (TextView) view.findViewById(R.id.habit);
            cardView = (CardView) view.findViewById(R.id.addRoutineCardView);
            routineImg = (ImageView) view.findViewById(R.id.habitImg);
        }
    }

}
