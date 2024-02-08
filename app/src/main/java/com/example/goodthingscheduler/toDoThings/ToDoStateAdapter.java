package com.example.goodthingscheduler.toDoThings;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;

import java.util.ArrayList;

public class ToDoStateAdapter extends RecyclerView.Adapter<ToDoStateAdapter.ViewHolder> {

    private ArrayList<ToDoStatesModel> goodThingsStateList;
    final private Context context;

    public ToDoStateAdapter(ArrayList<ToDoStatesModel> goodThingsStateList, Context context){
        this.goodThingsStateList = goodThingsStateList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_things_states_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        ToDoStatesModel goodThingState = goodThingsStateList.get(position);
        //holder.cardView.setCardBackgroundColor(Color.parseColor("#085c0f"));
        //holder.goodThingState.setTextColor(Color.WHITE);
        holder.goodThingState.setText(goodThingState.getState());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.goodThingsRV.getContext());
        linearLayoutManager.setInitialPrefetchItemCount(goodThingState.getThingsInStateList().size());
        ToDoThingAdapter goodThingAdapter = new ToDoThingAdapter(goodThingState.getThingsInStateList(), context.getApplicationContext());
        holder.goodThingsRV.setLayoutManager(linearLayoutManager);
        holder.goodThingsRV.setAdapter(goodThingAdapter);

    }

    @Override
    public int getItemCount() {
        return goodThingsStateList.size();
    }

    public void setData(ArrayList<ToDoStatesModel> newData) {
        goodThingsStateList = newData;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView goodThingState;
        private final RecyclerView goodThingsRV;
        private final CardView cardView;

        public ViewHolder(View view){
            super(view);
            goodThingState = view.findViewById(R.id.stateTextView);
            goodThingsRV = view.findViewById(R.id.goodThingsRV);
            cardView = view.findViewById(R.id.statesCard);
        }
    }
}
