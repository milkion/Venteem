package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.Event;

import java.util.List;

public class EventRepository {

    private EventDAO eventDAO;
    private LiveData<List<Event>> allEventsLiveData;

    EventRepository(Application application){
        EventDatabase db = EventDatabase.getDatabase(application);
        eventDAO = db.eventDAO();
        allEventsLiveData = eventDAO.getAllEvent();
    }

    LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }

    void insert(Event event) {
        EventDatabase.databaseWriteExecutor.execute(() -> eventDAO.addEvent(event));
    }

    void deleteEvent(String eventId) {
        EventDatabase.databaseWriteExecutor.execute(() -> eventDAO.deleteEvent(eventId));
    }

    void deleteAll() {
        EventDatabase.databaseWriteExecutor.execute(() -> eventDAO.deleteAllEvent());
    }


}
