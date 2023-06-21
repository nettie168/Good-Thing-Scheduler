package com.example.goodthingscheduler.XPCount;

public class XPCountModel {
    int id;
    String Date;
    int xp;

    public XPCountModel(String date, int xp) {
        Date = date;
        this.xp = xp;
    }

    public XPCountModel(int id, String date, int xp) {
        this.id = id;
        Date = date;
        this.xp = xp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
