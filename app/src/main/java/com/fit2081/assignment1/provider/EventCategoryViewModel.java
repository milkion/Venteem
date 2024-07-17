package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.EventCategory;

import java.util.List;

public class EventCategoryViewModel extends AndroidViewModel {

    private EventCategoryRepository repository;
    private LiveData<List<EventCategory>> allEventCategoriesLiveData;

    public EventCategoryViewModel(@NonNull Application application) {
        super(application);

        repository = new EventCategoryRepository(application);
        allEventCategoriesLiveData = repository.getAllEventCategory();
    }

    public LiveData<List<EventCategory>> getAllEventCategory() {
        return allEventCategoriesLiveData;
    }

    public void insert(EventCategory eventCategory) {
        repository.insert(eventCategory);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void update(EventCategory eventCategory) {
        repository.update(eventCategory);
    }

    public LiveData<List<EventCategory>> getEventCategoryById(String name) {
        return repository.getEventCategoryById(name);
    }
}
