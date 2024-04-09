package com.fit2081.assignment1;

public class EventCategory {

    private String categoryId;
    private String categoryName;
    private int eventCount;
    private boolean isActive;

    public EventCategory(String categoryId, String categoryName, int eventCount, boolean isActive) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getEventCount() {
        return eventCount;
    }

    public boolean isActive() {
        return isActive;
    }
}
