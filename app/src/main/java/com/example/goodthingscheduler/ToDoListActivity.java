package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodthingscheduler.GoodThings.GoodThingsCatActivity;
import com.example.goodthingscheduler.toDoAdd.ItemClickListener;
import com.example.goodthingscheduler.toDoAdd.ToDoAddThingActivity;
import com.example.goodthingscheduler.Categories.CategoriesUtil;
import com.example.goodthingscheduler.Categories.GoodCategoriesDB;
import com.example.goodthingscheduler.Categories.GoodCategoryModel;
import com.example.goodthingscheduler.toDoThings.CategorySelectorAdapter;
import com.example.goodthingscheduler.toDoThings.FragmentDay;
import com.example.goodthingscheduler.toDoThings.MyPagerAdapter;
import com.example.goodthingscheduler.toDoThings.ToDoThingsDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class ToDoListActivity extends AppCompatActivity {

    ToDoThingsDB toDoThingsDB;
    ItemClickListener itemClickListener;
    CategorySelectorAdapter categorySelectorAdapter;
    private GoodCategoriesDB goodCategoriesDB;
    private RecyclerView categorySelectorRV;
    ViewStub catViewStub;

    View inflatedLayout;
    MyPagerAdapter pagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onRestart() {
        super.onRestart();
        setCategorySelector();
        setGoodTitleView();
        //setGoodThingsRecyclerView();
        //new GoodThingsAsyncTask(goodThingsAdapter, toDoThingsDB).execute();

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

        setInspireOutfit();
        setGoodTitleView();
        setBottomNavMenu();
        setAddThingFab();

        /*RecyclerView goodThingsRV = findViewById(R.id.goodThingsRV);

        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),400);
        goodThingsAdapter = new ToDoStateAdapter(new ArrayList<>(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);
        goodThingsRV.setLayoutManager(layoutManager);
        goodThingsRV.setAdapter(goodThingsAdapter);
        new GoodThingsAsyncTask(goodThingsAdapter, toDoThingsDB).execute(); */

        //categorySelectorRV = findViewById(R.id.categoryRVSelector);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        setCategoryStub();
        inflatedLayout = catViewStub.inflate();
        categorySelectorRV = inflatedLayout.findViewById(R.id.habitRecyclerView);
        inflatedLayout.setVisibility(View.GONE);
        setCategorySelector();
    }

    private void setActionBar(){
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));
        getSupportActionBar().setElevation(0);
    }

    private void setInspireOutfit(){
        ImageView glassesSet = findViewById(R.id.glassesView);
        ImageView bowSet = findViewById(R.id.bowView);
        ImageView beltSet = findViewById(R.id.beltView);
        glassesSet.setImageResource(CategoriesUtil.glassesSelected);
        bowSet.setImageResource(CategoriesUtil.bowSelected);
        beltSet.setImageResource(CategoriesUtil.beltSelected);
    }

    private void setCategoryStub(){
        SwitchCompat Catswitch = findViewById(R.id.Catswitch);
        catViewStub = findViewById(R.id.catRVStub);

        Catswitch.setOnClickListener(view -> {

            if (catViewStub.getInflatedId() == View.NO_ID) {
                // If ViewStub is not inflated, inflate it
                catViewStub.inflate();
               } else {
                // If ViewStub is already inflated, toggle its visibility
                if (inflatedLayout != null) {
                    int visibility = inflatedLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                    inflatedLayout.setVisibility(visibility);
                }
            }
        });
    }

    private void setGoodTitleView(){
        ImageView categoryImgView = findViewById(R.id.categoryImage);
        TextView categoryTV = findViewById(R.id.categoryTextView);

        categoryTV.setText(CategoriesUtil.categorySelected);
        categoryImgView.setImageResource(CategoriesUtil.categoryImgId);
        categoryTV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, CategoriesUtil.categoryLogoId, 0);
    }

    private void setCategorySelector(){
        // Initialize listener for categories
        itemClickListener = s -> {
            //when a category button is selected update the adapter
            categorySelectorRV.post(() -> categorySelectorAdapter.notifyDataSetChanged());
            //update Title/Img/RV to selected category
            //CategoriesUtil.categorySelected = s;
            CategoriesUtil.categorySelected = s.getCategoryName();
            CategoriesUtil.categoryImgId = s.getImgId();
            CategoriesUtil.categoryLogoId = s.getLogoId();
            setGoodTitleView();
            categorySelectorRV.post(() -> pagerAdapter.notifyDataSetChanged());

            pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(pagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

            //FragmentDay fragment = (FragmentDay) getFragmentManager().findFragmentById(R.id.fragmentDay);
            FragmentDay fragment = (FragmentDay) getSupportFragmentManager().findFragmentById(R.id.fragmentDay);
            if (fragment != null) {
                fragment.setGoodThingsList();
            } else {
                Log.e("FragmentDay", "Fragment is null");
            }
            //setGoodThingsRecyclerView();
            //new GoodThingsAsyncTask(goodThingsAdapter, toDoThingsDB).execute();
        };

        //displays all saved categories
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),75); //70-80
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);

        categorySelectorRV.setLayoutManager(layoutManager);
        categorySelectorAdapter = new CategorySelectorAdapter(new ArrayList<>(), itemClickListener, this); //, thisActivity);
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

   /* private static class GoodThingsAsyncTask extends AsyncTask<Void, Void, ArrayList<ToDoStatesModel>> {
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
    } */

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

        bottomNavigationView.setSelectedItemId(R.id.toDos);

        // Perform item selected listener
        bottomNavigationView.setOnItemReselectedListener(item -> {});

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.goodThings) {
                finish();
                startActivity(new Intent(getApplicationContext(), GoodThingsCatActivity.class));
                return true;
            } else if (itemId == R.id.schedule) {
                finish();
                startActivity(new Intent(getApplicationContext(),SchedulerActivity.class));
                return true;
            } else return itemId == R.id.toDos;
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