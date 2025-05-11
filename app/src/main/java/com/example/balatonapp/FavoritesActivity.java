package com.example.balatonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.adapter.EventAdapter;
import com.example.balatonapp.adapter.SightAdapter;
import com.example.balatonapp.data.Event;
import com.example.balatonapp.data.Sight;
import com.example.balatonapp.firestore.Callback;
import com.example.balatonapp.firestore.FirestoreService;
import com.example.balatonapp.ui.events.EventViewModel;
import com.example.balatonapp.ui.news.NewsActivity;
import com.example.balatonapp.ui.sights.SightViewModel;
import com.example.balatonapp.ui.sights.SightsActivity;
import com.example.balatonapp.ui.events.EventsActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView sightsRecycler, eventsRecycler;
    private SightAdapter sightAdapter;
    private EventAdapter eventAdapter;
    private TextView emptyView;

    private FirestoreService firestoreService;
    private SightViewModel sightViewModel;
    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Toolbar beállítása
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kedvencek");
        }

        sightsRecycler = findViewById(R.id.sightsRecyclerView);
        eventsRecycler = findViewById(R.id.eventsRecyclerView);
        emptyView = findViewById(R.id.emptyView);

        firestoreService = new FirestoreService();
        sightViewModel = new ViewModelProvider(this).get(SightViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        sightAdapter = new SightAdapter();
        eventAdapter = new EventAdapter();

        sightsRecycler.setLayoutManager(new LinearLayoutManager(this));
        eventsRecycler.setLayoutManager(new LinearLayoutManager(this));

        sightsRecycler.setAdapter(sightAdapter);
        eventsRecycler.setAdapter(eventAdapter);

        loadFavorites();
    }

    private void loadFavorites() {
        firestoreService.getFavorites("sights", new Callback<Set<String>>() {
            @Override
            public void onResult(Set<String> sightIds) {
                sightViewModel.getAllSights().observe(FavoritesActivity.this, sights -> {
                    List<Sight> filtered = new ArrayList<>();
                    for (Sight s : sights) {
                        if (sightIds.contains(s.getImageName())) {
                            filtered.add(s);
                        }
                    }
                    sightAdapter.submitList(filtered);
                    toggleEmptyView(filtered, null);
                });
            }
        });

        firestoreService.getFavorites("events", new Callback<Set<String>>() {
            @Override
            public void onResult(Set<String> eventIds) {
                eventViewModel.getAllEvents().observe(FavoritesActivity.this, events -> {
                    List<Event> filtered = new ArrayList<>();
                    for (Event e : events) {
                        if (eventIds.contains(e.getImageName())) {
                            filtered.add(e);
                        }
                    }
                    eventAdapter.submitList(filtered);
                    toggleEmptyView(null, filtered);
                });
            }
        });
    }

    private void toggleEmptyView(List<Sight> sights, List<Event> events) {
        if ((sights != null && !sights.isEmpty()) || (events != null && !events.isEmpty())) {
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    // Menü megjelenítése
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return true;
    }

    // Menüelemek kezelése
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_home) {
            startActivity(new Intent(this, HomeActivity.class));
            return true;
        } else if (id == R.id.menu_sights) {
            startActivity(new Intent(this, SightsActivity.class));
            return true;
        } else if (id == R.id.menu_events) {
            startActivity(new Intent(this, EventsActivity.class));
            return true;
        } else if (id == R.id.menu_news) {
            startActivity(new Intent(this, NewsActivity.class));
            return true;
        } else if (id == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
