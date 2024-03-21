package com.example.goodthingscheduler.toDoAdd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.Categories.CategoriesUtil;
import com.example.goodthingscheduler.Categories.GoodCategoryModel;

import java.util.ArrayList;

public class CategoryTagsAdapter extends RecyclerView.Adapter<CategoryTagsAdapter.ViewHolder> {

  //  Activity mActivity;

    final private ArrayList<GoodCategoryModel> categoryList;
    final private  ItemClickListener itemClickListener;
    public Boolean isCatSelected;
    int selectedPosition = -1;

    public CategoryTagsAdapter(ArrayList<GoodCategoryModel> categoryList, ItemClickListener itemClickListener, Context context){ //, Activity mActivity){
        this.categoryList = categoryList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_tag_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        GoodCategoryModel category = categoryList.get(position);

        holder.categoryImage.setImageResource(category.getLogoId());
        holder.categoryImage.setContentDescription(category.getCategoryName());
        holder.categoryText.setText(category.getCategoryName());

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
                        GoodCategoryModel goodCategoryModel = new GoodCategoryModel(0, holder.categoryText.getText().toString(), category.getImgId(), category.getLogoId());
                        //itemClickListener.onClick(holder.categoryText.getText().toString());
                        itemClickListener.onClick(goodCategoryModel);

                    }
                });


     /*   holder.categoryCard.setOnClickListener(view -> {
            if(!category.getCategoryName().equals("New Category")) {
              //  if(isCatSelected) {
                Log.i("CatSelRVBeforeIf",CategoriesUtil.categorySelected);
           //     Intent intent = new Intent(context, AddGoodThingActivity.class);
                if(category.getCategoryName().equals(CategoriesUtil.categorySelected)){
                    holder.categoryCard.setCardBackgroundColor(Color.WHITE); //Color.WHITE
                 //   holder.categoryText.setVisibility(View.GONE);
                    //Categories.categorySelected = "Good Things";
                    CategoriesUtil.categoryImgId = R.drawable.bookswide160dpi;
                    CategoriesUtil.categoryLogoId = R.drawable.ic_baseline_miscellaneous_services_24;
                    isCatSelected = false;
                    selectedPosition = holder.getAdapterPosition();
                    itemClickListener.onClick(holder.categoryText.getText().toString());

                    Toast.makeText(context, CategoriesUtil.categorySelected + " unselected", Toast.LENGTH_SHORT).show();
                    Log.i("CatSelRVAfterIf_1",CategoriesUtil.categorySelected);

                } else {
                    holder.categoryCard.setCardBackgroundColor(Color.MAGENTA);
                 //   holder.categoryText.setVisibility(View.VISIBLE);
                    CategoriesUtil.categorySelected = category.getCategoryName();
                    CategoriesUtil.categoryImgId = category.getImgId();
                    CategoriesUtil.categoryLogoId = category.getLogoId();
                    isCatSelected = true;
                    selectedPosition = holder.getAdapterPosition();
                    itemClickListener.onClick(holder.categoryText.getText().toString());

                    Toast.makeText(context, CategoriesUtil.categorySelected + " selected", Toast.LENGTH_SHORT).show();
                    Log.i("CatSelRV_AfterElse",CategoriesUtil.categorySelected);
                }
        //        mActivity.recreate();
        //        mActivity.overridePendingTransition(0,0);
            }else{
                context.startActivity(new Intent(context, AddNewCategoryActivity.class));
            }
          //  Log.i("cat_img", String.valueOf(category.getImgId()));
        });*/
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
        final private TextView categoryText;
        final private ImageView categoryImage;
        //final private CardView categoryCard;
        final private RadioButton categoryRadio;

        public ViewHolder(View view){
            super(view);
            categoryText = view.findViewById(R.id.categoryTagTV);
            categoryImage = view.findViewById(R.id.categoryTagImageView);
            categoryRadio = view.findViewById(R.id.categoryRadioButton);
            //categoryCard = view.findViewById(R.id.categoryTagCard);

        }
    }
}
