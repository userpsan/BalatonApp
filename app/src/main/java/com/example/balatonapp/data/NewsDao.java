package com.example.balatonapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert
    void insert(News news);

    @Update
    void update(News news);

    @Delete
    void delete(News news);

    @Query("SELECT * FROM news ORDER BY date DESC")
    LiveData<List<News>> getAllNews();
}
