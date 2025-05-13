package com.example.balatonapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface SightDao {

    @Insert
    void insert(Sight sight);

    @Update
    void update(Sight sight);

    @Query("SELECT * FROM sight_table ORDER BY name ASC")
    LiveData<List<Sight>> getAllSights();
}
