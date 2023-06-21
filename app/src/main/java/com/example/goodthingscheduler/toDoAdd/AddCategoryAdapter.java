package com.example.goodthingscheduler.toDoAdd;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AddCategoryAdapter extends RecyclerView.Adapter<AddCategoryAdapter.ViewHolder> {

    private final ArrayList<GoodCategoryModel> goodThingsList;
    final private Context context;
    private GoodCategoriesDB goodCategoriesDB;
 //   ItemClickListener itemClickListener;

    public AddCategoryAdapter(ArrayList<GoodCategoryModel> goodThingsList, Context context){ //, ItemClickListener itemClickListener){
        this.goodThingsList = goodThingsList;
        this.context = context;
     //   this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_thing_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        goodCategoriesDB = new GoodCategoriesDB(context);

        GoodCategoryModel goodCategoryModel = goodThingsList.get(position);

        holder.goodThingTV.setText(goodCategoryModel.getCategoryName());
        holder.goodThingLogo.setImageResource(goodCategoryModel.getLogoId());

        ArrayList<String> catList = goodCategoriesDB.listCatsDB();

        holder.checkBox.setChecked(catList.contains(goodCategoryModel.getCategoryName()));

        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            CategoriesUtil.categorySelected = goodCategoryModel.getCategoryName();
            CategoriesUtil.categoryImgId = goodCategoryModel.getImgId();
            CategoriesUtil.categoryLogoId = goodCategoryModel.getLogoId();
            //itemClickListener.onClick(goodCategoryModel.getCategoryName());
            CategoriesUtil.categoryList.add(new GoodCategoryModel(goodCategoryModel.getId(),goodCategoryModel.getCategoryName(),goodCategoryModel.getImgId(),goodCategoryModel.getLogoId()));
        });

        holder.goodThingTV.setOnClickListener(view -> {
            CategoriesUtil.categorySelected = goodCategoryModel.getCategoryName();
            CategoriesUtil.categoryImgId = goodCategoryModel.getImgId();
            CategoriesUtil.categoryLogoId = goodCategoryModel.getLogoId();
            holder.card.setCardBackgroundColor(Color.MAGENTA);
           // ArrayList<GoodCategoryModel> categoryModels = goodCategoriesDB.listAllGoodCatsDB();
       //     for(int k=0; k<categoryModels.size(); k++){
                if(!goodCategoriesDB.listCatsDB().contains(CategoriesUtil.categorySelected)){
           //     if(categoryModels.get(k).getCategoryName().equals())
                    Toast.makeText(context, "Cat:"+CategoriesUtil.categorySelected, Toast.LENGTH_SHORT).show();
                    goodCategoriesDB.addGoodCategory(new GoodCategoryModel(0, CategoriesUtil.categorySelected, CategoriesUtil.categoryImgId, CategoriesUtil.categoryLogoId));

                  //  Intent intent = new Intent(context, AddGoodThingActivity.class);
                //    context.startActivity(intent);
                }
            RoutineUtils.routineSel=goodCategoryModel.getCategoryName();

            Intent habitDetailsActivity = new Intent(context.getApplicationContext(), ToDoListActivity.class);
            habitDetailsActivity.addFlags(FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(habitDetailsActivity);

          //  Intent intent = new Intent(context, AddHabitsActivity.class);
            //    context.startActivity(intent);
       //     }

        });
    }


    @Override
    public int getItemCount() {
        return goodThingsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final private TextView goodThingTV;
        final private ImageView goodThingLogo;
        final private CardView card;
        final private CheckBox checkBox;

        public ViewHolder(View view){
            super(view);
            goodThingTV = view.findViewById(R.id.goodThingTextView);
            goodThingLogo = view.findViewById(R.id.goodThingLogoImageView);
            card = view.findViewById(R.id.goodThingCard);
            checkBox = view.findViewById(R.id.toDoCheckBox);
        }
    }
}
