package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.goodthingscheduler.XPCount.XPDayDBHandler;
import com.example.goodthingscheduler.toDoAdd.ItemClickListenerInt;
import com.example.goodthingscheduler.Categories.CategoriesUtil;

public class WardrobeActivity extends AppCompatActivity {

    ImageView glassesView;
    ImageView bowView;
    ItemClickListenerInt itemClickListener;
    XPDayDBHandler xpDayDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrobe);

        xpDayDBHandler = new XPDayDBHandler(this);
        Integer totalXp = xpDayDBHandler.TotalXP();

        glassesView = findViewById(R.id.glassesView);
        bowView = findViewById(R.id.bowView);
        ImageView beltView = findViewById(R.id.beltView);

        glassesView.setImageResource(CategoriesUtil.glassesSelected);
        bowView.setImageResource(CategoriesUtil.bowSelected);
        beltView.setImageResource(CategoriesUtil.beltSelected);

        ImageView black = findViewById(R.id.glassesblack);
        ImageView red = findViewById(R.id.glassesred);
        ImageView pink = findViewById(R.id.glassespink);

       // black.setOnClickListener(view -> glassesView.setImageResource(R.drawable.glasses160));
        //red.setOnClickListener(view -> glassesView.setImageResource(R.drawable.glassesred160));
        //pink.setOnClickListener(view -> glassesView.setImageResource(R.drawable.glassespink160));

      //  if(totalXp > 5 ){
            black.setOnClickListener(view -> {
                glassesView.setImageResource(R.drawable.glasses160);
                CategoriesUtil.glassesSelected = R.drawable.glasses160;
            });
     //   }

    //    if(totalXp > 15){
            red.setOnClickListener(view -> {
                glassesView.setImageResource(R.drawable.glassesred160);
                CategoriesUtil.glassesSelected = R.drawable.glassesred160;
            });
      //  }


        pink.setOnClickListener(view -> {
            glassesView.setImageResource(R.drawable.glassespink160);
            CategoriesUtil.glassesSelected = R.drawable.glassespink160;
        });

        ImageView blackbow = findViewById(R.id.bowblack);
        ImageView blackwhitebow = findViewById(R.id.bowblackwhite);
        ImageView pinkbluebow = findViewById(R.id.bowpinkblue);
        ImageView pinkgreenbow = findViewById(R.id.bowpinkgreen);


       /* blackbow.setOnClickListener(view -> bowView.setImageResource(R.drawable.bowblack));
        blackwhitebow.setOnClickListener(view -> bowView.setImageResource(R.drawable.bowblackwhite));
        pinkbluebow.setOnClickListener(view -> bowView.setImageResource(R.drawable.bowpinkblue));
        pinkgreenbow.setOnClickListener(view -> bowView.setImageResource(R.drawable.bowpinkgreen));
*/
        blackbow.setOnClickListener(view -> {
            bowView.setImageResource(R.drawable.bowblack);
            CategoriesUtil.bowSelected = R.drawable.bowblack;
        });

        blackwhitebow.setOnClickListener(view -> {
            bowView.setImageResource(R.drawable.bowblackwhite);
            CategoriesUtil.bowSelected = R.drawable.bowblackwhite;
        });

        pinkbluebow.setOnClickListener(view -> {
            bowView.setImageResource(R.drawable.bowpinkblue);
            CategoriesUtil.bowSelected = R.drawable.bowpinkblue;
        });

        pinkgreenbow.setOnClickListener(view -> {
            bowView.setImageResource(R.drawable.bowpinkgreen);
            CategoriesUtil.bowSelected = R.drawable.bowpinkgreen;
        });

                /* RecyclerView glassesRecyclerView = findViewById(R.id.glassesRV);
        ArrayList<GoodCategoryModel> glassesList = new ArrayList<>();

        glassesList.add(new GoodCategoryModel(0, "round black glasses", 0, R.drawable.glasses160));
        glassesList.add(new GoodCategoryModel(0, "round pink glasses", 0, R.drawable.glassespink160));
        glassesList.add(new GoodCategoryModel(0, "round red glasses", 0, R.drawable.glassesred160));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        glassesRecyclerView.setLayoutManager(layoutManager);
        WardrobeAdapter wardrobeAdapter = new WardrobeAdapter(glassesList, itemClickListener);
        glassesRecyclerView.setAdapter(wardrobeAdapter);  */


        //   if(!CategoriesUtil.glassesSelected==null){
        //     glassesView.setImageResource(CategoriesUtil.glassesSelected);
        //}
        // Initialize listener
        //  itemClickListener = i -> {
        // Notify adapter
        //wardrobeAdapter.post(() -> wardrobeAdapter.notifyDataSetChanged());
        //    CategoriesUtil.glassesSelected = i;
        //  glassesView.setImageResource(i);
//        };
    }


}