package com.androidacademy.msk.exerciseproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.data.DataUtils;
import com.androidacademy.msk.exerciseproject.data.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<NewsItem> newsItems;

    public NewsAdapter(@Nullable List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new NewsAdapter.ViewHolder(inflater.inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTextView;
        private TextView titleTextView;
        private TextView previewTextView;
        private TextView publishDateTextView;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.tv_category);
            titleTextView = itemView.findViewById(R.id.tv_title);
            previewTextView = itemView.findViewById(R.id.tv_preview);
            publishDateTextView = itemView.findViewById(R.id.tv_publish_date);
            imageView = itemView.findViewById(R.id.news_image);
        }

        private void bind(NewsItem newsItem) {
            categoryTextView.setText(newsItem.getCategory().getName());
            titleTextView.setText(newsItem.getTitle());
            previewTextView.setText(newsItem.getPreviewText());
            String publishDate = DataUtils.convertDateToString(newsItem.getPublishDate());
            publishDateTextView.setText(publishDate);
            Picasso.get().load(newsItem.getImageUrl()).into(imageView);
        }
    }
}
