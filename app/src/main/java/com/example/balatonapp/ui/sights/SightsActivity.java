package com.example.balatonapp.ui.sights;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.FavoritesActivity;
import com.example.balatonapp.MainActivity;
import com.example.balatonapp.R;
import com.example.balatonapp.adapter.SightAdapter;
import com.example.balatonapp.ui.events.EventsActivity;
import com.example.balatonapp.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SightsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SightAdapter adapter;
    private SightViewModel sightViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sights);

        setupToolbar();

        recyclerView = findViewById(R.id.recyclerViewSights);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SightAdapter(false);
        recyclerView.setAdapter(adapter);

        sightViewModel = new ViewModelProvider(this).get(SightViewModel.class);
        sightViewModel.getAllSights().observe(this, sights -> {
            if (sights != null) {
                adapter.submitList(sights);
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.sights);
        }
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
            startActivity(new Intent(this, EventsActivity.class));
            finish();
            return true;
        } else if (id == R.id.menu_favorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
            finish();
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
