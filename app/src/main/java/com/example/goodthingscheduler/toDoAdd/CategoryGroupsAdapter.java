package com.example.goodthingscheduler.toDoAdd;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;

import java.util.ArrayList;

public class CategoryGroupsAdapter extends RecyclerView.Adapter<CategoryGroupsAdapter.ViewHolder> {

    private final ArrayList<CategoryGroupsModel> categoryGroupsModels;
    final private Context context;
 //   ItemClickListener itemClickListener;
  //  private Activity mActivity;

    public CategoryGroupsAdapter(ArrayList<CategoryGroupsModel> categoryGroupsModels, Context context){ //}, ItemClickListener itemClickListener, Activity mActivity){
        this.categoryGroupsModels = categoryGroupsModels;
        this.context = context;
      //  this.itemClickListener = itemClickListener;
       // this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.good_things_states_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CategoryGroupsModel categoryGroupsModel = categoryGroupsModels.get(position);
        holder.goodGroupTV.setText(categoryGroupsModel.getGroup());

     //   LinearLayoutManager layoutManager = new LinearLayoutManager(holder.goodThingsRV.getContext());
       // layoutManager.setInitialPrefetchItemCount(goodGroupsModel.getThingsInGroupList().size());

        GridLayoutManager layoutManager = new GridLayoutManager(holder.goodThingsRV.getContext(),2);
        layoutManager.setInitialPrefetchItemCount(categoryGroupsModel.getThingsInGroupList().size());

       // GoodThingAdapter goodThingAdapter = new GoodThingAdapter(goodGroupsModel.getThingsInGroupList(), context.getApplicationContext());
        AddCategoryAdapter addCategoryAdapter = new AddCategoryAdapter(categoryGroupsModel.getThingsInGroupList(),context.getApplicationContext()); //, itemClickListener);
        holder.goodThingsRV.setLayoutManager(layoutManager);
        holder.goodThingsRV.setAdapter(addCategoryAdapter);
      //  holder.goodThingsRV.setAdapter(goodThingAdapter);
      /*  holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 confirmationDialog();
            }
        });*/
    }

   /* public void confirmationDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View subView = layoutInflater.inflate(R.layout.confirmation_dialogue_layout, null);

        final TextView confirmationTV = subView.findViewById(R.id.confirmationTextView);
        GoodCategoriesDB goodCategoriesDB = new GoodCategoriesDB(context);

        String confirmationString = "Would you like to add "+ Categories.categorySelected+"?";
        confirmationTV.setText(confirmationString);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // builder.setTitle("Add new Task");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ArrayList<GoodCategoryModel> categoryList = goodCategoriesDB.listAllGoodCatsDB();
                //   for(int j=0; j< categoryList.size();j++) {
                if (categoryList.contains(Categories.categorySelected)) {
                    Toast.makeText(context, Categories.categorySelected + " is already there", Toast.LENGTH_SHORT).show();
                } else {
                    goodCategoriesDB.addGoodCategory(new GoodCategoryModel(0, Categories.categorySelected, Categories.categoryImgId, Categories.categoryLogoId));
                    Toast.makeText(context, Categories.categorySelected + " added", Toast.LENGTH_SHORT).show();
                    ((Activity)context).finish();
                    Intent intent = new Intent(context, AddGoodThingActivity.class);
                    context.startActivity(intent);
                }
                //  }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }*/


    @Override
    public int getItemCount() {
        return categoryGroupsModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView goodGroupTV;
        private final RecyclerView goodThingsRV;
        private final CardView card;

        public ViewHolder(View view){
            super(view);
            goodGroupTV = view.findViewById(R.id.stateTextView);
            goodThingsRV = view.findViewById(R.id.goodThingsRV);
            card = view.findViewById(R.id.statesCard);
        }
    }
}
