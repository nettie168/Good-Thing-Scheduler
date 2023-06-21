package com.example.goodthingscheduler.toDoThings;

public class ToDoThingModel {
    public  int id;
    public String category;
    public String goodThing;
    public String inspiredBy;
    public String state;
    public String dateAdded;
    public String dateToStart;
    public String dateToEnd;
    public String datesDone;

    public ToDoThingModel(int id, String category, String goodThing, String state, String datesDone) {
        this.id = id;
        this.category = category;
        this.goodThing = goodThing;
        this.state = state;
        this.datesDone = datesDone;
    }

    public ToDoThingModel(String goodThing) {
        this.goodThing = goodThing;
    }

    public ToDoThingModel(int id, String category, String goodThing, String inspiredBy, String state, String dateAdded, String dateToStart, String dateToEnd, String datesDone) {
        this.id = id;
        this.category = category;
        this.goodThing = goodThing;
        this.inspiredBy = inspiredBy;
        this.state = state;
        this.dateAdded = dateAdded;
        this.dateToStart = dateToStart;
        this.dateToEnd = dateToEnd;
        this.datesDone = datesDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGoodThing() {
        return goodThing;
    }

    public void setGoodThing(String goodThing) {
        this.goodThing = goodThing;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInspiredBy() {
        return inspiredBy;
    }

    public void setInspiredBy(String inspiredBy) {
        this.inspiredBy = inspiredBy;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateToStart() {
        return dateToStart;
    }

    public void setDateToStart(String dateToStart) {
        this.dateToStart = dateToStart;
    }

    public String getDateToEnd() {
        return dateToEnd;
    }

    public void setDateToEnd(String dateToEnd) {
        this.dateToEnd = dateToEnd;
    }

    public String getDatesDone() {
        return datesDone;
    }

    public void setDatesDone(String datesDone) {
        this.datesDone = datesDone;
    }
}
