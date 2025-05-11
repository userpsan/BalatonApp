package com.example.balatonapp.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.balatonapp.data.NewsDao;
import com.example.balatonapp.data.EventDao;
import com.example.balatonapp.data.SightDao;
import com.example.balatonapp.data.News;
import com.example.balatonapp.data.Event;
import com.example.balatonapp.data.Sight;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {News.class, Event.class, Sight.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();
    public abstract EventDao eventDao();
    public abstract SightDao sightDao();

    private static volatile AppDatabase INSTANCE;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "balaton_database"
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    databaseWriteExecutor.execute(() -> {
                                        AppDatabase database = getDatabase(context);
                                        DataInitializer.populate(database); // csak ha létezik
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
