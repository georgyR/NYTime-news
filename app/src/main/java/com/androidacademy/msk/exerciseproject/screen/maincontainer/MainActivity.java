package com.androidacademy.msk.exerciseproject.screen.maincontainer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.model.Section;
import com.androidacademy.msk.exerciseproject.screen.newsdetails.NewsDetailsFragment;
import com.androidacademy.msk.exerciseproject.screen.newslist.NewsListFragment;
import com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.toolbar.ToolbarState;
import com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.toolbar.ToolbarStateSwitcher;
import com.androidacademy.msk.exerciseproject.utils.EnumUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NewsListFragment.ItemClickListener {

    private static final String TAG_LIST = "TAG_LIST";
    private static final String TAG_DETAILS = "TAG_DETAILS";

    public boolean isTwoPanel;
    @NonNull
    private FragmentManager fragmentManager;
    @NonNull
    private ToolbarStateSwitcher toolbarStateSwitcher;
    private Spinner spinner;

    public static Intent getStartIntent(@NonNull Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        spinner = getSpinner(toolbar);

        toolbarStateSwitcher = new ToolbarStateSwitcher(getSupportActionBar(), spinner);

        isTwoPanel = findViewById(R.id.framelayout_main_details) != null;

        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER);
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout_main_list, NewsListFragment.Companion.newInstance(), TAG_LIST)
                    .addToBackStack(null)
                    .commit();
            return;
        }

        Fragment detailsFragment = fragmentManager.findFragmentByTag(TAG_DETAILS);
        if (detailsFragment == null) {
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER);
            return;
        }
        fragmentManager.popBackStackImmediate();
        if (isTwoPanel) {
            replaceFragment(detailsFragment, R.id.framelayout_main_details);
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER);
        } else {
            replaceFragment(detailsFragment, R.id.framelayout_main_list);
            toolbarStateSwitcher.setToolbarState(ToolbarState.BACK_BUTTON_AND_TITLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (isTwoPanel) {
            finish();
        }
        if (fragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER);
            super.onBackPressed();
        }
    }


    @Override
    public void onNewItemClicked(int id) {
        Fragment detailsFragment = fragmentManager.findFragmentByTag(TAG_DETAILS);
        if (detailsFragment != null) {
            fragmentManager.popBackStackImmediate();
        }

        if (isTwoPanel) {
            replaceFragment(NewsDetailsFragment.newInstance(id), R.id.framelayout_main_details);
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER);
        } else {
            replaceFragment(NewsDetailsFragment.newInstance(id), R.id.framelayout_main_list);
            toolbarStateSwitcher.setToolbarState(ToolbarState.BACK_BUTTON_AND_TITLE);
        }
    }


    private void replaceFragment(@NonNull Fragment detailsFragment, @IdRes int containerId) {
        fragmentManager.beginTransaction()
                .replace(containerId, detailsFragment, TAG_DETAILS)
                .addToBackStack(null)
                .commit();
    }

    @NonNull
    private Spinner getSpinner(@NonNull Toolbar toolbar) {
        setSupportActionBar(toolbar);
        Spinner spinner = findViewById(R.id.spinner_newslist);
        List<String> spinnerList = EnumUtils.convertEnumValuesToCapitalizedList(Section.values());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_spinner, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NewsListFragment fragment = (NewsListFragment) fragmentManager.findFragmentByTag(TAG_LIST);
                if (fragment != null) {
                    fragment.spinnerItemClicked((int) id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return spinner;
    }
}
