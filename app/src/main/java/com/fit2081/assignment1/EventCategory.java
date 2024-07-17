package com.fit2081.assignment1;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = EventCategory.TABLE_NAME)
public class EventCategory {
    public static final String TABLE_NAME = "eventCategory";
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "categoryId")
    private String categoryId;

    @ColumnInfo(name = "categoryName")
    private String categoryName;

    @ColumnInfo(name = "eventCount")
    private int eventCount;

    @ColumnInfo(name = "isActive")
    private boolean isActive;

    @ColumnInfo(name = "categoryLocation")
    private String location;

    public EventCategory(String categoryId, String categoryName, int eventCount, boolean isActive, String location) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;
        this.location = location;
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

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getId() {
       return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
