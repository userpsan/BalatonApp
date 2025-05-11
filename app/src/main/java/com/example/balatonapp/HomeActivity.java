package com.example.balatonapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.balatonapp.ui.events.EventsActivity;
import com.example.balatonapp.ui.news.NewsActivity;
import com.example.balatonapp.ui.sights.SightsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupToolbar();
        animateHeader();
        setupLogoutButton();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");  // Üres cím
        }
    }

    private void animateHeader() {
        ImageView logo = findViewById(R.id.headerImage);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(fadeIn);
    }

    private void setupLogoutButton() {
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> logoutUser());
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_sights) {
            startActivity(new Intent(this, SightsActivity.class));
            return true;
        } else if (id == R.id.menu_events) {
            startActivity(new Intent(this, EventsActivity.class));
            return true;
        } else if (id == R.id.menu_news) {
            startActivity(new Intent(this, NewsActivity.class));
            return true;
        } else if (id == R.id.menu_favorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
            return true;
        } else if (id == R.id.menu_logout) {
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
