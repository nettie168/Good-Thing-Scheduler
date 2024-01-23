package com.example.goodthingscheduler.toDoAdd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.example.goodthingscheduler.toDoCategories.GoodCategoriesDB;
import com.example.goodthingscheduler.toDoCategories.GoodCategoryModel;

import java.util.ArrayList;
import java.util.Objects;

public class AddNewCategoryActivity extends AppCompatActivity {

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

        //Aim to try out all bases (do one thing for a month from each, have a free/or very cheap idea for each), get points & badges, for depth(doing same thing consistently) & some breadth
        ArrayList<GoodCategoryModel> categoryList = new ArrayList<>();
        //Most Popular
        categoryList.add(new GoodCategoryModel(0, "Books", R.drawable.snowy_mountain, R.drawable.ic_baseline_auto_stories_24));
        categoryList.add(new GoodCategoryModel(1, "Films and tv", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_movies_24));
        categoryList.add(new GoodCategoryModel(2, "Gardening and House Plants", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        categoryList.add(new GoodCategoryModel(2, "Photography", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_a_photo_24));
        //categoryList.add(new GoodCategoryModel(2, "Tasty food", R.drawable.snowy_mountain, R.drawable.ic_baseline_ramen_dining_24));
        categoryList.add(new GoodCategoryModel(2, "Healthy Mealtime", R.drawable.snowy_mountain, R.drawable.ic_baseline_ramen_dining_24));
        categoryList.add(new GoodCategoryModel(3, "Baking", R.drawable.snowy_mountain, R.drawable.ic_baseline_bakery_dining_24));
        categoryList.add(new GoodCategoryModel(2, "Drawing", R.drawable.snowy_mountain, R.drawable.ic_baseline_draw_24));
       // categoryList.add(new GoodCategoryModel(2, "Time in nature", R.drawable.snowy_mountain, R.drawable.ic_baseline_emoji_nature_24));
      //  categoryList.add(new GoodCategoryModel(2, "Animals", R.drawable.snowy_mountain, R.drawable.ic_baseline_pets_24));
      //  categoryList.add(new GoodCategoryModel(2, "Quotes", R.drawable.snowy_mountain, R.drawable.ic_baseline_format_quote_24));
       // categoryList.add(new GoodCategoryModel(2, "Fitness", R.drawable.snowy_mountain, R.drawable.ic_baseline_format_quote_24));

        categoryList.add(new GoodCategoryModel(2, "Language learning", R.drawable.snowy_mountain, R.drawable.ic_baseline_language_24));
        categoryList.add(new GoodCategoryModel(2, "Practice Music", R.drawable.snowy_mountain, R.drawable.ic_baseline_music_note_24));
        categoryList.add(new GoodCategoryModel(2, "Listen to Music", R.drawable.snowy_mountain, R.drawable.ic_baseline_music_note_24));
        categoryList.add(new GoodCategoryModel(2, "Visit New Places", R.drawable.snowy_mountain, R.drawable.ic_baseline_attractions_24));
        categoryList.add(new GoodCategoryModel(2, "Painting", R.drawable.snowy_mountain, R.drawable.ic_baseline_brush_24));
        categoryList.add(new GoodCategoryModel(2, "Arts & Crafts", R.drawable.snowy_mountain, R.drawable.ic_baseline_brush_24));


        ArrayList<GoodCategoryModel> homeList = new ArrayList<>();
        homeList.add(new GoodCategoryModel(0,"Tidy Spaces", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        homeList.add(new GoodCategoryModel(0,"Tidy Kitchen", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        homeList.add(new GoodCategoryModel(0,"Clean Bathroom", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        homeList.add(new GoodCategoryModel(0,"Laundry", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        homeList.add(new GoodCategoryModel(0,"Healthy Finances", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        homeList.add(new GoodCategoryModel(0,"Food shopping", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        homeList.add(new GoodCategoryModel(0,"Self-Care", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        homeList.add(new GoodCategoryModel(0,"Important but not Urgent", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));


        ArrayList<GoodCategoryModel> workList = new ArrayList<>();
        workList.add(new GoodCategoryModel(0,"Goals", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        workList.add(new GoodCategoryModel(0,"Meetings", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        workList.add(new GoodCategoryModel(0,"Work Life Balance", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));
        workList.add(new GoodCategoryModel(0,"Finances", R.drawable.snowy_mountain,R.drawable.ic_baseline_wb_sunny_24));


        //Games & Puzzles (Brain Training)
        ArrayList<GoodCategoryModel> GamesList = new ArrayList<>();

        GamesList.add(new GoodCategoryModel(2, "Games", "Board Games", R.drawable.snowy_mountain, R.drawable.ic_baseline_extension_24));
        GamesList.add(new GoodCategoryModel(2, "Games", "Card Games", R.drawable.snowy_mountain, R.drawable.ic_baseline_extension_24));
        GamesList.add(new GoodCategoryModel(2, "Games","Video Games", R.drawable.snowy_mountain, R.drawable.ic_baseline_sports_esports_24));
        GamesList.add(new GoodCategoryModel(2, "Games","Chess", R.drawable.snowy_mountain, R.drawable.ic_baseline_extension_24));
        GamesList.add(new GoodCategoryModel(2, "Games","Puzzles (sudoku, crossword, wordle, jigsaw)", R.drawable.snowy_mountain, R.drawable.ic_baseline_extension_24));
        GamesList.add(new GoodCategoryModel(2, "Games","Jigsaw Puzzles", R.drawable.snowy_mountain, R.drawable.ic_baseline_extension_24));

        ////Fitness
        //Dance
        ArrayList<GoodCategoryModel> DanceList = new ArrayList<>();

        //categoryList.add(new GoodCategoryModel(2, "Dancing Dances", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Dances","Ballet", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Dances","Street", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Dances","Contemporary", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Dances", "Ballroom Dancing", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Dances","Salsa", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Break Dancing", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Hip Hop", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Jazz", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Tap", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Swing", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Modern", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Tik Tok Dancing", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));

        //Folk Dance
        DanceList.add(new GoodCategoryModel(2, "Raqs Sharki/Belly Dancing", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Kathak (Bollywood dancing)", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Irish", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Folk (Akademi?)", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Ceilidh", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Bhangra", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Flamenco", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        DanceList.add(new GoodCategoryModel(2, "Morris Dancing", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));

        //Creations (To Do)
        ArrayList<GoodCategoryModel> CreationsList = new ArrayList<>();
        CreationsList.add(new GoodCategoryModel(2, "Web development", R.drawable.snowy_mountain, R.drawable.ic_baseline_code_24));
        CreationsList.add(new GoodCategoryModel(2, "App development", R.drawable.snowy_mountain, R.drawable.ic_baseline_code_24));
        CreationsList.add(new GoodCategoryModel(2, "D&D", R.drawable.snowy_mountain, R.drawable.baseline_adb_24));
        CreationsList.add(new GoodCategoryModel(2, "Music", R.drawable.snowy_mountain, R.drawable.ic_baseline_music_note_24));
        CreationsList.add(new GoodCategoryModel(2, "Drawings", R.drawable.snowy_mountain, R.drawable.ic_baseline_draw_24));
        CreationsList.add(new GoodCategoryModel(2, "Paintings", R.drawable.snowy_mountain, R.drawable.ic_baseline_brush_24));
        CreationsList.add(new GoodCategoryModel(2, "Photography", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_a_photo_24));
        CreationsList.add(new GoodCategoryModel(2, "Film Making", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_a_photo_24));
        CreationsList.add(new GoodCategoryModel(2, "Dress Making", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_a_photo_24));
        CreationsList.add(new GoodCategoryModel(2, "Knitting & Crochet", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_a_photo_24));
        CreationsList.add(new GoodCategoryModel(2, "Cosplaying", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_a_photo_24));


        //Music & Acting (To See)
        ArrayList<GoodCategoryModel> ArtsList = new ArrayList<>();
        ArtsList.add(new GoodCategoryModel(2, "Theatre", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
        ArtsList.add(new GoodCategoryModel(2, "Drag Acts", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
        ArtsList.add(new GoodCategoryModel(2, "Comedy", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));

        //Express Yourself
        ArtsList.add(new GoodCategoryModel(2, "Create Theatre", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
        ArtsList.add(new GoodCategoryModel(2, "Create Drag Acts", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
        ArtsList.add(new GoodCategoryModel(2, "Create Comedy", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
     //   ArtsList.add(new GoodCategoryModel(2, "Perform Musicals", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
      //  ArtsList.add(new GoodCategoryModel(2, "Brain Dump", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
      //  ArtsList.add(new GoodCategoryModel(2, "Expressive Painting", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
      //  ArtsList.add(new GoodCategoryModel(2, "Expressive Storytelling", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));
      //  ArtsList.add(new GoodCategoryModel(2, "Expressive Drawings", R.drawable.snowy_mountain, R.drawable.ic_baseline_theater_comedy_24));


        //Sports
        ArrayList<GoodCategoryModel> sportsList = new ArrayList<>();
        sportsList.add(new GoodCategoryModel(2, "Sports","Climbing", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        sportsList.add(new GoodCategoryModel(2, "Sports","Cycling", R.drawable.snowy_mountain, R.drawable.ic_baseline_pedal_bike_24));
        sportsList.add(new GoodCategoryModel(2, "Sports","Mountain Biking", R.drawable.snowy_mountain, R.drawable.ic_baseline_pedal_bike_24)); //2 wheel & 4 wheel
        sportsList.add(new GoodCategoryModel(2, "Sports","Swimming", R.drawable.snowy_mountain, R.drawable.ic_baseline_water_24));
        sportsList.add(new GoodCategoryModel(2, "Sports","Outdoor/Wild Swimming", R.drawable.snowy_mountain, R.drawable.ic_baseline_water_24));
        sportsList.add(new GoodCategoryModel(2, "Running", R.drawable.snowy_mountain, R.drawable.ic_baseline_directions_run_24));
        sportsList.add(new GoodCategoryModel(2, "Rowing", R.drawable.snowy_mountain, R.drawable.ic_baseline_rowing_24));
        sportsList.add(new GoodCategoryModel(2, "Roller-Skating", R.drawable.snowy_mountain, R.drawable.ic_baseline_ice_skating_24));


        //Ball Sports
        sportsList.add(new GoodCategoryModel(2, "Tennis", R.drawable.snowy_mountain, R.drawable.ic_baseline_sports_tennis_24));
        sportsList.add(new GoodCategoryModel(2, "Basketball", R.drawable.snowy_mountain, R.drawable.ic_baseline_sports_basketball_24)); //wheelchair & non
        sportsList.add(new GoodCategoryModel(2, "Rugby", R.drawable.snowy_mountain, R.drawable.ic_baseline_sports_rugby_24)); //wheelchair and non
        sportsList.add(new GoodCategoryModel(2, "Football", R.drawable.snowy_mountain, R.drawable.ic_baseline_sports_soccer_24));
        sportsList.add(new GoodCategoryModel(2, "Badminton", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        sportsList.add(new GoodCategoryModel(2, "Table Tennis", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        sportsList.add(new GoodCategoryModel(2, "Hockey", R.drawable.snowy_mountain, R.drawable.ic_baseline_sports_hockey_24));

        //Martial Arts
        sportsList.add(new GoodCategoryModel(2, "Martial Arts", R.drawable.snowy_mountain, R.drawable.ic_baseline_sports_martial_arts_24));

        //Snow Sports
        sportsList.add(new GoodCategoryModel(2, "Skiing", R.drawable.snowy_mountain, R.drawable.ic_baseline_downhill_skiing_24));
        sportsList.add(new GoodCategoryModel(2, "Ice-Skating", R.drawable.snowy_mountain, R.drawable.ic_baseline_ice_skating_24));
        sportsList.add(new GoodCategoryModel(2, "Snowboarding", R.drawable.snowy_mountain, R.drawable.ic_baseline_snowboarding_24));

        //Water Sports
        sportsList.add(new GoodCategoryModel(2, "Surfing", R.drawable.snowy_mountain, R.drawable.ic_baseline_surfing_24));


        ////Self-care & Well-being
        ArrayList<GoodCategoryModel> selfCareList = new ArrayList<>();
        selfCareList.add(new GoodCategoryModel(2, "Reflections", R.drawable.snowy_mountain, R.drawable.ic_baseline_self_improvement_24));
        selfCareList.add(new GoodCategoryModel(2, "Meditations", R.drawable.snowy_mountain, R.drawable.ic_baseline_self_improvement_24));
        selfCareList.add(new GoodCategoryModel(2, "Yoga", R.drawable.snowy_mountain, R.drawable.ic_baseline_self_improvement_24));
        selfCareList.add(new GoodCategoryModel(2, "Time in nature", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        selfCareList.add(new GoodCategoryModel(2, "Finances", R.drawable.snowy_mountain, R.drawable.ic_baseline_account_balance_24));
        selfCareList.add(new GoodCategoryModel(2, "Healthy Meals", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        selfCareList.add(new GoodCategoryModel(2, "Happy Home", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));
        selfCareList.add(new GoodCategoryModel(2, "Walks and hikes", R.drawable.snowy_mountain, R.drawable.ic_baseline_hiking_24));

        //Self-Development
    /*    selfCareList.add(new GoodCategoryModel(2, "Practice Courage", R.drawable.snowy_mountain, R.drawable.ic_baseline_hiking_24));
        selfCareList.add(new GoodCategoryModel(2, "Practice Resilience", R.drawable.snowy_mountain, R.drawable.ic_baseline_hiking_24));
        selfCareList.add(new GoodCategoryModel(2, "Practice Maths", R.drawable.snowy_mountain, R.drawable.ic_baseline_hiking_24));
        selfCareList.add(new GoodCategoryModel(2, "Improve Reading",R.drawable.snowy_mountain, R.drawable.ic_baseline_hiking_24));
*/
        //Community/Helping Others
        ArrayList<GoodCategoryModel> CommunityList = new ArrayList<>();
        CommunityList.add(new GoodCategoryModel(2, "Time with Partner", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Time with Friends", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Time with Family", R.drawable.snowy_mountain, R.drawable.ic_baseline_diversity_3_24));
        CommunityList.add(new GoodCategoryModel(2, "Acts of Kindness", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Time with Loved Ones", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Time with Pets", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        //CommunityList.add(new GoodCategoryModel(2, "Time with House Plants", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));
        CommunityList.add(new GoodCategoryModel(2, "Volunteering", R.drawable.snowy_mountain, R.drawable.ic_baseline_volunteer_activism_24));

        ////Travel
        ArrayList<GoodCategoryModel> TravelList = new ArrayList<>();
        TravelList.add(new GoodCategoryModel(2, "Places to visit", R.drawable.snowy_mountain, R.drawable.ic_baseline_travel_explore_24));
        TravelList.add(new GoodCategoryModel(2, "Places to eat", R.drawable.snowy_mountain, R.drawable.ic_baseline_restaurant_menu_24));
        TravelList.add(new GoodCategoryModel(2, "Places to stay", R.drawable.snowy_mountain, R.drawable.ic_baseline_hotel_24));
        TravelList.add(new GoodCategoryModel(2, "Hikes", R.drawable.snowy_mountain, R.drawable.ic_baseline_hiking_24));
        TravelList.add(new GoodCategoryModel(2, "Camping", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));

        //  categoryList.add(new GoodCategoryModel(2, "Good Things", R.drawable.snowy_mountain, R.drawable.ic_baseline_local_florist_24));

      /*  ArrayList<GoodCategoryModel> SensationsList = new ArrayList<>();
        SensationsList.add(new GoodCategoryModel(2, "Favourite Smells", R.drawable.snowy_mountain, R.drawable.ic_baseline_travel_explore_24));
        SensationsList.add(new GoodCategoryModel(2, "Favourite Feelings", R.drawable.snowy_mountain, R.drawable.ic_baseline_travel_explore_24));
        SensationsList.add(new GoodCategoryModel(2, "Favourite Tastes", R.drawable.snowy_mountain, R.drawable.ic_baseline_travel_explore_24));
        SensationsList.add(new GoodCategoryModel(2, "Favourite Sounds", R.drawable.snowy_mountain, R.drawable.ic_baseline_travel_explore_24));
        SensationsList.add(new GoodCategoryModel(2, "Favourite Sights", R.drawable.snowy_mountain, R.drawable.ic_baseline_travel_explore_24));
*/
        ArrayList<CategoryGroupsModel> groupsList = new ArrayList<>();
        groupsList.add(new CategoryGroupsModel("Popular",categoryList));
        groupsList.add(new CategoryGroupsModel("Home",homeList));
        groupsList.add(new CategoryGroupsModel("Community",CommunityList));
        groupsList.add(new CategoryGroupsModel("Sports",sportsList));
        groupsList.add(new CategoryGroupsModel("Creations",CreationsList));
        groupsList.add(new CategoryGroupsModel("Games",GamesList));
        groupsList.add(new CategoryGroupsModel("Arts",ArtsList));
        groupsList.add(new CategoryGroupsModel("Travel & Adventure",TravelList));
        groupsList.add(new CategoryGroupsModel("Self-Care",selfCareList));
        groupsList.add(new CategoryGroupsModel("Dance",DanceList));
        //groupsList.add(new CategoryGroupsModel("Work",workList));
        //   groupsList.add(new GoodGroupsModel("Senses",SensationsList));


        // Initialize listener
        /*      itemClickListener = new ItemClickListener() {
            @Override public void onClick(String s)
            {
                // Notify adapter
                newCategoriesRV.post(() -> {
           //         newCategoryAdapter.notifyDataSetChanged();
                });
                // Display toast
                Toast.makeText(getApplicationContext(), "Selected : " + s,Toast.LENGTH_SHORT).show();
                //CategoriesUtil.categorySelected = s;
                //finish();
                //goodCategoryModelArrayList.add(new GoodCategoryModel(0, CategoriesUtil.categorySelected, CategoriesUtil.categoryImgId, CategoriesUtil.categoryLogoId));
                Log.i("adding Category List",CategoriesUtil.categorySelected+" "+CategoriesUtil.categoryImgId+" "+CategoriesUtil.categoryLogoId);

            }
        };*/
        // Set layout manager
           newCategoriesRV.setLayoutManager(new LinearLayoutManager(this));
      //  newCategoriesRV.setLayoutManager(new GridLayoutManager(this,5));
        // Initialize adapter
     //   newCategoryAdapter = new NewCategoryAdapter(categoryList, this, itemClickListener);
        addCategoryAdapter = new CategoryGroupsAdapter(groupsList, this); //, itemClickListener, thisActivity);
        // set adapter
        newCategoriesRV.setAdapter(addCategoryAdapter);

     /*   NewCategoryAdapter categoryAdapter = new NewCategoryAdapter(categoryList,this);
        // LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // CategoryTagsAdapter categoryAdapter = new CategoryTagsAdapter(categoryList,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 6);
        newCategoriesRV.setLayoutManager(layoutManager);
        newCategoriesRV.setAdapter(categoryAdapter);*/
    }

    private void confirmCategories(){
//for i in CategoryUtils.categoryList, add category to DB
        //if action before was add from toDo Dialog then finish (reload Dialog recyclerview)
        //if action was from categoryActivity also finish (recreate activity)
        for(int i = 0; i < CategoriesUtil.categoryList.size(); i ++){
            //Log.i("confirm categories","i is "+i+", id is "+CategoriesUtil.categoryList.get(i).getId()+" "+CategoriesUtil.categoryList.get(i).getCategoryName()+", img: "+CategoriesUtil.categoryList.get(i).getImgId()+", logo:  "+CategoriesUtil.categoryList.get(i).getLogoId());
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