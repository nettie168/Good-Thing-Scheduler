package com.example.goodthingscheduler.toDoAdd;

import com.example.goodthingscheduler.Categories.GoodCategoryModel;

import java.util.ArrayList;

public class CategoryGroupsModel {
    private String group;
    private ArrayList<GoodCategoryModel> thingsInGroupList;

    public CategoryGroupsModel(String group, ArrayList<GoodCategoryModel> thingsInGroupList) {
        this.group = group;
        this.thingsInGroupList = thingsInGroupList;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ArrayList<GoodCategoryModel> getThingsInGroupList() {
        return thingsInGroupList;
    }

    public void setThingsInGroupList(ArrayList<GoodCategoryModel> thingsInGroupList) {
        this.thingsInGroupList = thingsInGroupList;
    }
}
