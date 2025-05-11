package com.example.balatonapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balatonapp.R;
import com.example.balatonapp.data.News;

public class NewsAdapter extends ListAdapter<News, NewsAdapter.NewsViewHolder> {

    public NewsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<News> DIFF_CALLBACK = new DiffUtil.ItemCallback<News>() {
        @Override
        public boolean areItemsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.getImageName().equals(newItem.getImageName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
                    && oldItem.getExcerpt().equals(newItem.getExcerpt())
                    && oldItem.getLink().equals(newItem.getLink());
        }
    };

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = getItem(position);
        Context context = holder.itemView.getContext();

        holder.title.setText(news.getTitle());
        holder.excerpt.setText(news.getExcerpt());

        // Kép betöltése drawable-ból
        int imageResId = context.getResources().getIdentifier(news.getImageName(), "drawable", context.getPackageName());
        if (imageResId != 0) {
            holder.image.setImageResource(imageResId);
        } else {
            holder.image.setImageResource(R.drawable.placeholder);
        }

        holder.readMore.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.getLink()));
            context.startActivity(browserIntent);
        });
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, excerpt;
        ImageView image;
        Button readMore;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.newsTitle);
            excerpt = itemView.findViewById(R.id.newsExcerpt);
            image = itemView.findViewById(R.id.newsImage);
            readMore = itemView.findViewById(R.id.newsButton);
        }
    }
}
