package com.example.balatonapp.ui.news;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.balatonapp.data.AppDatabase;
import com.example.balatonapp.data.News;
import com.example.balatonapp.data.NewsDao;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private final NewsDao newsDao;
    private final LiveData<List<News>> allNews;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        newsDao = db.newsDao();
        allNews = newsDao.getAllNews();
    }

    public LiveData<List<News>> getAllNews() {
        return allNews;
    }

    public void insert(News news) {
        AppDatabase.databaseWriteExecutor.execute(() -> newsDao.insert(news));
    }
}
