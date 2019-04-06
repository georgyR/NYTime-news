package com.androidacademy.msk.exerciseproject.screen.newslist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.androidacademy.msk.exerciseproject.R
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.model.Section
import com.androidacademy.msk.exerciseproject.utils.SectionUtils

class NewsAdapter(
        private val clickListener: (Int) -> Unit,
        context: Context
) : RecyclerView.Adapter<NewsViewHolder>() {

    private val news = mutableListOf<DbNewsItem>()
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
            NewsViewHolder(
                    inflater.inflate(viewType, parent, false),
                    clickListener,
                    news)

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) = holder.bind(news[position])

    override fun getItemViewType(position: Int): Int {
        return try {
            val section =  SectionUtils.getSection(news[position].section)
            when (section) {
                Section.TECHNOLOGY -> R.layout.item_technology_news
                else -> R.layout.item_common_news
            }
        } catch (e: IllegalArgumentException) {
            R.layout.item_common_news
        }
    }

    fun addListData(newsItems: List<DbNewsItem>) {
        news.clear()
        news.addAll(newsItems)
        notifyDataSetChanged()
    }
}