package com.example.balatonapp.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.FavoritesActivity;
import com.example.balatonapp.HomeActivity;
import com.example.balatonapp.MainActivity;
import com.example.balatonapp.R;
import com.example.balatonapp.adapter.EventAdapter;
import com.example.balatonapp.ui.news.NewsActivity;
import com.example.balatonapp.ui.sights.SightsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class EventsActivity extends AppCompatActivity {

    private EventViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Toolbar beállítás
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Programok");
        }

        // Egyéni menü ikon (jobb felső sarok)
        ImageView menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(this::showPopupMenu);

        // RecyclerView beállítás
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EventAdapter adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(EventViewModel.class);
        viewModel.getAllEvents().observe(this, adapter::submitList);
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_home) {
                startActivity(new Intent(this, HomeActivity.class));
            } else if (id == R.id.menu_sights) {
                startActivity(new Intent(this, SightsActivity.class));
            } else if (id == R.id.menu_events) {
                return true; // már itt vagyunk
            } else if (id == R.id.menu_news) {
                startActivity(new Intent(this, NewsActivity.class));
            } else if (id == R.id.menu_favorites) {
                startActivity(new Intent(this, FavoritesActivity.class));
            } else if (id == R.id.menu_logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            return true;
        });

        popup.show();
    }
}
