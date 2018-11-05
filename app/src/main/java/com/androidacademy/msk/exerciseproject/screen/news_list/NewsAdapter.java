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
import com.androidacademy.msk.exerciseproject.db.model.DbNewsItem;
import com.androidacademy.msk.exerciseproject.network.api.Section;
import com.androidacademy.msk.exerciseproject.utils.DateUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    @NonNull
    private final List<DbNewsItem> news = new ArrayList<>();
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
        try {
            Section section = Section.valueOf(news.get(position).getSection().toUpperCase());

            switch (section) {
                case TECHNOLOGY:
                    return R.layout.item_technology_news;
                default:
                    return R.layout.item_common_news;
            }
        } catch (IllegalArgumentException e) {
            return R.layout.item_common_news;

        }
    }

    public void addListData(List<DbNewsItem> newsItems) {
        news.clear();
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
                    listener.onItemClick(news.get(position).getId());
                }
            });
        }

        private void bind(@NonNull DbNewsItem newsItem) {
            String section = newsItem.getSection();
            categoryTextView.setText(section);
            titleTextView.setText(newsItem.getTitle());
            previewTextView.setText(newsItem.getAbstractX());

            String publishDate = null;
            if (newsItem.getPublishedDate() != null) {
                publishDate = DateUtils.convertTimestampToSpecialString(
                        newsItem.getPublishedDate(),
                        inflater.getContext());
            }
            publishDateTextView.setText(publishDate);

            String previewImageUrl = newsItem.getPreviewImageUrl();
            if (previewImageUrl != null) {
                Context context = imageView.getRootView().getContext();
                RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_image_blank);
                Glide.with(context).load(previewImageUrl).apply(requestOptions).into(imageView);
            } else {
                imageView.setVisibility(View.GONE);
            }

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }
}
