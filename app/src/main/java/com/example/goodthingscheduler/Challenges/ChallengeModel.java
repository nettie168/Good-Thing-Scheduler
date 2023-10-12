package com.example.goodthingscheduler.Challenges;

import com.example.goodthingscheduler.scheduleHabits.HabitModel;

import java.util.ArrayList;

public class ChallengeModel {
    private String name;
    private String detail;
    private String category;
    private ArrayList<HabitModel> habitsToDoList;
    private int img;

    public ChallengeModel(String name, String detail, String category, ArrayList<HabitModel> habitsToDoList, int img) {
        this.name = name;
        this.detail = detail;
        this.category = category;
        this.habitsToDoList = habitsToDoList;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<HabitModel> getHabitsToDoList() {
        return habitsToDoList;
    }

    public void setHabitsToDoList(ArrayList<HabitModel> habitsToDoList) {
        this.habitsToDoList = habitsToDoList;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
