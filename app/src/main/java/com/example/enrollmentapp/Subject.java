// Subject.java
package com.example.enrollmentapp;

public class Subject {
    private String name;
    private int credits;
    private boolean isSelected;

    public Subject(String name, int credits) {
        this.name = name;
        this.credits = credits;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
