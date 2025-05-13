package com.example.balatonapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {

    @Query("SELECT * FROM event_table ORDER BY date ASC")
    LiveData<List<Event>> getAllEvents();

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);
}