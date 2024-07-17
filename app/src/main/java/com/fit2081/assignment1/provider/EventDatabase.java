package com.fit2081.assignment1.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fit2081.assignment1.Event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Event.class}, version =1)
public abstract class EventDatabase extends RoomDatabase {

    public static final String EVENT_DATABASE = "event_database";

    public abstract EventDAO eventDAO();

    private static volatile EventDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static EventDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventCategoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    EventDatabase.class, EVENT_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
