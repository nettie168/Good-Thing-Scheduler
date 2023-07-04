package com.example.goodthingscheduler.Challenges;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.ChallengesActivity;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.scheduleAddRoutineHabits.AddHabitsActivity;
import com.example.goodthingscheduler.scheduleAddRoutineHabits.AddRoutineActivity;
import com.example.goodthingscheduler.scheduleHabits.HabitModel;
import com.example.goodthingscheduler.scheduleHabits.RoutineUtils;

import java.util.ArrayList;

public class ChallengeCategoryAdapter extends RecyclerView.Adapter<ChallengeCategoryAdapter.ViewHolder> {

    private final ArrayList<HabitModel> habitArrayList;
    private final Context context;

    public ChallengeCategoryAdapter(ArrayList<HabitModel> habitArrayList, Context context) {
        this.habitArrayList = habitArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_routine_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        HabitModel habit = habitArrayList.get(position);

        holder.routineText.setText(habit.getRoutine());
        holder.routineImg.setImageResource(habit.getHabitImgId());

        holder.cardView.setOnClickListener(view -> {
            RoutineUtils.routineSel = habit.getRoutine();
            RoutineUtils.routineSelStartHour = 9;
            RoutineUtils.routineSelStartMinute = 0;
            Intent intent = new Intent(context, SubChallengesActivity.class);
            ((ChallengesActivity)context).finish();
            context.startActivity(intent);
        });

    }

    public int getItemCount(){
        return habitArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayoutCompat cardView;
        public TextView routineText;
        public ImageView routineImg;

        public ViewHolder(View view){
            super(view);
            routineText =  view.findViewById(R.id.habit);
            cardView = view.findViewById(R.id.addRoutineCardView);
            routineImg = view.findViewById(R.id.habitImg);
        }
    }

}
