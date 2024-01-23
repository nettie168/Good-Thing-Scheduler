package com.example.goodthingscheduler.scheduleHabits;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.scheduleAddRoutineHabits.AddHabitsActivity;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {

    private final ArrayList<RoutineModel> routineList;
    private final Context context;
    private RoutineListDBHandler routineListDBHandler;
    private DailyHabitsDBHandler dailyHabitsDBHandler;
    //public boolean isShowing;
    //more menu button to delete routine, edit eg. routine to repeat, how often, name, & reorder habits

    public RoutineAdapter(ArrayList<RoutineModel> routineList, Context context) {
        this.routineList = routineList;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
       // if(routineList.size()!=0){
           // holder.emptyRVTV.setVisibility(View.GONE);
        //}
        RoutineModel routine = routineList.get(position);
        routineListDBHandler = new RoutineListDBHandler(context);
        dailyHabitsDBHandler = new DailyHabitsDBHandler(context);

        if(routine.getRoutine().equals("No Routines")){
            holder.routineText.setText(routine.getRoutine());
            holder.startTimeTV.setVisibility(View.GONE);
            holder.moreBtn.setVisibility(View.GONE);
            holder.routineTally.setVisibility(View.INVISIBLE);
            holder.hideShowBtn.setVisibility(View.INVISIBLE);
            holder.habitRecyclerView.setVisibility(View.GONE);

        }else {

            ArrayList<HabitModel> dailyHabitsDBArray = dailyHabitsDBHandler.listHabits(routine.getRoutine());

            //   holder.routineCard.setCardBackgroundColor(Color.parseColor("#085c0f"));
            // holder.startTimeTV.setTextColor(Color.WHITE);
            //holder.routineText.setTextColor(Color.WHITE);
            //holder.routineTally.setTextColor(Color.WHITE);

            holder.startTimeTV.setText(LocalTime.of(routine.getStartHour(), routine.getStartMinute()).toString());
            holder.routineText.setText(routine.getRoutine());

            int habitsComplete = 0;
            if (!routine.getHabitArrayList().isEmpty() ) {
                for (int i = 0; i < routine.getHabitArrayList().size(); i++) {
                    int habitStatus = dailyHabitsDBArray.get(i).getStatus();
                    if (habitStatus > 0) {
                        habitsComplete += 1;
                    }
                }
            }


            String routineTallyString = habitsComplete + "/" + routine.getHabitArrayList().size();
            holder.routineTally.setText(routineTallyString);

            if (routine.getOpenClosed() == 1) {
                holder.habitRecyclerView.setVisibility(View.VISIBLE);
            } else {
                holder.habitRecyclerView.setVisibility(View.GONE);
            }

            holder.routineText.setOnClickListener(view -> {
                if (routine.getOpenClosed() == 1) {
                    holder.habitRecyclerView.setVisibility(View.GONE);
                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_down_float);
                    routine.setOpenClosed(0);
                    routineListDBHandler.updateOpenClosed(new RoutineModel(routine.getId(), 0));
                } else {
                    holder.habitRecyclerView.setVisibility(View.VISIBLE);
                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_up_float);
                    routine.setOpenClosed(1);
                    routineListDBHandler.updateOpenClosed(new RoutineModel(routine.getId(), 1));
                }
            });

            holder.hideShowBtn.setOnClickListener(view -> {
                if (routine.getOpenClosed() == 1) {
                    holder.habitRecyclerView.setVisibility(View.GONE);
                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_down_float);
                    routine.setOpenClosed(0);
                    //Log.i("adapter routine is", "id is "+String.valueOf(routine.getId())+" opencl is"+routine.getOpenClosed());
                    routineListDBHandler.updateOpenClosed(new RoutineModel(routine.getId(), 0));
                    //  isShowing=false;
                } else {
                    holder.habitRecyclerView.setVisibility(View.VISIBLE);
                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_up_float);
                    routine.setOpenClosed(1);
                    routineListDBHandler.updateOpenClosed(new RoutineModel(routine.getId(), 1));
                    //Log.i("adapter routine is", "id is "+String.valueOf(routine.getId()));
                    //  isShowing=true;
                }
            });


            int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(context, 100); //120 //140

            GridLayoutManager layoutManager = new GridLayoutManager(holder.habitRecyclerView.getContext(), mNoOfColumns); //4
            //    LinearLayoutManager layoutManager = new LinearLayoutManager(holder.habitRecyclerView.getContext(), RecyclerView.VERTICAL, false);

            layoutManager.setInitialPrefetchItemCount(routine.getHabitArrayList().size());
            HabitAdapter habitAdapter = new HabitAdapter(routine.getHabitArrayList(), dailyHabitsDBArray, context.getApplicationContext());
            holder.habitRecyclerView.setLayoutManager(layoutManager);
            holder.habitRecyclerView.setAdapter(habitAdapter);

            holder.moreBtn.setOnClickListener(view -> {
                RoutineUtils.routineSel = routine.getRoutine();
                RoutineUtils.routineHabitList = routine.getHabitArrayList();
                RoutineUtils.routineSelStartHour = routine.getStartHour();
                RoutineUtils.routineSelStartMinute = routine.getStartMinute();
                RoutineUtils.routineSelId = routine.getId();

                ArrayList<String> splitDaysString = new ArrayList<>(Arrays.asList(routine.getDaysOfWeek().split(",")));
                //Log.i("split Days",splitDaysString.toString());
                RoutineUtils.daysOfWeekSelected = splitDaysString;
                //   for(int i = 0; i < RoutineUtils.daysOfWeekSelected.size(); i++){
                //    Log.i("routine adapter, day of week","i: "+i+","+RoutineUtils.daysOfWeekSelected.get(i));
                // }
                context.startActivity(new Intent(context, AddHabitsActivity.class));
            });
        }
    }

    public int getItemCount(){
        return routineList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView routineText;
        private final RecyclerView habitRecyclerView;
        public ImageButton hideShowBtn;
        private final TextView startTimeTV;
        private final ImageButton moreBtn;
        private final TextView routineTally;
        private final CardView routineCard;

        public ViewHolder(View view){
            super(view);
            startTimeTV = view.findViewById(R.id.startTimeTV);
            routineText = view.findViewById(R.id.routineTV);
            habitRecyclerView = view.findViewById(R.id.habitRecyclerView);
            hideShowBtn = view.findViewById(R.id.hideShowBtn);
            moreBtn = view.findViewById(R.id.moreToDo);
            routineTally = view.findViewById(R.id.routineTallyTV);
            routineCard = view.findViewById(R.id.routineCard);
        }
    }

}
