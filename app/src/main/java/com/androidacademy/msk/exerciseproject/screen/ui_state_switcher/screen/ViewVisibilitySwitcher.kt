package com.androidacademy.msk.exerciseproject.screen.ui_state_switcher.screen

import android.view.View

class ViewVisibilitySwitcher(
        private val dataView: View,
        private val loadingIndicatorView: View,
        private val errorView: View,
        private val emptyListView: View
) {

    fun setUiState(state: ScreenState) {
        val view = when (state) {
            ScreenState.HAS_DATA -> dataView
            ScreenState.LOADING -> loadingIndicatorView
            ScreenState.ERROR -> errorView
            ScreenState.EMPTY -> emptyListView
        }

        show(view)
    }

    private fun show(targetView: View) {
        val viewArray = arrayOf(dataView, loadingIndicatorView, errorView, emptyListView)
        var isViewFound = false
        viewArray.forEach { view ->
            if (!isViewFound && view.id == targetView.id) {
                view.visibility = View.VISIBLE
                isViewFound = true
            } else {
                view.visibility = View.GONE
            }
        }
    }
}
