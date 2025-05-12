package com.example.balatonapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.R;
import com.example.balatonapp.data.Sight;
import com.example.balatonapp.firestore.Callback;
import com.example.balatonapp.firestore.FirestoreService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SightAdapter extends RecyclerView.Adapter<SightAdapter.SightViewHolder> {

    private final List<Sight> sights = new ArrayList<>();
    private final FirestoreService firestoreService = new FirestoreService();
    private final Set<String> favoriteIds = new HashSet<>();
    private final boolean isFavoritesList;

    public SightAdapter(boolean isFavoritesList) {
        this.isFavoritesList = isFavoritesList;
    }

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

        // Kép beállítása
        int imageResId = context.getResources().getIdentifier(
                sight.getImageName(), "drawable", context.getPackageName());
        holder.image.setImageResource(imageResId != 0 ? imageResId : R.drawable.placeholder);

        // Kezdeti állapot: 3 sor
        holder.description.setMaxLines(3);
        holder.expandButton.setText(R.string.expand);

        holder.expandButton.setOnClickListener(v -> {
            boolean isCollapsed = holder.description.getMaxLines() == 3;

            if (isCollapsed) {
                holder.description.setMaxLines(Integer.MAX_VALUE);
                holder.expandButton.setText(R.string.collapse);
            } else {
                holder.description.setMaxLines(3);
                holder.expandButton.setText(R.string.expand);
            }

            // Kritikus: újrarajzolás kényszerítése
            holder.description.invalidate();
            holder.description.requestLayout();
        });



        String id = sight.getImageName();
        boolean isFavorite = favoriteIds.contains(id);

        if (isFavoritesList) {
            holder.favoriteButton.setText(R.string.remove_favorite);
            holder.favoriteButton.setEnabled(true);
            holder.favoriteButton.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));

            holder.favoriteButton.setOnClickListener(v -> {
                firestoreService.removeFavorite("sight", id);
                favoriteIds.remove(id);
                int pos = holder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    sights.remove(pos);
                    notifyItemRemoved(pos);
                }
            });
        } else {
            if (isFavorite) {
                holder.favoriteButton.setText(R.string.in_favorites);
                holder.favoriteButton.setEnabled(false);
                holder.favoriteButton.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
            } else {
                holder.favoriteButton.setText(R.string.add_favorite);
                holder.favoriteButton.setEnabled(true);
                holder.favoriteButton.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));

                holder.favoriteButton.setOnClickListener(v -> {
                    HashMap<String, Object> sightData = new HashMap<>();
                    sightData.put("title", sight.getName());
                    sightData.put("location", sight.getLocation());
                    sightData.put("description", sight.getDescription());
                    sightData.put("imageName", sight.getImageName());

                    firestoreService.addFavorite("sight", id, sightData);
                    favoriteIds.add(id);
                    notifyItemChanged(holder.getAdapterPosition());
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return sights.size();
    }

    public void submitList(List<Sight> newSights) {
        sights.clear();
        sights.addAll(newSights);

        firestoreService.getFavorites("sight", new Callback<Set<String>>() {
            @Override
            public void onResult(Set<String> favs) {
                favoriteIds.clear();
                favoriteIds.addAll(favs);
                notifyDataSetChanged();
            }
        });
    }

    static class SightViewHolder extends RecyclerView.ViewHolder {
        TextView name, location, description, expandButton;
        ImageView image;
        Button favoriteButton;

        public SightViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.sightTitle);
            location = itemView.findViewById(R.id.sightLocation);
            description = itemView.findViewById(R.id.sightDescription);
            expandButton = itemView.findViewById(R.id.btnExpand);
            image = itemView.findViewById(R.id.sightImage);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }
}
