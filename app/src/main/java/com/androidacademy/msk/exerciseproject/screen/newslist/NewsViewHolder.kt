package com.androidacademy.msk.exerciseproject.screen.newslist

import android.support.v7.widget.RecyclerView
import android.view.View
import com.androidacademy.msk.exerciseproject.R
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.utils.DateUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_common_news.view.*

class NewsViewHolder(itemView: View,
                     listener: NewsAdapter.OnItemClickListener,
                     news: List<DbNewsItem>) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            val position = this.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(news[position].id!!)
            }
        }
    }

    fun bind(newsItem: DbNewsItem) {
        val section = newsItem.section
        itemView.textview_itemnews_category.text = section
        itemView.textview_itemnews_title.text = newsItem.title
        itemView.textview_itemnews_preview.text = newsItem.abstractX

        if (newsItem.publishedDate != null) {
            val publishDate = DateUtils.getSpecialFormattedDate(
                    newsItem.publishedDate!!,
                    itemView.context)
            itemView.textview_itemnews_date.text = publishDate
        }

        val previewImageUrl = newsItem.previewImageUrl
        if (previewImageUrl != null) {
            val context = itemView.context
            val requestOptions = RequestOptions().error(R.drawable.ic_image_blank)
            Glide.with(context).load(previewImageUrl).apply(requestOptions).into(itemView.imageview_itemnews)
        } else {
            itemView.imageview_itemnews.visibility = View.GONE
        }
    }
}