package com.example.balatonapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.R;
import com.example.balatonapp.data.Sight;
import com.example.balatonapp.firestore.Callback;
import com.example.balatonapp.firestore.FirestoreService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SightAdapter extends RecyclerView.Adapter<SightAdapter.SightViewHolder> {

    private final List<Sight> sights = new ArrayList<>();
    private final FirestoreService firestoreService = new FirestoreService();
    private final Set<String> favoriteIds = new HashSet<>();

    @NonNull
    @Override
    public SightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sight, parent, false);
        return new SightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SightViewHolder holder, int position) {
        Sight sight = sights.get(position);
        Context context = holder.itemView.getContext();

        holder.name.setText(sight.getName());
        holder.location.setText(sight.getLocation());
        holder.description.setText(sight.getDescription());

        // Kép betöltése drawable-ból
        int imageResId = context.getResources().getIdentifier(
                sight.getImageName(), "drawable", context.getPackageName());
        if (imageResId != 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.placeholder);
        }

        // Gomb szöveg frissítése
        boolean isFav = favoriteIds.contains(sight.getImageName());
        holder.favoriteButton.setText(isFav ? "Eltávolítás" : "Kedvenc");

        holder.favoriteButton.setOnClickListener(v -> {
            String id = sight.getImageName();
            if (favoriteIds.contains(id)) {
                firestoreService.removeFavorite("sights", id);
                favoriteIds.remove(id);
                holder.favoriteButton.setText("Kedvenc");
            } else {
                firestoreService.addFavorite("sights", id);
                favoriteIds.add(id);
                holder.favoriteButton.setText("Eltávolítás");
            }
        });
    }

    @Override
    public int getItemCount() {
        return sights.size();
    }

    public void submitList(List<Sight> newSights) {
        sights.clear();
        sights.addAll(newSights);

        firestoreService.getFavorites("sights", new Callback<Set<String>>() {
            @Override
            public void onResult(Set<String> favs) {
                favoriteIds.clear();
                favoriteIds.addAll(favs);
                notifyDataSetChanged();
            }
        });
    }

    static class SightViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, description;
        ImageView image;
        Button favoriteButton;

        public SightViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sightTitle);
            location = itemView.findViewById(R.id.sightLocation);
            description = itemView.findViewById(R.id.sightDescription);
            image = itemView.findViewById(R.id.sightImage);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }

}
