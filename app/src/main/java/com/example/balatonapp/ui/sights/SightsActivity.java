package com.example.balatonapp.ui.sights;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.FavoritesActivity;
import com.example.balatonapp.HomeActivity;
import com.example.balatonapp.MainActivity;
import com.example.balatonapp.R;
import com.example.balatonapp.adapter.SightAdapter;
import com.example.balatonapp.ui.events.EventsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SightsActivity extends AppCompatActivity {

    private SightAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Látnivalók");
        }


        ImageView menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(this::showPopupMenu);


        RecyclerView recyclerView = findViewById(R.id.recyclerViewSights);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SightAdapter(false);
        recyclerView.setAdapter(adapter);


        SightViewModel sightViewModel = new ViewModelProvider(this).get(SightViewModel.class);
        sightViewModel.getAllSights().observe(this, sights -> {
            if (sights != null) {
                adapter.submitList(sights);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_home) {
                startActivity(new Intent(this, HomeActivity.class));
            } else if (id == R.id.menu_sights) {
                return true;
            } else if (id == R.id.menu_events) {
                startActivity(new Intent(this, EventsActivity.class));
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
