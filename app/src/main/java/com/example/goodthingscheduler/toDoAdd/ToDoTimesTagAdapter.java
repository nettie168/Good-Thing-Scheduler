package com.example.goodthingscheduler.toDoAdd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;

import java.util.ArrayList;

public class ToDoTimesTagAdapter extends RecyclerView.Adapter<ToDoTimesTagAdapter.ViewHolder>{
    // Initialize variable
    ArrayList<String> arrayList;
    ItemClickListener itemClickListener;
    // int selectedPosition;
    int selectedPosition = -1;

    // Create constructor
    public ToDoTimesTagAdapter(ArrayList<String> arrayList, ItemClickListener itemClickListener) {
        this.arrayList = arrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_time_radio_item, parent, false);
        // Pass holder view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Set text on radio button
        holder.radioButton.setText(arrayList.get(position));

        if(arrayList.get(position).contains(CategoriesUtil.stateSelected)){
            selectedPosition = holder.getAdapterPosition();
        }else{
            selectedPosition = -1;
        }

        // Checked selected radio button
        holder.radioButton.setChecked(position == selectedPosition);

        // set listener on radio button
        holder.radioButton.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        // check condition
                        if (b) {
                            // When checked// update selected position
                            selectedPosition = holder.getAdapterPosition();
                            // Call listener
                            itemClickListener.onClick(holder.radioButton.getText().toString());
                        }
                    }
                });
    }

    @Override public long getItemId(int position) {
        // pass position
        return position;
    }
    @Override public int getItemViewType(int position) {
        // pass position
        return position;
    }

    @Override public int getItemCount() {
        // pass total list size
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Initialize variable
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assign variable
            radioButton = itemView.findViewById(R.id.todo_time_radio_button);
        }
    }


}
