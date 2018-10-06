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

    private static final int COMMON_NEWS = 0;
    private static final int CRIMINAL_NEWS = 1;
    private List<NewsItem> newsItems;
    private OnItemClickListener clickListener;

    public NewsAdapter(@Nullable List<NewsItem> newsItems, OnItemClickListener clickListener) {
        this.newsItems = newsItems;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == CRIMINAL_NEWS) {
            return new ViewHolder(
                    inflater.inflate(R.layout.item_criminal_news, parent, false),
                    clickListener);
        } else {
            return new ViewHolder(
                    inflater.inflate(R.layout.item_common_news, parent, false),
                    clickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    @Override
    public int getItemViewType(int position) {

        String category = newsItems.get(position).getCategory().getName();
        if (category.equals(DataUtils.CRIMINAL)) {
            return CRIMINAL_NEWS;
        } else {
            return COMMON_NEWS;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryTextView;
        private TextView titleTextView;
        private TextView previewTextView;
        private TextView publishDateTextView;
        private ImageView imageView;

        ViewHolder(@NonNull View itemView, @NonNull OnItemClickListener listener) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.tv_category);
            titleTextView = itemView.findViewById(R.id.tv_title);
            previewTextView = itemView.findViewById(R.id.tv_preview);
            publishDateTextView = itemView.findViewById(R.id.tv_publish_date);
            imageView = itemView.findViewById(R.id.news_image);

            itemView.setOnClickListener(v -> {
                int position = ViewHolder.this.getAdapterPosition();
                listener.onItemClick(position);
            });
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
