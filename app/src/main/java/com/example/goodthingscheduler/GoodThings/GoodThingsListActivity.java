package com.example.goodthingscheduler.GoodThings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goodthingscheduler.Categories.CategoriesUtil;
import com.example.goodthingscheduler.R;
import com.example.goodthingscheduler.ToDoListActivity;
import com.example.goodthingscheduler.toDoThings.ToDoStateAdapter;
import com.example.goodthingscheduler.toDoThings.ToDoStatesModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingModel;
import com.example.goodthingscheduler.toDoThings.ToDoThingsDB;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.ArrayList;

public class GoodThingsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_things_list);

        setGoodTitleView();

        ToDoThingsDB toDoThingsDB = new ToDoThingsDB(this);

        int mNoOfColumns = CategoriesUtil.calculateNoOfColumns(getApplicationContext(),400);
        ToDoStateAdapter goodThingsAdapter = new ToDoStateAdapter(new ArrayList<>(), this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, mNoOfColumns);

        RecyclerView goodThingsRV = findViewById(R.id.goodThingsRV);
        goodThingsRV.setLayoutManager(layoutManager);
        goodThingsRV.setAdapter(goodThingsAdapter);
        new GoodThingsAsyncTask(goodThingsAdapter, toDoThingsDB).execute();
    }

    private void setGoodTitleView(){
        ImageView categoryImgView = findViewById(R.id.categoryImage);
        TextView categoryTV = findViewById(R.id.categoryTextView);

        categoryTV.setText(CategoriesUtil.categorySelected);
        categoryImgView.setImageResource(CategoriesUtil.categoryImgId);
        categoryTV.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, CategoriesUtil.categoryLogoId, 0);
    }

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
}