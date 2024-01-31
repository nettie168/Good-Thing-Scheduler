package com.example.goodthingscheduler.scheduleHabits;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.scheduleAddRoutineHabits.AddHabitsActivity;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.example.goodthingscheduler.toDoThings.ToDoThingModel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {

    private ArrayList<RoutineModel> routineList;
    private final Context context;
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
        RoutineListDBHandler routineListDBHandler = new RoutineListDBHandler(context);
        DailyHabitsDBHandler dailyHabitsDBHandler = new DailyHabitsDBHandler(context);

        if(routine.getRoutine().equals("No Routines")){
            holder.routineText.setText(routine.getRoutine());
            holder.startTimeTV.setVisibility(View.GONE);
            holder.moreBtn.setVisibility(View.GONE);
            holder.routineTally.setVisibility(View.INVISIBLE);
            holder.hideShowBtn.setVisibility(View.INVISIBLE);
            holder.habitsRVStub.setVisibility(View.GONE);
           // holder.habitRecyclerView.setVisibility(View.GONE);
        } else {

            holder.startTimeTV.setVisibility(View.VISIBLE);
            holder.moreBtn.setVisibility(View.VISIBLE);
            holder.routineTally.setVisibility(View.VISIBLE);
            holder.hideShowBtn.setVisibility(View.VISIBLE);

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
            holder.hideShowBtn.setImageResource(android.R.drawable.arrow_down_float);

          /*  if (routine.getOpenClosed() == 1) {
                //holder.habitRecyclerView.setVisibility(View.VISIBLE);
                holder.habitsRVStub.inflate();
            } else {
                holder.habitsRVStub.setVisibility(View.GONE);
                //holder.habitRecyclerView.setVisibility(View.GONE);
            }

            holder.routineText.setOnClickListener(view -> {
                if (routine.getOpenClosed() == 1) {
                    //holder.habitRecyclerView.setVisibility(View.GONE);
                    holder.habitsRVStub.setVisibility(View.GONE);
                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_down_float);
                    routine.setOpenClosed(0);
                    routineListDBHandler.updateOpenClosed(new RoutineModel(routine.getId(), 0));
                } else {
                    //holder.habitRecyclerView.setVisibility(View.VISIBLE);
                    holder.habitsRVStub.setVisibility(View.VISIBLE);
                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_up_float);
                    routine.setOpenClosed(1);
                    routineListDBHandler.updateOpenClosed(new RoutineModel(routine.getId(), 1));
                }
            });*/

            holder.hideShowBtn.setOnClickListener(view -> {
               /* if (routine.getOpenClosed() == 1) {
                    //holder.habitRecyclerView.setVisibility(View.GONE);

                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_down_float);
                    routine.setOpenClosed(0);
                    routineListDBHandler.updateOpenClosed(new RoutineModel(routine.getId(), 0));
                    //  isShowing=false;
                } else {
                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_up_float);
                    routine.setOpenClosed(1);
                    routineListDBHandler.updateOpenClosed(new RoutineModel(routine.getId(), 1));
                    //  isShowing=true;
                }*/

                if (holder.habitsRVStub.getInflatedId() == View.NO_ID) {
                    // If ViewStub is not inflated, inflate it
                    holder.habitsRVStub.inflate();
                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_up_float);
                    // You can customize the inflatedView here if needed
                } else {
                    // If ViewStub is already inflated, toggle its visibility
                    //View inflatedView = view.findViewById(holder.habitsRVStub.getInflatedId());
                    holder.hideShowBtn.setImageResource(android.R.drawable.arrow_up_float);
                    if (holder.inflatedLayout != null) {
                        int visibility = holder.inflatedLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                        holder.inflatedLayout.setVisibility(visibility);
                        holder.hideShowBtn.setImageResource(android.R.drawable.arrow_down_float);
                    }
                }

            });


            int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(context, 100); //120 //140

            GridLayoutManager layoutManager = new GridLayoutManager(holder.habitRecyclerView.getContext(), mNoOfColumns); //4

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

                RoutineUtils.daysOfWeekSelected = new ArrayList<>(Arrays.asList(routine.getDaysOfWeek().split(",")));

                context.startActivity(new Intent(context, AddHabitsActivity.class));
            });
        }
    }

    public int getItemCount(){
        return routineList.size();
    }

    public void setData(ArrayList<RoutineModel> newData) {
        routineList = newData;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView routineText;
        private final RecyclerView habitRecyclerView;
        private final ViewStub habitsRVStub;
        private final View inflatedLayout;
        public ImageButton hideShowBtn;
        private final TextView startTimeTV;
        private final ImageButton moreBtn;
        private final TextView routineTally;

        public ViewHolder(View view){
            super(view);
            startTimeTV = view.findViewById(R.id.startTimeTV);
            routineText = view.findViewById(R.id.routineTV);
            hideShowBtn = view.findViewById(R.id.hideShowBtn);
            moreBtn = view.findViewById(R.id.moreToDo);
            routineTally = view.findViewById(R.id.routineTallyTV);

            habitsRVStub = view.findViewById(R.id.habitsRVStub);
            inflatedLayout = habitsRVStub.inflate();
            habitRecyclerView = inflatedLayout.findViewById(R.id.habitRecyclerView);
        }
    }

}
