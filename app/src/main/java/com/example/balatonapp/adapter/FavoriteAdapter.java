package com.example.balatonapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.balatonapp.NotificationHelper;
import com.example.balatonapp.R;
import com.example.balatonapp.data.FavoriteItem;
import com.example.balatonapp.firestore.FirestoreService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<FavoriteItem> favorites = new ArrayList<>();
    private final Context context;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteItem item = favorites.get(position);

        holder.title.setText(item.getTitle());
        holder.location.setText(item.getLocation());
        holder.description.setText(item.getDescription());
        holder.noteInput.setText(item.getNote());
        holder.noteInput.setOnClickListener(v -> {
            holder.noteInput.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(holder.noteInput, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        // Kép beállítása drawable alapján
        int imageResId = context.getResources().getIdentifier(item.getImageName(), "drawable", context.getPackageName());
        if (imageResId != 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.placeholder);
        }

        // Megjegyzés mentése
        holder.saveNoteButton.setOnClickListener(v -> {
            String newNote = holder.noteInput.getText().toString().trim();
            item.setNote(newNote);
            new FirestoreService().updateNote(item.getItemId(), newNote);
            NotificationHelper.showNotification(context, "Kedvencek frissítve", item.getTitle() + " megjegyzése mentve.");
        });

        // Törlés a listából és Firestore-ból
        holder.deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Kedvenc törlése")
                    .setMessage("Biztosan eltávolítod a kedvencek közül?")
                    .setPositiveButton("Igen", (dialog, which) -> {
                        new FirestoreService().removeFavorite(item.getType(), item.getItemId());
                        favorites.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, favorites.size());
                    })
                    .setNegativeButton("Mégse", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public void submitList(List<FavoriteItem> items) {
        favorites = items;
        notifyDataSetChanged();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, description;
        EditText noteInput;
        ImageView image;
        Button saveNoteButton, deleteButton;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.favImage);
            title = itemView.findViewById(R.id.favTitle);
            location = itemView.findViewById(R.id.favLocation);
            description = itemView.findViewById(R.id.favDescription);
            noteInput = itemView.findViewById(R.id.favNoteInput);
            saveNoteButton = itemView.findViewById(R.id.saveNoteButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
