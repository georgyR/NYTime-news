package com.androidacademy.msk.exerciseproject.screen.news_list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import com.androidacademy.msk.exerciseproject.R
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem
import com.androidacademy.msk.exerciseproject.di.Injector
import com.androidacademy.msk.exerciseproject.model.Section
import com.androidacademy.msk.exerciseproject.screen.about.AboutActivity
import com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.screen.ScreenState.*
import com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.screen.ViewVisibilitySwitcher
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import javax.inject.Inject

private const val MIN_WIDTH_IN_DP = 300
private const val KEY_LIST_POSITION = "KEY_LIST_POSITION"
private const val TABLET_WIDTH = 720

fun newInstance() = NewsListFragment()

class NewsListFragment : MvpAppCompatFragment(), NewsListView  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorView: View
    private lateinit var emptyListView: View
    private lateinit var tryAgainButton: Button
    private lateinit var fab: FloatingActionButton
    private lateinit var spinner: Spinner
    private lateinit var adapter: NewsAdapter
    private lateinit var visibilitySwitcher: ViewVisibilitySwitcher
    private lateinit var listener: ItemClickListener
    private var position: Int = 0

    @Inject
    @InjectPresenter
    var presenter: NewsListPresenter? = null

    @ProvidePresenter
    internal fun providePresenter(): NewsListPresenter? {
        Injector.getInstance().dbAndNetworkComponent.inject(this)
        return presenter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = savedInstanceState?.getInt(KEY_LIST_POSITION) ?: 0
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)

        progressBar = view.findViewById(R.id.progressbar_newslist)
        recyclerView = view.findViewById(R.id.recyclerview__newslist)
        errorView = view.findViewById(R.id.errorview_newslist)
        emptyListView = view.findViewById(R.id.viewempty_newslist)

        visibilitySwitcher = ViewVisibilitySwitcher(
                recyclerView,
                progressBar,
                errorView,
                emptyListView)

        tryAgainButton = view.findViewById(R.id.button_viewerror_try_again)
        tryAgainButton.setOnClickListener { presenter?.onFabClicked() }

        fab = view.findViewById(R.id.fab_newslist)
        fab.setOnClickListener { presenter?.onFabClicked() }

        spinner = activity!!.findViewById(R.id.spinner_newslist)
        setupSpinner(spinner)

        return view
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupRecyclerView(recyclerView)
    }

    override fun onResume() {
        super.onResume()
        if (position != 0) {
            recyclerView.layoutManager!!.scrollToPosition(position)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuitem_open_about -> {
                startActivity(AboutActivity.getStartIntent(context!!))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()
        val layoutManager = recyclerView.layoutManager
        position = if (layoutManager is LinearLayoutManager) {
            layoutManager.findFirstCompletelyVisibleItemPosition()
        } else {
            (layoutManager as StaggeredGridLayoutManager)
                    .findFirstCompletelyVisibleItemPositions(null)[0]
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_LIST_POSITION, position)
    }

    override fun showNews(news: MutableList<DbNewsItem>) {
        visibilitySwitcher.setUiState(HAS_DATA)
        adapter.addListData(news)
        recyclerView.scrollToPosition(0)
    }

    override fun showError() {
        visibilitySwitcher.setUiState(ERROR)
    }

    override fun showEmptyView() {
        visibilitySwitcher.setUiState(EMPTY)
    }

    override fun showProgressBar() {
        visibilitySwitcher.setUiState(LOADING)
    }

    override fun openDetailsScreen(id: Int) {
        listener.onNewItemClicked(id)
    }

    override fun setCurrentSectionInSpinner(position: Int) {
        spinner.setSelection(position)
    }

    private fun setItemDecoration(recyclerView: RecyclerView) {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)

        val dividerDrawable = ContextCompat.getDrawable(context!!, R.drawable.divider)
        if (dividerDrawable != null) {
            decoration.setDrawable(dividerDrawable)
        }
        recyclerView.addItemDecoration(decoration)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    fab.hide()
                } else if (dy < 0) {
                    fab.show()
                }
            }
        })

        setLayoutManager(recyclerView)
        setItemDecoration(recyclerView)
        val clickListener =
                NewsAdapter.OnItemClickListener { id, position -> presenter?.onItemClicked(id) }
        adapter = NewsAdapter(clickListener, context!!)
        recyclerView.adapter = adapter
    }

    private fun setLayoutManager(recyclerView: RecyclerView) {
        val displayMetrics = resources.displayMetrics
        val screenWidthInDp = displayMetrics.widthPixels / displayMetrics.density

        val layoutManager: RecyclerView.LayoutManager
        layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
                screenWidthInDp >= TABLET_WIDTH || screenWidthInDp < MIN_WIDTH_IN_DP) {
            LinearLayoutManager(context)
        } else {
            val snapCount = screenWidthInDp.toInt() / MIN_WIDTH_IN_DP
            StaggeredGridLayoutManager(
                    snapCount,
                    StaggeredGridLayoutManager.VERTICAL)
        }

        recyclerView.layoutManager = layoutManager
    }

    private fun setupSpinner(spinner: Spinner) {
        presenter?.onSetupSpinnerPosition()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                presenter?.onSpinnerItemClicked(Section.values()[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    interface ItemClickListener {
        fun onNewItemClicked(id: Int)
    }
}