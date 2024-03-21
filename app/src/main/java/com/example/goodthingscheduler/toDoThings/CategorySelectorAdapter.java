package com.example.goodthingscheduler.toDoThings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.toDoAdd.AddNewCategoryActivity;
import com.example.goodthingscheduler.toDoAdd.ItemClickListener;
import com.example.goodthingscheduler.Categories.CategoriesUtil;
import com.example.goodthingscheduler.Categories.GoodCategoryModel;
import com.google.android.material.divider.MaterialDivider;

import java.util.ArrayList;

public class CategorySelectorAdapter extends RecyclerView.Adapter<CategorySelectorAdapter.ViewHolder> {

  //  Activity mActivity;

    private ArrayList<GoodCategoryModel> categoryList;
    final private ItemClickListener itemClickListener;
    final private Context context;
    int selectedPosition = -1;

    public CategorySelectorAdapter(ArrayList<GoodCategoryModel> categoryList, ItemClickListener itemClickListener, Context context){ //, Activity mActivity){
        this.categoryList = categoryList;
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_selector,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        GoodCategoryModel category = categoryList.get(position);


        holder.categoryRadio.setBackground(ContextCompat.getDrawable(context, category.getLogoId()));
        holder.categoryRadio.setContentDescription(category.getCategoryName());

        if(category.getCategoryName().contains(CategoriesUtil.categorySelected)){
            selectedPosition = holder.getAdapterPosition();
            holder.cardView.setCardBackgroundColor(Color.MAGENTA);
            holder.catSelLine.setVisibility(View.VISIBLE);
            //holder.categoryRadio.setBackgroundColor(Color.BLACK); //tint
        }else{
            selectedPosition = -1;
            holder.cardView.setCardBackgroundColor(Color.parseColor("#085c0f"));
            holder.catSelLine.setVisibility(View.INVISIBLE);
            //holder.categoryRadio.setBackgroundColor(Color.WHITE); //tint
        }

        holder.categoryRadio.setChecked(position == selectedPosition);

        holder.categoryRadio.setOnCheckedChangeListener(
                (compoundButton, b) -> {
                    // check condition
                    if (b) {
                        if(category.getCategoryName().equals("add category")){
                            Intent intent = new Intent(context, AddNewCategoryActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }else{
                            // When checked// update selected position
                            selectedPosition = holder.getAdapterPosition();
                            // Call listener
                            //itemClickListener.onClick(category.getCategoryName());
                            GoodCategoryModel goodCategoryModel = new GoodCategoryModel(0, category.getCategoryName(), category.getImgId(), category.getLogoId());
                            itemClickListener.onClick(goodCategoryModel);

                            CategoriesUtil.categoryImgId = category.getImgId();
                            CategoriesUtil.categoryLogoId = category.getLogoId();
                            //holder.categoryRadio.setBackgroundColor(Color.BLUE);
                            //holder.cardView.setCardBackgroundColor(Color.MAGENTA);
                            //holder.cardView.setCardBackgroundColor(Color.MAGENTA);
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



    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public void setData(ArrayList<GoodCategoryModel> newData) {
        categoryList = newData;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final private RadioButton categoryRadio;
        final private CardView cardView;
        final private MaterialDivider catSelLine;

        public ViewHolder(View view){
            super(view);
            categoryRadio = view.findViewById(R.id.categoryRadioButton);
            cardView = view.findViewById(R.id.categorySelCardView);
            catSelLine = view.findViewById(R.id.catSelLine);

        }
    }
}
