package com.example.balatonapp.ui.sights;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.balatonapp.data.AppDatabase;
import com.example.balatonapp.data.Sight;
import com.example.balatonapp.data.SightDao;

import java.util.List;

public class SightViewModel extends AndroidViewModel {

    private final SightDao sightDao;
    private final LiveData<List<Sight>> allSights;

    public SightViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        sightDao = db.sightDao();
        allSights = sightDao.getAllSights();
    }

    public LiveData<List<Sight>> getAllSights() {
        return allSights;
    }

    public void insert(Sight sight) {
        AppDatabase.databaseWriteExecutor.execute(() -> sightDao.insert(sight));
    }
}
