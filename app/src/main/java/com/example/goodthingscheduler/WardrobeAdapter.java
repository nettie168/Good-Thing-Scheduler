package com.example.goodthingscheduler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.toDoAdd.ItemClickListener;
import com.example.goodthingscheduler.toDoAdd.ItemClickListenerInt;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.example.goodthingscheduler.toDoCategories.GoodCategoryModel;

import java.util.ArrayList;

public class WardrobeAdapter extends RecyclerView.Adapter<WardrobeAdapter.ViewHolder> {

    final private ArrayList<GoodCategoryModel> wardrobeList;
    final private ItemClickListenerInt itemClickListener;
    int selectedPosition = -1;


    public WardrobeAdapter(ArrayList<GoodCategoryModel> wardrobeList, ItemClickListenerInt itemClickListener){
        this.wardrobeList = wardrobeList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wardrobe_item_selector,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        GoodCategoryModel wardrobe_item = wardrobeList.get(position);

        holder.wardrobeImage.setImageResource(wardrobe_item.getLogoId());
        holder.wardrobeImage.setContentDescription(wardrobe_item.getCategoryName());

     //   if(wardrobe_item.getCategoryName().contains(CategoriesUtil.glassesSelected)){
       //     selectedPosition = holder.getAdapterPosition();
        //}else{
            selectedPosition = -1;
        //}

        holder.wardrobeImage.setOnClickListener(view -> {
            Log.i("Wardrobe Adapter", String.valueOf(wardrobe_item.getLogoId()));
            //CategoriesUtil.glassesSelected = wardrobe_item.getCategoryName();
            itemClickListener.onClick(wardrobe_item.getLogoId());
        });
    }

    @Override
    public int getItemCount() {
        return wardrobeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final private ImageView wardrobeImage;

        public ViewHolder(View view){
            super(view);
            wardrobeImage = view.findViewById(R.id.wardrobeItemImgView);
        }
    }
}
