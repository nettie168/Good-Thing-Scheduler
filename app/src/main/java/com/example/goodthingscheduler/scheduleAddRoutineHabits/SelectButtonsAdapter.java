package com.example.goodthingscheduler.scheduleAddRoutineHabits;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.ToDoListActivity;
import com.example.goodthingscheduler.scheduleHabits.RoutineUtils;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.example.goodthingscheduler.toDoCategories.GoodCategoriesDB;
import com.example.goodthingscheduler.toDoCategories.GoodCategoryModel;

import java.util.ArrayList;

public class SelectButtonsAdapter extends RecyclerView.Adapter<SelectButtonsAdapter.ViewHolder> {

    private final ArrayList<String> list;
    final private Context context;

    public SelectButtonsAdapter(ArrayList<String> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_button,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        String dayOfWeek = list.get(position);

        holder.button.setText(dayOfWeek);
        //Log.i("select adapter","dayOfWeek:"+dayOfWeek);

        ArrayList<String> trimDays = new ArrayList<>();

        for(int i = 0; i < RoutineUtils.daysOfWeekSelected.size(); i++){
            String day = RoutineUtils.daysOfWeekSelected.get(i).trim();
            trimDays.add(day);
          //  Log.i("sel adapter",RoutineUtils.routineSel+", day="+day+",trimDays="+trimDays.get(i));
        }

            //Log.i("Select Btn Adapter","days of Week Selected:"+RoutineUtils.daysOfWeekSelected);
            if(trimDays.contains(dayOfWeek)){
                holder.button.setBackgroundColor(Color.parseColor("#6352a9"));
                holder.button.setTextColor(Color.WHITE);
            }else {
                holder.button.setBackgroundColor(Color.WHITE);
                holder.button.setTextColor(Color.parseColor("#6352a9"));
            }

        holder.button.setOnClickListener(view -> {
            if(RoutineUtils.daysOfWeekSelected.contains(dayOfWeek)){
                //Log.i("select btn adapter remove","day of week thing is:"+dayOfWeek);
                RoutineUtils.daysOfWeekSelected.remove(dayOfWeek);
                holder.button.setBackgroundColor(Color.WHITE);
                holder.button.setTextColor(Color.parseColor("#6352a9"));

            }else {
                RoutineUtils.daysOfWeekSelected.add(dayOfWeek.trim());
                //Log.i("select btn adapter add","day of week thing is:"+dayOfWeek);
                holder.button.setBackgroundColor(Color.parseColor("#6352a9"));
                holder.button.setTextColor(Color.WHITE);
            }

        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final private Button button;

        public ViewHolder(View view){
            super(view);
            button = view.findViewById(R.id.textButton);
        }
    }
}
