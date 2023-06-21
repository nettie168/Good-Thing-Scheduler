package com.example.goodthingscheduler.toDoCategories;

public class GoodCategoryModel {
    public int id;
    public String categoryName;
    public int imgId;
    public int logoId;
    public String group;

    public GoodCategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }

    public GoodCategoryModel(int id, String categoryName, int imgId, int logoId) {
        this.id = id;
        this.categoryName = categoryName;
        this.imgId = imgId;
        this.logoId = logoId;
    }

    public GoodCategoryModel(int id, String group, String categoryName, int imgId, int logoId) {
        this.id = id;
        this.categoryName = categoryName;
        this.imgId = imgId;
        this.logoId = logoId;
        this.group = group;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
