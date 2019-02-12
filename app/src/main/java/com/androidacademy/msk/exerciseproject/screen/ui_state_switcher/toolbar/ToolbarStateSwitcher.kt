package com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.toolbar

import android.support.v7.app.ActionBar
import android.view.View
import android.widget.Spinner

import com.androidacademy.msk.exerciseproject.model.Section

class ToolbarStateSwitcher(private val actionBar: ActionBar, private val spinner: Spinner) {

    fun setToolbarState(state: ToolbarState) {
        when (state) {
            ToolbarState.SPINNER -> {
                actionBar.setDisplayShowTitleEnabled(false)
                spinner.visibility = View.VISIBLE
                actionBar.setDisplayHomeAsUpEnabled(false)
            }
            ToolbarState.BACK_BUTTON_AND_TITLE -> {
                val position = spinner.selectedItemPosition
                var section = Section.values()[position].name
                section = section.toLowerCase().capitalize()
                actionBar.title = section
                actionBar.setDisplayShowTitleEnabled(true)
                spinner.visibility = View.GONE
                actionBar.setDisplayHomeAsUpEnabled(true)
            }
        }
    }
}
