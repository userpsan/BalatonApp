package com.example.balatonapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.R;
import com.example.balatonapp.data.Event;

public class EventAdapter extends ListAdapter<Event, EventAdapter.EventViewHolder> {

    public EventAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Event> DIFF_CALLBACK = new DiffUtil.ItemCallback<Event>() {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.title.equals(newItem.title) &&
                    oldItem.location.equals(newItem.location) &&
                    oldItem.date.equals(newItem.date) &&
                    oldItem.description.equals(newItem.description) &&
                    oldItem.imageName.equals(newItem.imageName);
        }
    };

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = getItem(position);
        holder.title.setText(event.title);
        holder.location.setText(event.location);
        holder.date.setText(event.date);
        holder.description.setText(event.description);

        // Kép betöltése
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(
                event.imageName, "drawable", holder.itemView.getContext().getPackageName()
        );
        if (imageResId != 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.placeholder);
        }

        // "Tovább" gomb működése
        holder.btnExpand.setOnClickListener(v -> {
            boolean isExpanded = holder.description.getMaxLines() == Integer.MAX_VALUE;
            holder.description.setMaxLines(isExpanded ? 3 : Integer.MAX_VALUE);
            holder.btnExpand.setText(isExpanded ? "Tovább" : "Bezár");
        });
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, date, description, btnExpand;
        ImageView image;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eventTitle);
            location = itemView.findViewById(R.id.eventLocation);
            date = itemView.findViewById(R.id.eventDate);
            description = itemView.findViewById(R.id.eventDescription);
            btnExpand = itemView.findViewById(R.id.btnExpand);
            image = itemView.findViewById(R.id.eventImage);
        }
    }
}
