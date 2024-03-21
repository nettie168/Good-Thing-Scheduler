package com.example.goodthingscheduler.toDoThings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.goodthingscheduler.Categories.GoodCategoryModel;
import com.example.goodthingscheduler.FragmentT;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentDay();
            case 1:
                return new FragmentT();
            case 2:
                return new FragmentT();
            case 3:
                return new FragmentT();
            case 4:
                return  new FragmentT();
            default:
                return new FragmentDay(); //return null
        }
    }

    @Override
    public int getCount() {
        return 5; // Number of fragments
    }

    public void setData(ArrayList<GoodCategoryModel> newData) {
        // categoryList = newData;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // You can set tab titles here if needed
        String title = null;
        if (position == 0)
            title = "Day";
        else if (position == 1)
            title = "Week";
        else if (position == 2)
            title = "Month";
        else if (position == 3)
            title = "Year";
        else if(position == 4)
            title = "All";
        return title;
    }
}

