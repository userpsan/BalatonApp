package com.example.balatonapp.ui.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.FavoritesActivity;
import com.example.balatonapp.HomeActivity;
import com.example.balatonapp.MainActivity;
import com.example.balatonapp.R;
import com.example.balatonapp.adapter.EventAdapter;
import com.example.balatonapp.ui.sights.SightsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class EventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Programok");
        }

        ImageView menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(this::showPopupMenu);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewEvents);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        EventAdapter adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);

        EventViewModel viewModel = new ViewModelProvider(this).get(EventViewModel.class);
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
                return true;
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
