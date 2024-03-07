package com.example.goodthingscheduler.GoodThings;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.Categories.CategoriesUtil;
import com.example.goodthingscheduler.Categories.GoodCategoriesDB;
import com.example.goodthingscheduler.Categories.GoodCategoryModel;
import com.example.goodthingscheduler.toDoAdd.CategoryGroupsAdapter;
import com.example.goodthingscheduler.toDoAdd.CategoryGroupsModel;

import java.util.ArrayList;
import java.util.Objects;

public class AddNewGoodCategoryActivity extends AppCompatActivity {

    //ItemClickListener itemClickListener;
    CategoryGroupsAdapter addCategoryAdapter;
    //ArrayList<GoodCategoryModel> goodCategoryModelArrayList;
    GoodCategoriesDB goodCategoriesDB;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_bar, menu);

        MenuItem item = menu.findItem(R.id.saveActionBarBtn);

        item.setTitle("Confirm");

        item.setOnMenuItemClickListener(menuItem -> {
            confirmCategories();
            return false;
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Add Category");

        goodCategoriesDB = new GoodCategoriesDB(this);

        initNewCatsRV();
    }

    private void initNewCatsRV(){

        RecyclerView newCategoriesRV = findViewById(R.id.newCategoriesRV);
        //ImageButton closeNewCategoriesBtn = findViewById(R.id.closeCategoryBtn);
        /* DofE/Expedition Schools: Fitness/Physical/Sports, Volunteering/Acts of Service/Community, Skills/Personal Talents/Projects, Expedition (Hike, Adventure, Camping), Residential (Travel)*/
//Looking after yourself I: safe place, shelter, food, water, movement, social space,
//Looking after yourself II: resources to live (money), physical hygiene, emotional hygiene, sleep hygiene,
//Looking after yourself III: paying off debt, budgeting, place of creation
// From Finch/counselling: 5 Pillars work and performance, social relationships, health and body, material safety and personal values.

//Houses(Bases)
// Outwards: Travel&go to new places,
        //Fun with people/Out in the world
// Physical: Sports&Exercise,(body&interpersonal)
// Rhythm & Composition: Music&Dance&Theatre,
// Laughter: Comedy
// Sensory: Cooking&Baking&Trying new food,
// Heart: Family&Community, Kindness&Giving back&knowing your area (people)
// Voice: WritingStories&theatre&screenplay (words&people)
// Nature: Gardens&plants, (time in nature)
// Play: Games
// Rest & Recharge
// Knowledge: understanding our world, knowing oneself, and our history&future in it (memory, intrapersonal, interpersonal)
//languages

        //Fun by yourself/In your head
// Physical: yoga&martial arts(mind&body)
// Rhythm: Music&Dance
// Composition: Art&Design&Photography&Film, Drawing&Painting, MakeUp&Hair&Clothes, (spatial)
// Laughter: Comedy
// Sensory: Cooking&Baking&Trying new food,
// Heart: Thankfulness&kindnessTo Animals
// Voice: WritingStories&books
// Nature: Gardens&plants, (time in nature)
// Inwards: Books&libraries, (words)
// Deep Thinking: Puzzles&Games, Tech&Coding (logic)
// Play: Creation & Expression
// Knowledge: understanding our world, knowing oneself, and our history&future in it (memory, intrapersonal, interpersonal)
// Rest & Recharge

        //Set out a limit (to encourage users)
        //To begin only have some open/accessible
        //Others unlock only after adding good things to the category they've got
        //Eg. Limited to 12 Good Categories (set to the average/highest number most people use
        //Then could add monetary/exclusive for over 12
        //Aim to try out all bases (do one thing for a month from each, have a free/or very cheap idea for each), get points & badges, for depth(doing same thing consistently) & some breadth
        ArrayList<GoodCategoryModel> categoryList = new ArrayList<>();
        //Most Popular
        categoryList.add(new GoodCategoryModel(0, "Books", R.drawable.snowy_mountain, R.drawable.ic_baseline_auto_stories_24));
        categoryList.add(new GoodCategoryModel(1, "Films and tv", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_movies_24));
        categoryList.add(new GoodCategoryModel(2, "Gardening and House Plants", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        categoryList.add(new GoodCategoryModel(2, "Photography", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_a_photo_24));
        categoryList.add(new GoodCategoryModel(2, "Tasty food", R.drawable.snowy_mountain, R.drawable.ic_baseline_ramen_dining_24));
        categoryList.add(new GoodCategoryModel(3, "Baking", R.drawable.snowy_mountain, R.drawable.ic_baseline_bakery_dining_24));
        categoryList.add(new GoodCategoryModel(2, "Drawing", R.drawable.snowy_mountain, R.drawable.ic_baseline_draw_24));
        categoryList.add(new GoodCategoryModel(2, "Language learning", R.drawable.snowy_mountain, R.drawable.ic_baseline_language_24));
        categoryList.add(new GoodCategoryModel(2, "Music", R.drawable.snowy_mountain, R.drawable.ic_baseline_music_note_24));
        categoryList.add(new GoodCategoryModel(2, "Visit New Places", R.drawable.snowy_mountain, R.drawable.ic_baseline_attractions_24));
        categoryList.add(new GoodCategoryModel(2, "Painting", R.drawable.snowy_mountain, R.drawable.ic_baseline_brush_24));

        //Games & Puzzles (Brain Training)
        ArrayList<GoodCategoryModel> GamesList = new ArrayList<>();
        GamesList.add(new GoodCategoryModel(2, "Games","Video Games", R.drawable.snowy_mountain, R.drawable.ic_baseline_sports_esports_24));
        GamesList.add(new GoodCategoryModel(2, "Games","Puzzles", R.drawable.snowy_mountain, R.drawable.ic_baseline_extension_24));
        GamesList.add(new GoodCategoryModel(2, "Games","Jigsaw Puzzles", R.drawable.snowy_mountain, R.drawable.ic_baseline_extension_24));

        //Creations (To Do)
        ArrayList<GoodCategoryModel> CreationsList = new ArrayList<>();
        CreationsList.add(new GoodCategoryModel(2, "Web/App development", R.drawable.snowy_mountain, R.drawable.ic_baseline_code_24));
        CreationsList.add(new GoodCategoryModel(2, "D&D", R.drawable.snowy_mountain, R.drawable.baseline_adb_24));
        CreationsList.add(new GoodCategoryModel(2, "Music", R.drawable.snowy_mountain, R.drawable.ic_baseline_music_note_24));
        CreationsList.add(new GoodCategoryModel(2, "Drawings", R.drawable.snowy_mountain, R.drawable.ic_baseline_draw_24));
        CreationsList.add(new GoodCategoryModel(2, "Paintings", R.drawable.snowy_mountain, R.drawable.ic_baseline_brush_24));
        CreationsList.add(new GoodCategoryModel(2, "Photography", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_a_photo_24));
        CreationsList.add(new GoodCategoryModel(2, "Film Making", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_a_photo_24));


        //Music & Acting (To See)
        ArrayList<GoodCategoryModel> ArtsList = new ArrayList<>();
        ArtsList.add(new GoodCategoryModel(2, "Theatre", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
        ArtsList.add(new GoodCategoryModel(2, "Comedy", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));

        //Sports
        ArrayList<GoodCategoryModel> sportsList = new ArrayList<>();
        sportsList.add(new GoodCategoryModel(2, "Sports","Cycling", R.drawable.snowy_mountain, R.drawable.ic_baseline_pedal_bike_24));
        sportsList.add(new GoodCategoryModel(2, "Sports","Swimming", R.drawable.snowy_mountain, R.drawable.ic_baseline_water_24));
        sportsList.add(new GoodCategoryModel(2, "Running", R.drawable.snowy_mountain, R.drawable.ic_baseline_directions_run_24));
        sportsList.add(new GoodCategoryModel(2, "Roller-Skating", R.drawable.snowy_mountain, R.drawable.ic_baseline_ice_skating_24));
        sportsList.add(new GoodCategoryModel(2, "Martial Arts", R.drawable.snowy_mountain, R.drawable.ic_baseline_sports_martial_arts_24));
        sportsList.add(new GoodCategoryModel(2, "Skiing", R.drawable.skiing160, R.drawable.ic_baseline_downhill_skiing_24));
        sportsList.add(new GoodCategoryModel(2, "Ice-Skating", R.drawable.snowy_mountain, R.drawable.ic_baseline_ice_skating_24));
        sportsList.add(new GoodCategoryModel(2, "Dances","Dance", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));


        //Community/Helping Others
        ArrayList<GoodCategoryModel> CommunityList = new ArrayList<>();
        CommunityList.add(new GoodCategoryModel(2, "Time with Partner", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Time with Friends", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Time with Family", R.drawable.snowy_mountain, R.drawable.ic_baseline_diversity_3_24));
        CommunityList.add(new GoodCategoryModel(2, "Time with Loved Ones", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Time with Pets", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Time with House Plants", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Volunteering", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));

        ////Travel
        ArrayList<GoodCategoryModel> TravelList = new ArrayList<>();
        TravelList.add(new GoodCategoryModel(2, "Places to visit", R.drawable.snowy_mountain, R.drawable.ic_baseline_travel_explore_24));
        TravelList.add(new GoodCategoryModel(2, "Places to eat", R.drawable.snowy_mountain, R.drawable.ic_baseline_restaurant_menu_24));

        ArrayList<CategoryGroupsModel> groupsList = new ArrayList<>();
        groupsList.add(new CategoryGroupsModel("Popular",categoryList));
        groupsList.add(new CategoryGroupsModel("Community",CommunityList));
        groupsList.add(new CategoryGroupsModel("Sports",sportsList));
        groupsList.add(new CategoryGroupsModel("Creations",CreationsList));
        groupsList.add(new CategoryGroupsModel("Games",GamesList));
        groupsList.add(new CategoryGroupsModel("Arts",ArtsList));
        groupsList.add(new CategoryGroupsModel("Travel & Adventure",TravelList));

        newCategoriesRV.setLayoutManager(new LinearLayoutManager(this));
        addCategoryAdapter = new CategoryGroupsAdapter(groupsList, this); //, itemClickListener, thisActivity);
        newCategoriesRV.setAdapter(addCategoryAdapter);

    }

    private void confirmCategories(){
        for(int i = 0; i < CategoriesUtil.categoryList.size(); i ++){
            if(!goodCategoriesDB.listCatsDB().contains(CategoriesUtil.categoryList.get(i).getCategoryName())){
                goodCategoriesDB.addGoodCategory(new GoodCategoryModel(CategoriesUtil.categoryList.get(i).getId(), CategoriesUtil.categoryList.get(i).getCategoryName(), CategoriesUtil.categoryList.get(i).getImgId(), CategoriesUtil.categoryList.get(i).getLogoId()));
            }
            CategoriesUtil.categorySelected = CategoriesUtil.categoryList.get(i).getCategoryName();
        }
        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(goodCategoriesDB != null){
            goodCategoriesDB.close();
        }
    }


}