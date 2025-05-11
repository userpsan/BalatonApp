package com.example.balatonapp.ui.news;

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

import com.example.balatonapp.HomeActivity;
import com.example.balatonapp.MainActivity;
import com.example.balatonapp.R;
import com.example.balatonapp.adapter.NewsAdapter;
import com.example.balatonapp.ui.events.EventsActivity;
import com.example.balatonapp.ui.sights.SightsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class NewsActivity extends AppCompatActivity {

    private NewsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewNews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewsAdapter adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        viewModel.getAllNews().observe(this, adapter::submitList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_home) {
            startActivity(new Intent(this, HomeActivity.class));
            return true;
        } else if (id == R.id.menu_events) {
            startActivity(new Intent(this, EventsActivity.class));
            return true;
        } else if (id == R.id.menu_sights) {
            startActivity(new Intent(this, SightsActivity.class));
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
