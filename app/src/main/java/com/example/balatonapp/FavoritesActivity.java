package com.example.balatonapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.adapter.EventAdapter;
import com.example.balatonapp.adapter.SightAdapter;
import com.example.balatonapp.data.Event;
import com.example.balatonapp.data.FavoriteItem;
import com.example.balatonapp.data.Sight;
import com.example.balatonapp.ui.events.EventsActivity;
import com.example.balatonapp.ui.sights.SightsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
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

        setupToolbarAndMenu();

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

    private void setupToolbarAndMenu() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kedvencek");
        }

        ImageView menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(this::showPopupMenu);
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_home) {
                startActivity(new Intent(this, HomeActivity.class));
            } else if (id == R.id.menu_events) {
                startActivity(new Intent(this, EventsActivity.class));
            } else if (id == R.id.menu_sights) {
                startActivity(new Intent(this, SightsActivity.class));
            } else if (id == R.id.menu_favorites) {
                return true; // már itt vagyunk
            } else if (id == R.id.menu_logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            return true;
        });

        popup.show();
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
        db.collection("favorites_" + currentUserUid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    favoriteEvents.clear();
                    favoriteSights.clear();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        FavoriteItem item = doc.toObject(FavoriteItem.class);
                        if (item == null || item.getType() == null) continue;

                        if (item.getType().equals("event")) {
                            Date date = item.getDate();
                            String formattedDate = date != null
                                    ? DateFormat.format("yyyy.MM.dd", date).toString()
                                    : "";

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
}
