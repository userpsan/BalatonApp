package com.example.balatonapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.adapter.EventAdapter;
import com.example.balatonapp.adapter.SightAdapter;
import com.example.balatonapp.data.Event;
import com.example.balatonapp.data.FavoriteItem;
import com.example.balatonapp.data.Sight;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView eventsRecyclerView;
    private RecyclerView sightsRecyclerView;
    private TextView emptyView;

    private EventAdapter eventsAdapter;
    private SightAdapter sightsAdapter;

    private final List<Event> favoriteEvents = new ArrayList<>();
    private final List<Sight> favoriteSights = new ArrayList<>();

    private FirebaseFirestore db;
    private String currentUserUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        setupToolbar();

        emptyView = findViewById(R.id.emptyView);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        sightsRecyclerView = findViewById(R.id.sightsRecyclerView);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            finish();
            return;
        }

        currentUserUid = currentUser.getUid();
        db = FirebaseFirestore.getInstance();

        setupRecyclerViews();
        loadFavorites();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.menu_favorites);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerViews() {
        eventsAdapter = new EventAdapter();
        sightsAdapter = new SightAdapter(true);

        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsRecyclerView.setAdapter(eventsAdapter);

        sightsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sightsRecyclerView.setAdapter(sightsAdapter);
    }

    private void loadFavorites() {
        db.collection("favorites")
                .whereEqualTo("userId", currentUserUid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    favoriteEvents.clear();
                    favoriteSights.clear();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        FavoriteItem item = doc.toObject(FavoriteItem.class);
                        if (item == null || item.getType() == null) continue;

                        if (item.getType().equals("event")) {
                            // Konvertáljuk a dátumot (long -> String)
                            String formattedDate = DateFormat.format("yyyy.MM.dd", item.getDate()).toString();

                            Event event = new Event(
                                    item.getTitle(),
                                    item.getDescription(),
                                    formattedDate,
                                    item.getLocation(),
                                    item.getImageName()
                            );
                            favoriteEvents.add(event);

                        } else if (item.getType().equals("sight")) {
                            Sight sight = new Sight(
                                    item.getTitle(),
                                    item.getDescription(),
                                    item.getLocation(),
                                    item.getImageName()
                            );
                            favoriteSights.add(sight);
                        }
                    }

                    eventsAdapter.submitList(new ArrayList<>(favoriteEvents));
                    sightsAdapter.submitList(new ArrayList<>(favoriteSights));

                    if (favoriteEvents.isEmpty() && favoriteSights.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        emptyView.setText(R.string.no_favorites);
                    } else {
                        emptyView.setVisibility(View.GONE);
                    }
                })
                .addOnFailureListener(e -> {
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText(R.string.error_fetching_favorites);
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_home) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_events) {
            startActivity(new Intent(this, com.example.balatonapp.ui.events.EventsActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_sights) {
            startActivity(new Intent(this, com.example.balatonapp.ui.sights.SightsActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_favorites) {
            return true; // már itt vagyunk
        } else if (id == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
