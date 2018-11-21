package com.androidacademy.msk.exerciseproject.screen.news_list;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.data.database.entity.DbNewsItem;
import com.androidacademy.msk.exerciseproject.di.Injector;
import com.androidacademy.msk.exerciseproject.model.Section;
import com.androidacademy.msk.exerciseproject.screen.ViewVisibilitySwitcher;
import com.androidacademy.msk.exerciseproject.screen.about.AboutActivity;
import com.androidacademy.msk.exerciseproject.screen.main_container.FragmentContainer;
import com.androidacademy.msk.exerciseproject.utils.EnumUtils;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import javax.inject.Inject;

import static com.androidacademy.msk.exerciseproject.screen.ScreenState.EMPTY;
import static com.androidacademy.msk.exerciseproject.screen.ScreenState.ERROR;
import static com.androidacademy.msk.exerciseproject.screen.ScreenState.HAS_DATA;
import static com.androidacademy.msk.exerciseproject.screen.ScreenState.LOADING;

public class NewsListFragment extends MvpAppCompatFragment implements NewsListView {

    private static final int MIN_WIDTH_IN_DP = 300;
    private static final String KEY_LIST_POSITION = "KEY_LIST_POSITION";
    private static final int TABLET_WIDTH = 720;

    @NonNull
    private RecyclerView recyclerView;
    @NonNull
    private ProgressBar progressBar;
    @NonNull
    private View errorView;
    @NonNull
    private View emptyListView;
    @NonNull
    private Button tryAgainButton;
    @NonNull
    private FloatingActionButton fab;
    @NonNull
    private Spinner spinner;
    @NonNull
    private NewsAdapter adapter;
    @NonNull
    private ViewVisibilitySwitcher visibilitySwitcher;
    @NonNull
    private ItemClickListener listener;
    @NonNull
    private FragmentContainer twoPanelActivity;
    private int position;

    @Inject
    @InjectPresenter
    public NewsListPresenter presenter;

    @ProvidePresenter
    NewsListPresenter providePresenter() {
        Injector.getInstance().getDbAndNetworkComponent().inject(this);
        return presenter;
    }

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            listener = (ItemClickListener) context;
        }

        if (context instanceof FragmentContainer) {
            twoPanelActivity = (FragmentContainer) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt(KEY_LIST_POSITION);
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = view.findViewById(R.id.progressbar_newslist);

        recyclerView = view.findViewById(R.id.recyclerview__newslist);
        errorView = view.findViewById(R.id.errorview_newslist);

        emptyListView = view.findViewById(R.id.viewempty_newslist);

        visibilitySwitcher = new ViewVisibilitySwitcher(
                recyclerView,
                progressBar,
                errorView,
                emptyListView);

        tryAgainButton = view.findViewById(R.id.button_viewerror_try_again);
        tryAgainButton.setOnClickListener(v -> presenter.onTryAgainButtonClicked());

        fab = view.findViewById(R.id.fab_newslist);
        fab.setOnClickListener(v -> presenter.onFabClicked());

        spinner = getActivity().findViewById(R.id.spinner_newslist);
        setupSpinner(spinner);

        return view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        setupRecyclerView(recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!twoPanelActivity.isTwoPanel()) {
            spinner.setVisibility(View.VISIBLE);
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        if (position != 0) {
            recyclerView.getLayoutManager().scrollToPosition(position);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.activity_news_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_open_about:
                startActivity(AboutActivity.getStartIntent(getContext()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        } else {
            position = ((StaggeredGridLayoutManager) layoutManager)
                    .findFirstCompletelyVisibleItemPositions(null)[0];
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_LIST_POSITION, position);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        twoPanelActivity = null;
    }

    @Override
    public void showNews(@NonNull List<DbNewsItem> news) {
        visibilitySwitcher.setUiState(HAS_DATA);

        adapter.addListData(news);
        recyclerView.scrollToPosition(0);
    }


    @Override
    public void showError() {
        visibilitySwitcher.setUiState(ERROR);
    }

    @Override
    public void showEmptyView() {
        visibilitySwitcher.setUiState(EMPTY);
    }

    @Override
    public void showProgressBar() {
        visibilitySwitcher.setUiState(LOADING);
    }

    @Override
    public void openDetailsScreen(int id) {
        listener.onNewItemClicked(id);
    }

    @Override
    public void setCurrentSectionInSpinner(int position) {
        spinner.setSelection(position);
    }

    public String getCurrentSelectedSection() {
        int position = spinner.getSelectedItemPosition();
        return Section.values()[position].name();
    }


    public void setSpinnerVisibility(int visibility) {
        spinner.setVisibility(visibility);
    }

    public interface ItemClickListener {
        void onNewItemClicked(int id);
    }


    private void setItemDecoration(@NonNull RecyclerView recyclerView) {
        DividerItemDecoration decoration
                = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);

        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        if (dividerDrawable != null) {
            decoration.setDrawable(dividerDrawable);
        }
        recyclerView.addItemDecoration(decoration);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fab.hide();
                } else if (dy < 0) {
                    fab.show();
                }
            }
        });

        setLayoutManager(recyclerView);
        setItemDecoration(recyclerView);
        NewsAdapter.OnItemClickListener clickListener = (id, position) -> presenter.onItemClicked(id);
        adapter = new NewsAdapter(clickListener, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutManager(@NonNull RecyclerView recyclerView) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidthInDp = displayMetrics.widthPixels / displayMetrics.density;

        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE &&
                screenWidthInDp >= TABLET_WIDTH || screenWidthInDp < MIN_WIDTH_IN_DP) {
            layoutManager = new LinearLayoutManager(getContext());
        } else {
            int snapCount = ((int) screenWidthInDp / MIN_WIDTH_IN_DP);
            layoutManager = new StaggeredGridLayoutManager(
                    snapCount,
                    StaggeredGridLayoutManager.VERTICAL);
        }

        recyclerView.setLayoutManager(layoutManager);
    }

    private void setupSpinner(@NonNull Spinner spinner) {
        List<String> spinnerList = EnumUtils.convertEnumValuesToCapitalizedList(Section.values());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_spinner, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        presenter.onSetupSpinnerPosition();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onSpinnerItemClicked(Section.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
