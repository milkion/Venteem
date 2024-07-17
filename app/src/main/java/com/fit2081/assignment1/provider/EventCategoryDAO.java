package com.fit2081.assignment1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fit2081.assignment1.EventCategory;

import java.util.List;

@Dao
public interface EventCategoryDAO {

    @Query("select * from eventCategory")
    LiveData<List<EventCategory>> getAllEventCategories();

    @Insert
    void addEventCategory(EventCategory eventCategory);

    @Query("DELETE FROM eventCategory")
    void deleteAllEventCategory();

    @Update
    void updateEventCategory(EventCategory eventCategory);

    @Query("SELECT * FROM eventCategory WHERE categoryId = :name")
    LiveData<List<EventCategory>> getEventCategoryById(String name);


}
