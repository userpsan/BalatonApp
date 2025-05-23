package com.example.balatonapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.content.res.Configuration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.balatonapp.adapter.NewsAdapter;
import com.example.balatonapp.ui.events.EventsActivity;
import com.example.balatonapp.ui.news.NewsViewModel;
import com.example.balatonapp.ui.sights.SightsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Főoldal");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        ImageView menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(this::showPopupMenu);


        RecyclerView recyclerViewNews = findViewById(R.id.recyclerViewNews);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerViewNews.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        }

        newsAdapter = new NewsAdapter();
        recyclerViewNews.setAdapter(newsAdapter);

        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getAllNews().observe(this, newsList -> {
            if (newsList != null) {
                newsAdapter.submitList(newsList);
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.main_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_home) {
                return true;
            } else if (id == R.id.menu_events) {
                startActivity(new Intent(this, EventsActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else if (id == R.id.menu_sights) {
                startActivity(new Intent(this, SightsActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else if (id == R.id.menu_favorites) {
                startActivity(new Intent(this, FavoritesActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            } else if (id == R.id.menu_logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
            return true;
        });

        popup.show();
    }
}
