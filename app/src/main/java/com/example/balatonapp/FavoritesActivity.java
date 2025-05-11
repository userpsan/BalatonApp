package com.example.balatonapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.adapter.EventAdapter;
import com.example.balatonapp.adapter.SightAdapter;
import com.example.balatonapp.data.Event;
import com.example.balatonapp.data.Sight;
import com.google.firebase.auth.FirebaseAuth;
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

        db = FirebaseFirestore.getInstance();
        currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        setupRecyclerViews();
        loadFavorites();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Kedvencek");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerViews() {
        eventsAdapter = new EventAdapter();
        sightsAdapter = new SightAdapter();

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

                    for (var doc : queryDocumentSnapshots) {
                        String type = doc.getString("type");
                        if ("event".equals(type)) {
                            Event event = doc.toObject(Event.class);
                            favoriteEvents.add(event);
                        } else if ("sight".equals(type)) {
                            Sight sight = doc.toObject(Sight.class);
                            favoriteSights.add(sight);
                        }
                    }

                    eventsAdapter.submitList(new ArrayList<>(favoriteEvents));
                    sightsAdapter.submitList(new ArrayList<>(favoriteSights));

                    boolean isEmpty = favoriteEvents.isEmpty() && favoriteSights.isEmpty();
                    emptyView.setVisibility(isEmpty ? TextView.VISIBLE : TextView.GONE);
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
