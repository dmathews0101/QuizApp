package com.quizapp01;

public class Category {
    public static final int EXPLORE_INDIA = 1;
    public static final int COUNTRIES = 2;
    public static final int NOVELS = 3;
    public static final int PLANETS = 4;

    private int id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}