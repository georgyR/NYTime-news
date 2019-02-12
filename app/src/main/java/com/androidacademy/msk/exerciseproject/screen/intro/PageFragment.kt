package com.androidacademy.msk.exerciseproject.screen.intro

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidacademy.msk.exerciseproject.R
import kotlinx.android.synthetic.main.fragment_page.*

class PageFragment : Fragment() {

    companion object {

        private const val KEY_IMAGE = "KEY_IMAGE"

        fun newInstance(@DrawableRes drawableRes: Int): PageFragment {
            val args = Bundle()
            args.putInt(KEY_IMAGE, drawableRes)

            val fragment = PageFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt(KEY_IMAGE)?.apply { imageview_page.setImageResource(this) }
    }
}
