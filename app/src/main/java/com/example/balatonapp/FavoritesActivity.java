package com.example.balatonapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.adapter.FavoriteAdapter;
import com.example.balatonapp.data.FavoriteItem;
import com.example.balatonapp.ui.events.EventsActivity;
import com.example.balatonapp.ui.sights.SightsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private TextView emptyView;
    private FavoriteAdapter favoriteAdapter;
    private final List<FavoriteItem> favoriteItems = new ArrayList<>();
    private FirebaseFirestore db;
    private String currentUserUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        setupToolbarAndMenu();

        emptyView = findViewById(R.id.emptyView);
        RecyclerView favoritesRecyclerView = findViewById(R.id.eventsRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoriteAdapter = new FavoriteAdapter(this);
        favoritesRecyclerView.setAdapter(favoriteAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            finish();
            return;
        }

        currentUserUid = currentUser.getUid();
        db = FirebaseFirestore.getInstance();

        scheduleDailyReminder();
        loadFavorites();
        findViewById(R.id.btnAllFavorites).setOnClickListener(v -> loadFavorites());
        findViewById(R.id.btnOnlySights).setOnClickListener(v -> loadOnlySights());
        findViewById(R.id.btnOnlyWithNotes).setOnClickListener(v -> loadOnlyWithNotes());

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }
    private void scheduleDailyReminder() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
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
                return true;
            } else if (id == R.id.menu_logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            return true;
        });

        popup.show();
    }

    private void loadFavorites() {
        db.collection("favorites_" + currentUserUid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    favoriteItems.clear();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        FavoriteItem item = doc.toObject(FavoriteItem.class);
                        if (item != null) {
                            favoriteItems.add(item);
                        }
                    }

                    favoriteAdapter.submitList(new ArrayList<>(favoriteItems));

                    if (favoriteItems.isEmpty()) {
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
    private void loadOnlySights() {
        db.collection("favorites_" + currentUserUid)
                .whereEqualTo("type", "sight")
                .orderBy("title")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    favoriteItems.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        FavoriteItem item = doc.toObject(FavoriteItem.class);
                        if (item != null) {
                            favoriteItems.add(item);
                        }
                    }

                    favoriteAdapter.submitList(new ArrayList<>(favoriteItems));

                    emptyView.setVisibility(favoriteItems.isEmpty() ? View.VISIBLE : View.GONE);
                })
                .addOnFailureListener(e -> {
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText(R.string.error_fetching_favorites);
                });
    }
    @SuppressLint("SetTextI18n")
    private void loadOnlyWithNotes() {
        db.collection("favorites_" + currentUserUid)
                .whereGreaterThan("note", "")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    favoriteItems.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        FavoriteItem item = doc.toObject(FavoriteItem.class);
                        if (item != null) {
                            favoriteItems.add(item);
                        }
                    }

                    favoriteAdapter.submitList(new ArrayList<>(favoriteItems));

                    emptyView.setVisibility(favoriteItems.isEmpty() ? View.VISIBLE : View.GONE);
                    if (favoriteItems.isEmpty()) {
                        emptyView.setText("Nincs megjegyzéssel ellátott kedvenc.");
                    }
                })
                .addOnFailureListener(e -> {
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setText("Hiba történt a megjegyzések lekérdezésekor.");
                });
    }

}
