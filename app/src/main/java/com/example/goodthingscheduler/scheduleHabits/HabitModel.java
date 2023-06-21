package com.example.goodthingscheduler.scheduleHabits;

public class HabitModel {
    public int id;
    public String routine;
    public String task;
    public String detail;
    public String date;
    public String startDate;
    public String endDate;
    public int target;
    public String direction;
    public int status;
    public String daysOfWeek;
    public int frequency;
    public String onDateNext;
    public int habitImgId;

    /*   + ROUTINE + " TEXT, "
            + TASK + " TEXT, "
            + DETAIL + " TEXT, "
            + START_DATE + " TEXT, "
            + END_DATE + " TEXT, "
            + TARGET + " INTEGER, "
            + STATUS + " INTEGER, "
            + FREQUENCY + " TEXT, "
            + HABIT_IMG_ID + " INTEGER)";*/

    public HabitModel(int id, String routine, String task, String detail, String startDate, String endDate, int target, String direction, int status, String daysOfWeek, int frequency, String onDateNext, int habitImgId) {
        this.id = id;
        this.routine = routine;
        this.task = task;
        this.detail = detail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.target = target;
        this.direction = direction;
        this.status = status;
        this.daysOfWeek=daysOfWeek;
        this.frequency = frequency;
        this.onDateNext = onDateNext;
        this.habitImgId = habitImgId;
    }

    public HabitModel(int id, String date, String routine, String task, String detail, int status, int habitImgId) {
        this.id = id;
        this.routine = routine;
        this.task = task;
        this.date = date;
        this.detail = detail;
        this.status = status;
        this.habitImgId = habitImgId;
    }

    public HabitModel(int id, String date, String routine, String task, int status){//, int habitImgId){
        this.id = id;
        this.routine = routine;
        this.task = task;
        this.date = date;
        this.status = status;
     //   this.habitImgId = habitImgId;
    }

    public HabitModel(String task, int status, int habitImgId){
        this.task=task;
        this.status=status;
        this.habitImgId=habitImgId;
    }

    public HabitModel(String routine, int habitImgId) {
        this.routine = routine;
        this.habitImgId = habitImgId;
    }

    public HabitModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoutine() {
        return routine;
    }

    public void setRoutine(String routine) {
        this.routine = routine;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getHabitImgId() {
        return habitImgId;
    }

    public void setHabitImgId(int habitImgId) {
        this.habitImgId = habitImgId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOnDateNext() {
        return onDateNext;
    }

    public void setOnDateNext(String onDateNext) {
        this.onDateNext = onDateNext;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

}

