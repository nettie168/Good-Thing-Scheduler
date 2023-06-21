package com.example.goodthingscheduler.scheduleHabits;

import java.time.LocalTime;
import java.util.ArrayList;

public class RoutineModel {
    public int id;
    public String routine;
    public int startHour;
    public int startMinute;

    public int endHour;
    public int endMinute;

    public String daysOfWeek;
    public int frequency;
    public int noOfHabits;
    public int openClosed;
    public ArrayList<HabitModel> habitArrayList;
    //public int habitsDoneToday;

    public RoutineModel(String routine){
        this.routine = routine;
    }
    public RoutineModel(String routine, ArrayList<HabitModel> habitArrayList) {
        this.routine = routine;
        this.habitArrayList = habitArrayList;
    }

    public RoutineModel(String routine, int startHour, int startMinute, int endHour, int endMinute,
                        String daysOfWeek, int openClosed) {
        this.routine = routine;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.daysOfWeek = daysOfWeek;
        this.openClosed = openClosed;
    }

    public RoutineModel(int id, String routine, int startHour, int startMinute, int endHour, int endMinute, String daysOfWeek, int openClosed) {
        this.id=id;
        this.routine = routine;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.daysOfWeek = daysOfWeek;
        this.openClosed = openClosed;
    }

    public RoutineModel(int id, String routine, int startHour, int startMinute, int endHour, int endMinute, String daysOfWeek, int openClosed, ArrayList<HabitModel> habitArrayList) {
        this.id = id;
        this.routine = routine;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.daysOfWeek = daysOfWeek;
        this.openClosed = openClosed;
        this.habitArrayList = habitArrayList;
    }

    public RoutineModel(int id, int openClosed) {
        this.id=id;
        this.openClosed = openClosed;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public ArrayList<HabitModel> getHabitArrayList() {
        return habitArrayList;
    }

    public void setHabitArrayList(ArrayList<HabitModel> habitArrayList) {
        this.habitArrayList = habitArrayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public int getOpenClosed() {
        return openClosed;
    }

    public void setOpenClosed(int openClosed) {
        this.openClosed = openClosed;
    }
}
