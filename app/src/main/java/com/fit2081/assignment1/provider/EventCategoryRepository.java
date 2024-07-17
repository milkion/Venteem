package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.EventCategory;

import java.util.List;

public class EventCategoryRepository {

    private EventCategoryDAO eventCategoryDAO;

    private LiveData<List<EventCategory>> allEventCategoriesLiveData;

    EventCategoryRepository(Application application){
        EventCategoryDatabase db = EventCategoryDatabase.getDatabase(application);
        eventCategoryDAO = db.eventCategoryDAO();
        allEventCategoriesLiveData = eventCategoryDAO.getAllEventCategories();
    }

    LiveData<List<EventCategory>> getAllEventCategory() {
        return allEventCategoriesLiveData;
    }

    void insert(EventCategory eventCategory) {
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> eventCategoryDAO.addEventCategory(eventCategory));
    }

    void deleteAll() {
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> eventCategoryDAO.deleteAllEventCategory());
    }

    void update(EventCategory eventCategory) {
        EventCategoryDatabase.databaseWriteExecutor.execute(() -> eventCategoryDAO.updateEventCategory(eventCategory));
    }

    LiveData<List<EventCategory>> getEventCategoryById(String name) {
        return eventCategoryDAO.getEventCategoryById(name);
    }


}
