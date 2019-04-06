package com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.toolbar

import android.support.v7.app.ActionBar
import android.view.View
import android.widget.Spinner

import com.androidacademy.msk.exerciseproject.model.Section
import com.androidacademy.msk.exerciseproject.utils.SectionUtils

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
                val selectedSection = Section.values()[position]
                val section = SectionUtils.getCapitalizedSectionName(selectedSection)
                actionBar.title = section
                actionBar.setDisplayShowTitleEnabled(true)
                spinner.visibility = View.GONE
                actionBar.setDisplayHomeAsUpEnabled(true)
            }
        }
    }
}
