package com.fit2081.assignment1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.fit2081.assignment1.Event;
import com.fit2081.assignment1.EventCategory;

import java.util.List;

@Dao
public interface EventDAO {

    @Query("select * from event")
    LiveData<List<Event>> getAllEvent();

    @Insert
    void addEvent(Event event);

    @Query("DELETE FROM event")
    void deleteAllEvent();

    @Query("DELETE FROM event WHERE eventId = :eventId")
    void deleteEvent(String eventId);
}
