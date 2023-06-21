package com.example.goodthingscheduler.toDoThings;

import java.util.ArrayList;

public class ToDoStatesModel {
    private String category;
    private String state;
    private ArrayList<ToDoThingModel> thingsInStateList;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ToDoStatesModel(String category, String state, ArrayList<ToDoThingModel> thingsInStateList) {
        this.category = category;
        this.state = state;
        this.thingsInStateList = thingsInStateList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ArrayList<ToDoThingModel> getThingsInStateList() {
        return thingsInStateList;
    }

    public void setThingsInStateList(ArrayList<ToDoThingModel> thingsInStateList) {
        this.thingsInStateList = thingsInStateList;
    }
}
