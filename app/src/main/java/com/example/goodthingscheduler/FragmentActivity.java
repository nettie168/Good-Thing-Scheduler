package com.example.goodthingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.example.goodthingscheduler.Categories.CategoriesUtil;
import com.example.goodthingscheduler.Categories.GoodCategoriesDB;
import com.example.goodthingscheduler.Categories.GoodCategoryModel;
import com.example.goodthingscheduler.GoodThings.GoodThingsCatActivity;
import com.example.goodthingscheduler.toDoAdd.ItemClickListener;
import com.example.goodthingscheduler.toDoThings.CategorySelectorAdapter;
import com.example.goodthingscheduler.toDoThings.MyPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class FragmentActivity extends AppCompatActivity {

    RecyclerView categorySelectorRV;
    ItemClickListener itemClickListener;
    CategorySelectorAdapter categorySelectorAdapter;
    private GoodCategoriesDB goodCategoriesDB;
    ViewStub catViewStub;

    View inflatedLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        setActionBar();

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        catViewStub = findViewById(R.id.catRVStub);
        inflatedLayout = catViewStub.inflate();
        categorySelectorRV = inflatedLayout.findViewById(R.id.habitRecyclerView);

        setCategoryStub();

        goodCategoriesDB = new GoodCategoriesDB(this);
        setCategorySelector();

        setBottomNavMenu();
    }

    private void setActionBar(){
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.parseColor("#70ccfd")));
        getSupportActionBar().setElevation(0);
    }
    private void setCategoryStub(){
        SwitchCompat Catswitch = findViewById(R.id.Catswitch);

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

    private void setCategorySelector() {
        // Initialize listener for categories
        itemClickListener = s -> {
            categorySelectorRV.post(() -> categorySelectorAdapter.notifyDataSetChanged());
            CategoriesUtil.categorySelected = s.getCategoryName();
            CategoriesUtil.categoryImgId = s.getImgId();
            CategoriesUtil.categoryLogoId = s.getLogoId();
            //setGoodTitleView();
        };

        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),75); //70-80
        //GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

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
}