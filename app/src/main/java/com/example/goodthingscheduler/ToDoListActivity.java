package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.goodthingscheduler.toDoAdd.ItemClickListener;
import com.example.goodthingscheduler.toDoAdd.ToDoAddThingActivity;
import com.example.goodthingscheduler.toDoCategories.CategoriesUtil;
import com.example.goodthingscheduler.toDoCategories.GoodCategoriesDB;
import com.example.goodthingscheduler.toDoCategories.GoodCategoryModel;
import com.example.goodthingscheduler.toDoThings.CategorySelectorAdapter;
import com.example.goodthingscheduler.toDoThings.ToDoStateAdapter;
import com.example.goodthingscheduler.toDoThings.ToDoStatesModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingsDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ToDoListActivity extends AppCompatActivity {

    ToDoThingsDB toDoThingsDB;
    ItemClickListener itemClickListener;
    CategorySelectorAdapter categorySelectorAdapter;
    private GoodCategoriesDB goodCategoriesDB;
    private RecyclerView categorySelectorRV;
    private ToDoStateAdapter goodThingsAdapter;

    @Override
    public void onRestart() {
        super.onRestart();
        setCategorySelector();
        setGoodTitleView();
        //setGoodThingsRecyclerView();
        new GoodThingsAsyncTask(goodThingsAdapter, toDoThingsDB).execute();

        //When BACK BUTTON is pressed, the activity on the stack is restarted
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        setActionBar();

        toDoThingsDB = new ToDoThingsDB(this);
        goodCategoriesDB = new GoodCategoriesDB(this);

        CategoriesUtil.goodThingId = 1;
        CategoriesUtil.goodThing = "";

        setGoodTitleView();
        setBottomNavMenu();
        setAddThingFab();
        RecyclerView goodThingsRV = findViewById(R.id.goodThingsRV);
        //setGoodThingsRecyclerView();

        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),400);
        goodThingsAdapter = new ToDoStateAdapter(new ArrayList<>(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);
        goodThingsRV.setLayoutManager(layoutManager);
        goodThingsRV.setAdapter(goodThingsAdapter);
        new GoodThingsAsyncTask(goodThingsAdapter, toDoThingsDB).execute();

        categorySelectorRV = findViewById(R.id.categoryRVSelector);
        setCategorySelector();
    }

    private void setActionBar(){
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_chevron_left_24);
        //getSupportActionBar().setTitle("To Dos -"+" "+CategoriesUtil.categorySelected); //string is custom name you want
        // getSupportActionBar().setTitle("My Good Things");
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        //getSupportActionBar().setBackgroundDrawable(null);
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));
        getSupportActionBar().setElevation(0);
        //setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));
    }

    private void setGoodTitleView(){
     //   ImageView categoryImgView = findViewById(R.id.categoryImage);
        TextView categoryTV = findViewById(R.id.categoryTextView);

        categoryTV.setText(CategoriesUtil.categorySelected);
      //  categoryImgView.setImageResource(CategoriesUtil.categoryImgId);
      //  categoryImgView.setBackgroundColor(Color.parseColor("#70ccfd"));
        categoryTV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, CategoriesUtil.categoryLogoId, 0);
    }

    private void setCategorySelector(){
        // Initialize listener for categories
        itemClickListener = s -> {
            //when a category button is selected update the adapter
            categorySelectorRV.post(() -> categorySelectorAdapter.notifyDataSetChanged());
            //update Title/Img/RV to selected category
            CategoriesUtil.categorySelected = s;
            setGoodTitleView();
            //setGoodThingsRecyclerView();
            new GoodThingsAsyncTask(goodThingsAdapter, toDoThingsDB).execute();
        };

        //lists all saved categories
        ArrayList<GoodCategoryModel> categoryList = goodCategoriesDB.listAllGoodCatsDB();
        categoryList.add(new GoodCategoryModel(0, "add category", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_24));

        //displays all saved categories
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        //layoutManager.setReverseLayout(true);
        //layoutManager.setStackFromEnd(true);
        categorySelectorRV.setLayoutManager(layoutManager);
        categorySelectorAdapter = new CategorySelectorAdapter(categoryList, itemClickListener, this); //, thisActivity);
        categorySelectorRV.setAdapter(categorySelectorAdapter);
        new CategorySelectorAsyncTask(categorySelectorAdapter, goodCategoriesDB).execute();
    }

    private static class CategorySelectorAsyncTask extends AsyncTask<Void, Void, ArrayList<GoodCategoryModel>> {
        private final WeakReference<CategorySelectorAdapter> adapterReference;
        private final GoodCategoriesDB goodCategoriesDB;


        CategorySelectorAsyncTask(CategorySelectorAdapter adapter, GoodCategoriesDB dbHelper) {
            adapterReference = new WeakReference<>(adapter);
            goodCategoriesDB = dbHelper;
        }

        @Override
        protected ArrayList<GoodCategoryModel> doInBackground(Void... voids) {
            // Perform database list on a background thread
            //lists all saved categories
            ArrayList<GoodCategoryModel> categoryList = goodCategoriesDB.listAllGoodCatsDB();
            categoryList.add(new GoodCategoryModel(0, "add category", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_24));
            return categoryList;
        }

        @Override
        protected void onPostExecute(ArrayList<GoodCategoryModel> data) {
            // Update the RecyclerView adapter with the retrieved data
            CategorySelectorAdapter adapter = adapterReference.get();

            if (adapter != null) {
                adapter.setData(data);
            }
        }

    }

  /*  private void setCategorySelector(){
        RecyclerView categorySelectorRV = findViewById(R.id.categoryRVSelector);

        // Create an ExecutorService with a fixed pool of threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Submit the task to load categories
        Future<ArrayList<GoodCategoryModel>> future = executorService.submit(new LoadCategoriesTask());

        // Initialize listener
        itemClickListener = s -> {
            // Notify adapter
            // Submit tasks for execution
            executorService.submit(() -> {
                // Notify adapter
                categorySelectorRV.post(() -> categorySelectorAdapter.notifyDataSetChanged());

                // Update UI on the main thread
                runOnUiThread(() -> {
                    CategoriesUtil.categorySelected = s;
                    setGoodTitleView();
                    setGoodThingsRecyclerView();
                 });
            });
        };

        // Retrieve the result when the task is complete
        try {
            ArrayList<GoodCategoryModel> categoryList = future.get();

            // Update UI on the main thread with the loaded categories
            runOnUiThread(() -> {
                // Add a default category
                categoryList.add(new GoodCategoryModel(0, "add category", R.drawable.snowy_mountain, R.drawable.ic_baseline_add_24));

                // Initialize RecyclerView and Adapter
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                categorySelectorRV.setLayoutManager(layoutManager);
                categorySelectorAdapter = new CategorySelectorAdapter(categoryList, itemClickListener, this);
                categorySelectorRV.setAdapter(categorySelectorAdapter);
            });

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the executor to release resources
            executorService.shutdown();
        }*/

  //  }*/

  /*  private class LoadCategoriesTask implements Callable<ArrayList<GoodCategoryModel>> {
        @Override
        public ArrayList<GoodCategoryModel> call() {
            // Perform potentially time-consuming operations (e.g., database query) here
            return goodCategoriesDB.listAllGoodCatsDB();
        }
    }*/

    /*private void setGoodThingsRecyclerView(){
        //Lists all the categories currently saved
        ArrayList<String> categoryList = toDoThingsDB.listGoodCatDB(); //seems to be working
        //Lists the states of these categories (eg. To Do or Done)
        ArrayList<String> stateList = toDoThingsDB.listGoodStatesInCatDB(categoryList);

        if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
            thingsInState = toDoThingsDB.listAllGoodThings(stateList);
        }else {
            thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
        }

        if(thingsInState.isEmpty()){
            String helloMessage = "Add "+CategoriesUtil.categorySelected+" good thing or to do";
            toDoThingsDB.addGoodThing(new ToDoThingModel(0, CategoriesUtil.categorySelected,helloMessage,"",CategoriesUtil.categoryLogoId,"#FFFFFF","To Do", LocalDate.now().toString(), "date not set","date not set","date"));
        }

        if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
            thingsInState = toDoThingsDB.listAllGoodThings(stateList);
        }else {
            thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
        }

        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),400);

        ToDoStateAdapter goodThingsAdapter = new ToDoStateAdapter(thingsInState, this);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);
        goodThingsRV.setLayoutManager(layoutManager);
        goodThingsRV.setAdapter(goodThingsAdapter);
    }*/

    private static class GoodThingsAsyncTask extends AsyncTask<Void, Void, ArrayList<ToDoStatesModel>> {
        private final WeakReference<ToDoStateAdapter> adapterReference;
        private final ToDoThingsDB toDoThingsDB;

        private ArrayList<ToDoStatesModel> thingsInState;

        GoodThingsAsyncTask(ToDoStateAdapter adapter, ToDoThingsDB dbHelper) {
            adapterReference = new WeakReference<>(adapter);
            toDoThingsDB = dbHelper;
        }

        @Override
        protected ArrayList<ToDoStatesModel> doInBackground(Void... voids) {
            // Perform database insert on a background thread
            performBackground1();
            return thingsInState;
        }

        @Override
        protected void onPostExecute(ArrayList<ToDoStatesModel> data) {
            // Update the RecyclerView adapter with the retrieved data
            ToDoStateAdapter adapter = adapterReference.get();

            if (adapter != null) {
                    adapter.setData(data);
                }
            }

        private void performBackground1(){
            //Lists all the categories currently saved
            ArrayList<String> categoryList = toDoThingsDB.listGoodCatDB(); //seems to be working
            //Lists the states of these categories (eg. To Do or Done)
            ArrayList<String> stateList = toDoThingsDB.listGoodStatesInCatDB(categoryList);

            if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
                thingsInState = toDoThingsDB.listAllGoodThings(stateList);
            }else {
                thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
            }

            if(thingsInState.isEmpty()){
                String helloMessage = "Add "+CategoriesUtil.categorySelected+" good thing or to do";
                toDoThingsDB.addGoodThing(new ToDoThingModel(0, CategoriesUtil.categorySelected,helloMessage,"",CategoriesUtil.categoryLogoId,"#FFFFFF","To Do", LocalDate.now().toString(), "date not set","date not set","date"));
            }

            if(CategoriesUtil.categorySelected.equals("All Good Things (To Do)")){
                thingsInState = toDoThingsDB.listAllGoodThings(stateList);
            }else {
                thingsInState = toDoThingsDB.listGoodThingsInStateInCatDB(CategoriesUtil.categorySelected, stateList);
            }
        }
    }

    private void setAddThingFab(){
        FloatingActionButton addNewGoodThing = findViewById(R.id.addGoodThingFab);

        addNewGoodThing.setOnClickListener(view -> {
            CategoriesUtil.goodThingId=-1;
            CategoriesUtil.goodThing="";
            startActivity(new Intent(getApplicationContext(), ToDoAddThingActivity.class));
        });

        NestedScrollView scrollView = findViewById(R.id.toDoListNestedScrollView);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            // the delay of the extension of the FAB is set for 12 items
            if (scrollY > oldScrollY + 8 && addNewGoodThing.isShown()){ // && addFab.isExtended()) {
                //addFab.shrink();
                addNewGoodThing.hide();
            }
            // the delay of the extension of the FAB is set for 12 items
            if (scrollY < oldScrollY - 8){ // && !addFab.isExtended()) {
                //addFab.extend();
                addNewGoodThing.show();
            }
            // if the nestedScrollView is at the first item of the list then the
            // extended floating action should be in extended state
            if (scrollY == 0) {
                //addFab.extend();
                addNewGoodThing.show();
            }
        });

    }

    public void setBottomNavMenu(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.goodThings);

        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(item -> {});

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.calendar) {
                finish();
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                return true;
            } else if (itemId == R.id.schedule) {
                finish();
                startActivity(new Intent(getApplicationContext(),SchedulerActivity.class));
                return true;
            } else return itemId == R.id.goodThings;
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(toDoThingsDB != null){
            toDoThingsDB.close();
        }
        if(goodCategoriesDB != null){
            goodCategoriesDB.close();
        }
    }
}