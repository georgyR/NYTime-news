package com.androidacademy.msk.exerciseproject.screen.intro

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.androidacademy.msk.exerciseproject.R
import com.androidacademy.msk.exerciseproject.di.DI
import com.androidacademy.msk.exerciseproject.screen.MainActivity
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : MvpAppCompatActivity(), IntroView {

    companion object {
        private const val NUM_PAGES = 3
    }

    @InjectPresenter
    lateinit var presenter: IntroPresenter

    @ProvidePresenter
    fun providePresenter(): IntroPresenter {
        return DI.openIntroScope().getInstance(IntroPresenter::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        DI.closeIntroScope()
    }

    override fun setLayout() {
        setContentView(R.layout.activity_intro)
        viewpager_intro.adapter = IntroPagerAdapter(supportFragmentManager)
        indicator_intro.setViewPager(viewpager_intro)

        button_intro_get_started.setOnClickListener { startNewsListActivity() }
    }

    override fun startNewsListActivity() {
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }

    private class IntroPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val drawableRes = when (position) {
                0 -> R.drawable.device_screenshot_newslist
                1 -> R.drawable.device_screenshot_details
                2 -> R.drawable.device_screenshot_about
                else -> R.drawable.device_screenshot_newslist
            }
            return PageFragment.newInstance(drawableRes)
        }

        override fun getCount(): Int {
            return NUM_PAGES
        }
    }
}
