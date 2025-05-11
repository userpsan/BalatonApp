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
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getLocation().equals(newItem.getLocation()) &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getImageName().equals(newItem.getImageName());
        }
    };

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = getItem(position);

        holder.title.setText(event.getTitle());
        holder.location.setText(event.getLocation());
        holder.date.setText(event.getDate());
        holder.description.setText(event.getDescription());

        // Kép beállítása
        int imageResId = holder.itemView.getContext()
                .getResources()
                .getIdentifier(event.getImageName(), "drawable", holder.itemView.getContext().getPackageName());
        holder.image.setImageResource(imageResId != 0 ? imageResId : R.drawable.placeholder);

        // Leírás bővítés/gomb szöveg
        holder.btnExpand.setOnClickListener(v -> {
            boolean isExpanded = holder.description.getMaxLines() == Integer.MAX_VALUE;
            holder.description.setMaxLines(isExpanded ? 3 : Integer.MAX_VALUE);
            holder.btnExpand.setText(isExpanded ? R.string.expand : R.string.collapse);
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
