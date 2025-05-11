package com.example.balatonapp.ui.events;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.balatonapp.data.AppDatabase;
import com.example.balatonapp.data.Event;
import com.example.balatonapp.data.EventDao;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private final EventDao eventDao;
    private final LiveData<List<Event>> allEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public void insert(Event event) {
        AppDatabase.databaseWriteExecutor.execute(() -> eventDao.insert(event));
    }
}
