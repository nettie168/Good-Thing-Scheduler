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
import com.example.goodthingscheduler.ReflectionActivity;
import com.example.goodthingscheduler.SchedulerActivity;
import com.example.goodthingscheduler.XPCount.XPCountModel;
import com.example.goodthingscheduler.XPCount.XPDayDBHandler;

import java.util.ArrayList;

public class ExtendedToDosAdapter extends RecyclerView.Adapter<ExtendedToDosAdapter.ViewHolder> {

    private final ArrayList<HabitModel> habitArrayList;
    private final Context context;
    //TODO have swipe right to move task to tomorrow
    //swipe left to "pause, get rid, move to future
    //icons to represent habits (not just sun)

    public ExtendedToDosAdapter(ArrayList<HabitModel> habitArrayList, Context context) {
        this.habitArrayList = habitArrayList;
        this.context=context;
   //     habitDBHandler = new HabitDBHandler(context);
     //   habitListDBHandler = new HabitListDBHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_row,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_card_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
       // habitDBHandler = new HabitDBHandler(context);
        HabitModel habit = habitArrayList.get(position);

        holder.taskText.setText(habit.getTask());
//        holder.checkBox.setVisibility(View.GONE);
        holder.habitImg.setImageResource(habit.getHabitImgId());

        holder.cardView.setOnClickListener(view -> {

            Intent mActivity = new Intent(context.getApplicationContext(), ReflectionActivity.class);
            mActivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(mActivity);

            //context.startActivity(new Intent(context, HabitEditDetailsActivity.class));
        });
    }

    public int getItemCount(){
        return habitArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView taskText;
      //  CheckBox checkBox;
        public ImageView habitImg;

        public ViewHolder(View view){
            super(view);
            taskText = (TextView) view.findViewById(R.id.habit);
          //  checkBox = view.findViewById(R.id.checkbox);
            cardView = (CardView) view.findViewById(R.id.cardView);
            habitImg = (ImageView) view.findViewById(R.id.habitImg);
        }
    }

}
