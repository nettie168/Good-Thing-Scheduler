package com.example.goodthingscheduler.toDoCategories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.ToDoListActivity;
import com.example.goodthingscheduler.toDoAdd.AddNewCategoryActivity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    final private ArrayList<GoodCategoryModel> categoryList;
    final private Context context;

    public CategoryAdapter(ArrayList<GoodCategoryModel> categoryList, Context context){
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_category_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        GoodCategoryModel category = categoryList.get(position);

        holder.categoryText.setText(category.getCategoryName());
        holder.categoryImage.setImageResource(category.getImgId());

        holder.categoryCard.setOnClickListener(view -> {
            if(!category.getCategoryName().equals("New Category")) {
                CategoriesUtil.categorySelected = category.getCategoryName();
                CategoriesUtil.categoryImgId = category.getImgId();
                CategoriesUtil.categoryLogoId = category.getLogoId();

                context.startActivity(new Intent(context, ToDoListActivity.class));
            }else{
                context.startActivity(new Intent(context, AddNewCategoryActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final private TextView categoryText;
        final private ImageView categoryImage;
        final private CardView categoryCard;

        public ViewHolder(View view){
            super(view);
            categoryText = view.findViewById(R.id.categoryTextViewRV);
            categoryImage = view.findViewById(R.id.categoryImageViewRV);
            categoryCard = view.findViewById(R.id.goodCategoryCard);

        }
    }
}
