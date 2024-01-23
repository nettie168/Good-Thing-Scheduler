package com.example.goodthingscheduler.scheduleAddRoutineHabits;

import android.content.Context;
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

import com.example.goodthingscheduler.scheduleHabits.RoutineUtils;
import com.example.goodthingscheduler.Calendar.CalendarUtils;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.scheduleHabits.HabitDBHandler;
import com.example.goodthingscheduler.scheduleHabits.HabitListDBHandler;
import com.example.goodthingscheduler.scheduleHabits.HabitModel;

import java.util.ArrayList;
import java.util.Objects;

public class AddHabitsAdapter extends RecyclerView.Adapter<AddHabitsAdapter.ViewHolder> {

    private final ArrayList<HabitModel> habitArrayList;
    private final Context context;
    private final HabitDBHandler habitDBHandler;
    private HabitListDBHandler habitListDBHandler;

    public AddHabitsAdapter(ArrayList<HabitModel> habitArrayList, Context context) {
        this.habitArrayList = habitArrayList;
        this.context=context;
        habitDBHandler = new HabitDBHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_row,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_habit_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
     //   habitDBHandler = new HabitDBHandler(context);
        habitListDBHandler = new HabitListDBHandler(context);

        HabitModel habit = habitArrayList.get(position);

        holder.taskText.setText(habit.getTask());
        holder.checkBox.setVisibility(View.GONE);
        holder.habitImg.setImageResource(habit.getHabitImgId());

        //loop over RoutineUtils.routinehabitlist, get Task
        //if Task == habit.getTask --> colour background purple, break
        //if Task !equals --> white

        holder.habitSelTick.setVisibility(View.GONE);

        for(int i = 0; i < RoutineUtils.routineHabitList.size(); i++){
           // Log.i("")
            if(Objects.equals(RoutineUtils.routineHabitList.get(i).getTask(), habit.getTask())){
                holder.cardView.setCardBackgroundColor(Color.parseColor("#6352a9"));
                holder.taskText.setTextColor(Color.WHITE);
                holder.habitSelTick.setVisibility(View.VISIBLE);
                break;
            }else {
                holder.habitSelTick.setVisibility(View.GONE);
                holder.cardView.setCardBackgroundColor(Color.WHITE);
                holder.taskText.setTextColor(Color.parseColor("#6352a9"));
            }
        }

    /*    if(RoutineUtils.routineHabitList.contains(habit)){
            holder.cardView.setCardBackgroundColor(Color.parseColor("#6352a9"));
            holder.taskText.setTextColor(Color.WHITE);
            holder.habitSelTick.setVisibility(View.VISIBLE);
        }else{
            holder.habitSelTick.setVisibility(View.GONE);
            holder.cardView.setCardBackgroundColor(Color.WHITE);
            holder.taskText.setTextColor(Color.parseColor("#6352a9"));
        }*/

        //If habit already exists in this routine or other routine
        //tickImage visible
        //else gone
        //holder.habitSelTick.setVisibility(View.GONE);

        holder.cardView.setOnClickListener(view -> {
            if(habit.getStatus()==0){
                habit.setStatus(1);
                holder.cardView.setCardBackgroundColor(Color.parseColor("#6352a9"));
                holder.taskText.setTextColor(Color.WHITE);
                holder.habitSelTick.setVisibility(View.VISIBLE);

           //     habitsList.add(new HabitModel("wake up", 0, R.drawable.ic_baseline_wb_sunny_24));

                // if habit already in, but in other routine, do a pop-up saying so (or grey-out?)
                RoutineUtils.routineHabitList.add(new HabitModel(habit.getId(), RoutineUtils.routineSel, habit.getTask(), "", CalendarUtils.selectedDate.toString(), "ongoing", 1, "constant", 0, "Mon,Tue,Wed,Thur,Fri,Sat,Sun", 0, "", habit.getHabitImgId()));
                //habitDBHandler.addHabit(new HabitModel(habit.getId(), CalendarUtils.selectedDate.toString(), RoutineUtils.routineSel, habit.getTask(), "",0, habit.getHabitImgId()));
            //    habitListDBHandler.addHabit(new HabitModel(habit.getId(), RoutineUtils.routineSel, habit.getTask(), "", CalendarUtils.selectedDate.toString(), "ongoing", 1, "constant", 0, "Mon,Tue,Wed,Thur,Fri,Sat,Sun", 0, "", habit.getHabitImgId()));
                //habitListDBHandler.addHabit(new HabitModel(habit.getId(), CalendarUtils.selectedDate.toString(), AddRoutineUtils.routineSel, habit.getTask(), "",0, habit.getHabitImgId()));
            }else{
                habit.setStatus(0);
                holder.habitSelTick.setVisibility(View.GONE);

                //    holder.cardView.setCardBackgroundColor(0x00000000);
                //holder.cardView.getBackground().clearColorFilter();
                //   holder.cardView.setCardBackgroundColor(null);
                holder.cardView.setCardBackgroundColor(Color.WHITE);
                holder.taskText.setTextColor(Color.parseColor("#6352a9"));

                RoutineUtils.routineHabitList.remove(new HabitModel(habit.getId(), RoutineUtils.routineSel, habit.getTask(), "", CalendarUtils.selectedDate.toString(), "ongoing", 1, "constant", 0, "Mon,Tue,Wed,Thur,Fri,Sat,Sun", 0, "", habit.getHabitImgId()));
        //        habitDBHandler.deleteHabit(new HabitModel(habit.getId(), CalendarUtils.selectedDate.toString(), RoutineUtils.routineSel, habit.getTask(), "",0, habit.getHabitImgId()));
              //  habitListDBHandler.deleteHabit(new HabitModel(habit.getId(), RoutineUtils.routineSel, habit.getTask(), "", CalendarUtils.selectedDate.toString(), "ongoing", 1, "constant", 0, "Mon,Tue,Wed,Thur,Fri,Sat,Sun", 0, "", habit.getHabitImgId()));

            }
        });

    }

    public int getItemCount(){
        return habitArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView taskText;
        CheckBox checkBox;
        public ImageView habitSelTick;
        public ImageView habitImg;

        public ViewHolder(View view){
            super(view);
            habitSelTick = view.findViewById(R.id.habitSelTick);
            taskText = view.findViewById(R.id.habit);
            checkBox = view.findViewById(R.id.checkbox);
            cardView = view.findViewById(R.id.cardView);
            habitImg = view.findViewById(R.id.habitImg);
        }
    }

}
