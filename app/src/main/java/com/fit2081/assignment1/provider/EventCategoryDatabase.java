package com.fit2081.assignment1.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fit2081.assignment1.EventCategory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {EventCategory.class}, version = 3)
public abstract class EventCategoryDatabase extends RoomDatabase {

    public static final String EVENT_CATEGORY_DATABASE = "event_category_database";

    public abstract EventCategoryDAO eventCategoryDAO();

    private static volatile EventCategoryDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    static EventCategoryDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EventCategoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EventCategoryDatabase.class, EVENT_CATEGORY_DATABASE)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


