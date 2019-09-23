package com.androidacademy.msk.exerciseproject.screen.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import com.androidacademy.msk.exerciseproject.R
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.di.DI
import com.androidacademy.msk.exerciseproject.di.model.NewsId
import com.androidacademy.msk.exerciseproject.utils.DateUtils
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_news_details.*

class NewsDetailsFragment : MvpAppCompatFragment(), NewsDetailsView {

    companion object {

        private const val KEY_ID = "KEY_ID"

        fun newInstance(id: Int): NewsDetailsFragment {
            val args = Bundle()
            args.putInt(KEY_ID, id)

            val fragment = NewsDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var presenter: NewsDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): NewsDetailsPresenter {

        val id = NewsId(arguments?.getInt(KEY_ID) ?: 0)
        return DI.openNewsDetailsScope(id).getInstance(NewsDetailsPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        DI.closeNewsDetailsScope()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem?): Boolean {
        if (menuItem?.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(menuItem)
    }


    override fun showNewsDetails(newsItem: DbNewsItem) {
        textview_newsdetails_title.text = newsItem.title
        if (newsItem.fullsizeImageUrl.isNotEmpty()) {
            Glide.with(this).load(newsItem.fullsizeImageUrl).into(imageview_newsdetails)
        } else {
            imageview_newsdetails.visibility = View.GONE
        }
        textview_newsdetails_abstract.text = newsItem.abstractX
        val publishedDate = newsItem.publishedDate
        if (publishedDate.isNotEmpty()) {
            val date = DateUtils.getFormattedDate(publishedDate)
            textview_newsdetails_date.text = date
            context?.let {
                val time = DateUtils.getFormattedTime(publishedDate, it)
                textview_newsdetails_time.text = time
            }
        }
    }
}
