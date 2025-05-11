package com.example.balatonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.adapter.NewsAdapter;
import com.example.balatonapp.ui.events.EventsActivity;
import com.example.balatonapp.ui.news.NewsViewModel;
import com.example.balatonapp.ui.sights.SightsActivity;

public class HomeActivity extends AppCompatActivity {

    private NewsViewModel newsViewModel;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewNews);
        if (recyclerView == null) {
            throw new IllegalStateException("recyclerViewNews not found in layout. Ellenőrizd az activity_home.xml fájlt!");
        }

        recyclerView = findViewById(R.id.recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getAllNews().observe(this, adapter::submitList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_favorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
            return true;
        } else if (id == R.id.menu_sights) {
            startActivity(new Intent(this, SightsActivity.class));
            return true;
        } else if (id == R.id.menu_events) {
            startActivity(new Intent(this, EventsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
