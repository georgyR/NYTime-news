package com.androidacademy.msk.exerciseproject.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.androidacademy.msk.exerciseproject.R
import com.androidacademy.msk.exerciseproject.model.Section
import com.androidacademy.msk.exerciseproject.screen.newsdetails.NewsDetailsFragment
import com.androidacademy.msk.exerciseproject.screen.newslist.NewsListFragment
import com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.toolbar.ToolbarState
import com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.toolbar.ToolbarStateSwitcher
import com.androidacademy.msk.exerciseproject.utils.EnumUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsListFragment.ItemClickListener {

    companion object {

        private const val TAG_LIST = "TAG_LIST"
        private const val TAG_DETAILS = "TAG_DETAILS"

        @JvmStatic
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    private lateinit var toolbarStateSwitcher: ToolbarStateSwitcher
    private var isTwoPanel: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar_main)
        configureSpinner()

        isTwoPanel = findViewById<View>(R.id.framelayout_main_details) != null

        if (savedInstanceState == null) {
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.framelayout_main_list, NewsListFragment.newInstance(), TAG_LIST)
                    .addToBackStack(null)
                    .commit()
            return
        }


        val detailsFragment = supportFragmentManager.findFragmentByTag(TAG_DETAILS)
        if (detailsFragment == null) {
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER)
            return
        }
        supportFragmentManager.popBackStackImmediate()
        if (isTwoPanel) {
            replaceFragment(detailsFragment, R.id.framelayout_main_details)
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER)
        } else {
            replaceFragment(detailsFragment, R.id.framelayout_main_list)
            toolbarStateSwitcher.setToolbarState(ToolbarState.BACK_BUTTON_AND_TITLE)
        }
    }

    override fun onBackPressed() {
        if (isTwoPanel || supportFragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER)
            super.onBackPressed()
        }
    }

    override fun onNewItemClicked(id: Int) {
        val detailsFragment = supportFragmentManager.findFragmentByTag(TAG_DETAILS)
        if (detailsFragment != null) {
            supportFragmentManager.popBackStackImmediate()
        }

        if (isTwoPanel) {
            replaceFragment(NewsDetailsFragment.newInstance(id), R.id.framelayout_main_details)
            toolbarStateSwitcher.setToolbarState(ToolbarState.SPINNER)
        } else {
            replaceFragment(NewsDetailsFragment.newInstance(id), R.id.framelayout_main_list)
            toolbarStateSwitcher.setToolbarState(ToolbarState.BACK_BUTTON_AND_TITLE)
        }
    }


    private fun replaceFragment(detailsFragment: Fragment, @IdRes containerId: Int) {
        supportFragmentManager.beginTransaction()
                .replace(containerId, detailsFragment, TAG_DETAILS)
                .addToBackStack(null)
                .commit()
    }

    private fun configureSpinner() {
        val spinnerList = EnumUtils.convertEnumValuesToCapitalizedList(Section.values())
        val adapter = ArrayAdapter(this, R.layout.item_spinner, spinnerList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_newslist.adapter = adapter
        spinner_newslist.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val fragment = supportFragmentManager.findFragmentByTag(TAG_LIST)
                if (fragment != null) {
                    (fragment as NewsListFragment).spinnerItemClicked(id.toInt())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        supportActionBar?.apply { toolbarStateSwitcher = ToolbarStateSwitcher(this, spinner_newslist) }

    }
}
