package com.fit2081.assignment1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fit2081.assignment1.Event;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventRepository repository;
    private LiveData<List<Event>> allEventsLiveData;

    public EventViewModel(@NonNull Application application){
        super(application);

        repository = new EventRepository(application);
        allEventsLiveData = repository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEventsLiveData;
    }

    public void insert(Event event) {
        repository.insert(event);
    }

    public void deleteEvent(String eventId) {
        repository.deleteEvent(eventId);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
