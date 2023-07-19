package com.example.goodthingscheduler.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.SchedulerActivity;
import com.example.goodthingscheduler.scheduleHabits.HabitDBHandler;
import com.example.goodthingscheduler.toDoThings.ToDoThingModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingsDB;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{
    private final ArrayList<LocalDate> days;
    private final Context context;
    private ToDoThingsDB toDoThingsDB;

    public CalendarAdapter(ArrayList<LocalDate> days, Context context){
        this.days = days;
        this.context = context;
        toDoThingsDB = new ToDoThingsDB(context);
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams  = view.getLayoutParams();
        if(days.size() > 15)
            layoutParams.height = (int) (parent.getHeight()*0.159); //*0.1666666); //0.159
        else
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view);
    }

    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position){

        final LocalDate date = days.get(position);

        if(date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            holder.dayCell.setCardBackgroundColor(Color.parseColor("#b8baf3"));
        }

            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

            String dateToday = LocalDate.now().toString();
            if(date.toString().equals(dateToday)){
                holder.dayCell.setCardBackgroundColor(Color.parseColor("#70ccfd"));
            }else
                holder.dayImageView.setVisibility(View.GONE);

        toDoThingsDB = new ToDoThingsDB(context.getApplicationContext());
        ArrayList<ToDoThingModel> toDoInDay = toDoThingsDB.listToDoInDayCatFilter(date); //toDoThingsDB.listToDoInDay(date);

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.eventsInDayRV.getContext(), LinearLayoutManager.VERTICAL,false);
        layoutManager.setInitialPrefetchItemCount(toDoInDay.size());

        CalendarEventsAdapter calendarEventsAdapter = new CalendarEventsAdapter(toDoInDay,context.getApplicationContext());
        holder.eventsInDayRV.setLayoutManager(layoutManager);
        holder.eventsInDayRV.setAdapter(calendarEventsAdapter);


        holder.dayCell.setOnClickListener(view -> {
            CalendarUtils.selectedDate = date;

            context.startActivity(new Intent(context, SchedulerActivity.class));
        });

        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        YearMonth yearMonth = YearMonth.from(CalendarUtils.selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();

        if (Integer.parseInt(String.valueOf(position)) < dayOfWeek || Integer.parseInt(String.valueOf(position)) > daysInMonth + dayOfWeek - 1) {
                holder.dayOfMonth.setTextColor(Color.GRAY);
                holder.dayCell.setAlpha((float) 0.5);
            }
    }

    @Override
    public int getItemCount(){
        return days.size();
    }

   /* public interface OnItemListener{
        //void onItemClick(int position, String dayText);
        void onItemClick(int position, LocalDate date);
    }*/

    public static class CalendarViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener{

        public final TextView dayOfMonth;
        private final CardView dayCell;
        private final RecyclerView eventsInDayRV;
        private final ImageView dayImageView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
        //    itemView.setOnClickListener(this);
            dayCell = itemView.findViewById(R.id.calendarCellCard);
            eventsInDayRV = itemView.findViewById(R.id.eventsDayRV);
            dayImageView = itemView.findViewById(R.id.dayImageView);
        }

       // @Override
     //   public void onClick(View view) {
    //        onItemListener.onItemClick(getAdapterPosition(),days.get(getAdapterPosition()));
       // }
    }

}
