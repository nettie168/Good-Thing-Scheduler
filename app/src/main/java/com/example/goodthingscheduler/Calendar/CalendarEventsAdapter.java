package com.example.goodthingscheduler.Calendar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.SchedulerActivity;
import com.example.goodthingscheduler.toDoThings.ToDoThingModel;

import java.util.ArrayList;

public class CalendarEventsAdapter extends RecyclerView.Adapter<CalendarEventsAdapter.ViewHolder> {

    private final ArrayList<ToDoThingModel> routineList;
    private final Context context;

    public CalendarEventsAdapter(ArrayList<ToDoThingModel> routineList, Context context) {
        this.routineList = routineList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_event,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ToDoThingModel routine = routineList.get(position);

      //  if(position==2){
        //    holder.calendarEventTV.setText("+");
        //}else{
            holder.calendarEventTV.setText(routine.getGoodThing());
            //Log.i("calendar event", routine.getGoodThing());
        //}
    }


    public int getItemCount(){
        return routineList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView calendarEventTV;

        public ViewHolder(View view){
            super(view);
            calendarEventTV = view.findViewById(R.id.calendarEventTV);
        }
    }

}
