package com.example.goodthingscheduler.toDoThings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.toDoAdd.ItemClickListener;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.example.goodthingscheduler.toDoCategories.GoodCategoryModel;

import java.util.ArrayList;

public class CategorySelectorAdapter extends RecyclerView.Adapter<CategorySelectorAdapter.ViewHolder> {

  //  Activity mActivity;

    final private ArrayList<GoodCategoryModel> categoryList;
    final private ItemClickListener itemClickListener;
    final private Context context;
    public Boolean isCatSelected;
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
        }else{
            selectedPosition = -1;
        }

        holder.categoryRadio.setChecked(position == selectedPosition);

        holder.categoryRadio.setOnCheckedChangeListener(
                (compoundButton, b) -> {
                    // check condition
                    if (b) {
                        // When checked// update selected position
                        selectedPosition = holder.getAdapterPosition();
                        // Call listener
                        itemClickListener.onClick(category.getCategoryName());
                        CategoriesUtil.categoryImgId = category.getImgId();
                        //holder.cardView.setCardBackgroundColor(R.color.black);
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final private RadioButton categoryRadio;
        final private CardView cardView;

        public ViewHolder(View view){
            super(view);
            categoryRadio = view.findViewById(R.id.categoryRadioButton);
            cardView = view.findViewById(R.id.categorySelCardView);

        }
    }
}
