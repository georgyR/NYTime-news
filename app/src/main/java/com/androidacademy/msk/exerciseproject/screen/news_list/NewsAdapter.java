package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.androidacademy.msk.exerciseproject.data.Category;
import com.androidacademy.msk.exerciseproject.data.model.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    @NonNull
    private List<NewsItem> news = new ArrayList<>();
    @NonNull
    private final OnItemClickListener clickListener;
    @NonNull
    private final LayoutInflater inflater;

    public NewsAdapter(@NonNull OnItemClickListener clickListener,
                       @NonNull Context context) {
        this.clickListener = clickListener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(viewType, parent, false),
                clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public int getItemViewType(int position) {
        Category category = news.get(position).getCategory();

        switch (category) {
            case CRIMINAL:
                return R.layout.item_criminal_news;
            default:
                return R.layout.item_common_news;
        }
    }

    public void addListData(List<NewsItem> newsItems) {
        news.addAll(newsItems);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView categoryTextView;
        private final TextView titleTextView;
        private final TextView previewTextView;
        private final TextView publishDateTextView;
        private final ImageView imageView;

        ViewHolder(@NonNull View itemView, @NonNull OnItemClickListener listener) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.item_news__textview_category);
            titleTextView = itemView.findViewById(R.id.item_news__textview_title);
            previewTextView = itemView.findViewById(R.id.item_news__textview_preview);
            publishDateTextView = itemView.findViewById(R.id.item_news__textview_publish_date);
            imageView = itemView.findViewById(R.id.item_news__imageview_news);

            itemView.setOnClickListener(v -> {
                int position = ViewHolder.this.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position);
                }
            });
        }

        private void bind(@NonNull NewsItem newsItem) {
            categoryTextView.setText(newsItem.getCategory().getName());
            titleTextView.setText(newsItem.getTitle());
            previewTextView.setText(newsItem.getPreviewText());
            String publishDate = DateUtils.convertDateToString(newsItem.getPublishDate(), inflater.getContext());
            publishDateTextView.setText(publishDate);
            Picasso.get().load(newsItem.getImageUrl()).into(imageView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
