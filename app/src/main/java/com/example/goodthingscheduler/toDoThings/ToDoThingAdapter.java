package com.example.goodthingscheduler.toDoThings;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
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
import com.example.goodthingscheduler.scheduleHabits.RoutineUtils;
import com.example.goodthingscheduler.scheduleHabits.TaskTickedActivity;
import com.example.goodthingscheduler.toDoAdd.ToDoAddThingActivity;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;

import java.time.LocalDate;
import java.util.ArrayList;

public class ToDoThingAdapter extends RecyclerView.Adapter<ToDoThingAdapter.ViewHolder> {

    private ArrayList<ToDoThingModel> goodThingsList;
    private final Context context;
    private ToDoThingsDB toDoThingsDB;
    private XPDayDBHandler xpDayDBHandler;

    public ToDoThingAdapter(ArrayList<ToDoThingModel> goodThingsList, Context context){
        this.goodThingsList = goodThingsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_thing_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        xpDayDBHandler = new XPDayDBHandler(context);

        ToDoThingModel goodThing = goodThingsList.get(position);
        toDoThingsDB = new ToDoThingsDB(context.getApplicationContext());

      //  holder.goodThingCard.setCardBackgroundColor(Color.WHITE);
      //  holder.goodThingTV.setTextColor(Color.BLUE);

        holder.goodThingTV.setText(goodThing.getGoodThing());
        holder.goodThingLogo.setImageResource(goodThing.getLogoId());


        if(goodThing.getState().equals("Happy it exists")){
            //holder.goodThingTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.checkBox.setVisibility(View.GONE);
        }

        holder.checkBox.setChecked(goodThing.getState().equals("Done"));

        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(goodThing.getState().equals("Done")){
                goodThing.setState("To Do");
                //Log.i("good thing b4 is ","good thing: "+goodThing.getGoodThing()+", id: "+goodThing.getId()+", cat: "+goodThing.getCategory()+", state: "+goodThing.getState()+", added: "+goodThing.getDateAdded()+", start: "+goodThing.getDateToStart());
                toDoThingsDB.updateGoodThing(new ToDoThingModel(goodThing.getId(), goodThing.getCategory(), goodThing.getGoodThing(), goodThing.getInspiredBy(), CategoriesUtil.categoryLogoId, "#000000","To Do", goodThing.getDateAdded(), goodThing.getDateToStart(), goodThing.getDateToEnd(), ""));
                //Log.i("good thing after is ","good thing: "+goodThing.getGoodThing()+", id: "+goodThing.getId()+", cat: "+goodThing.getCategory()+", state: "+goodThing.getState()+", added: "+goodThing.getDateAdded()+", start: "+goodThing.getDateToStart());
                XPUtils.dayXP = new XPCountModel(CalendarUtils.selectedDate.toString(),XPUtils.dayXP.getXp()-5);
                xpDayDBHandler.updateDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(), XPUtils.dayXP.getXp()));
          //      Log.i("to do adapter","date is "+CalendarUtils.selectedDate.toString()+" xp is"+XPUtils.dayXP.getXp()+"-5");

            }else{
                goodThing.setState("Done");
                //Log.i("good thing b4 is ","good thing: "+goodThing.getGoodThing()+", id: "+goodThing.getId()+", cat: "+goodThing.getCategory()+", state: "+goodThing.getState()+", added: "+goodThing.getDateAdded()+", start: "+goodThing.getDateToStart());
                toDoThingsDB.updateGoodThing(new ToDoThingModel(goodThing.getId(), goodThing.getCategory(), goodThing.getGoodThing(), goodThing.getInspiredBy(), CategoriesUtil.categoryLogoId, "#000000","Done", goodThing.getDateAdded(), goodThing.getDateToStart(), goodThing.getDateToEnd(), LocalDate.now().toString()));
                //Log.i("good thing after is ","good thing: "+goodThing.getGoodThing()+", id: "+goodThing.getId()+", cat: "+goodThing.getCategory()+", state: "+goodThing.getState()+", added: "+goodThing.getDateAdded()+", start: "+goodThing.getDateToStart());

                XPUtils.dayXP = new XPCountModel(CalendarUtils.selectedDate.toString(),XPUtils.dayXP.getXp()+5);
                xpDayDBHandler.updateDayXP(new XPCountModel(CalendarUtils.selectedDate.toString(), XPUtils.dayXP.getXp()));
              //  Log.i("to do adapter","date is "+CalendarUtils.selectedDate.toString()+" xp is"+XPUtils.dayXP.getXp()+"+5");


                RoutineUtils.habitName = goodThing.getGoodThing();
                Intent taskTickActivity = new Intent(context.getApplicationContext(), TaskTickedActivity.class);
                taskTickActivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(taskTickActivity);
            }
        });

        holder.goodThingCard.setOnClickListener(view -> {
            CategoriesUtil.goodThing = goodThing.getGoodThing();
            CategoriesUtil.stateSelected = goodThing.getState();
            CategoriesUtil.goodThingId = goodThing.getId();
            CategoriesUtil.categorySelected = goodThing.getCategory();
            CategoriesUtil.inspiredBy = goodThing.getInspiredBy();
            CalendarUtils.dateToStart = goodThing.getDateToStart();
            CalendarUtils.dateToEnd = goodThing.getDateToEnd();
            RoutineUtils.habitImgId = CategoriesUtil.categoryLogoId;

            //Log.i("Utils","date to start "+CalendarUtils.dateToStart);

            //Toast.makeText(context, "id is"+CategoriesUtil.goodThingId, Toast.LENGTH_SHORT).show();

            //context.startActivity(new Intent(context, ToDoAddThingActivity.class));
            //   Toast.makeText(context, Categories.goodThing, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ToDoAddThingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            //   getActivity().startActivity(new Intent(context, AddGoodThingActivity.class));

            //getActivity().startActivity(new Intent(context, AddGoodThingActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return goodThingsList.size();
    }

    public void setData(ArrayList<ToDoThingModel> newData) {
        goodThingsList = newData;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final private TextView goodThingTV;
        final private ImageView goodThingLogo;
        final private CardView goodThingCard;
        final private CheckBox checkBox;

        public ViewHolder(View view){
            super(view);
            goodThingTV = view.findViewById(R.id.goodThingTextView);
            goodThingLogo = view.findViewById(R.id.goodThingLogoImageView);
            goodThingCard = view.findViewById(R.id.goodThingCard);
            checkBox = view.findViewById(R.id.toDoCheckBox);
        }
    }
}
