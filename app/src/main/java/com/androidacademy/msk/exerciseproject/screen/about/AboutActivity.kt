package com.androidacademy.msk.exerciseproject.screen.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.androidacademy.msk.exerciseproject.R
import com.androidacademy.msk.exerciseproject.data.network.SocialNetworkApp
import com.androidacademy.msk.exerciseproject.model.AppError
import com.androidacademy.msk.exerciseproject.utils.IntentUtils
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.view_profile.*

class AboutActivity : MvpAppCompatActivity(), AboutView {

    companion object {

        private const val EMAIL = "georgy.ryabykh@gmail.com"
        private const val PHONE_NUMBER = "+79165766299"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, AboutActivity::class.java)
        }
    }

    private val intentUtils: IntentUtils = IntentUtils(this)

    @InjectPresenter
    lateinit var presenter: AboutPresenter

    private lateinit var rootView: View
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_about)

        val toolbar = findViewById<Toolbar>(R.id.toolbar_all)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        rootView = findViewById(android.R.id.content)
        linearLayout = findViewById(R.id.linearlayout_viewprofile)

        button_viewprofile_send_message.setOnClickListener { presenter.onSendMessageButtonClicked() }

        button_viewprofile_send_email.setOnClickListener { presenter.onEmailButtonClicked() }

        imagebutton_viewprofile_telegram.setOnClickListener { presenter.onTelegramButtonClicked() }

        imagebutton_viewprofile_instagram.setOnClickListener { presenter.onInstagramButtonClicked() }

        addDisclaimerTextView(linearLayout)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun openSmsApp() {
        val message = edittext_viewprofile_message.text.toString()
        val smsAppIntent = intentUtils.getSmsAppIntent(PHONE_NUMBER, message)
        if (!openApp(smsAppIntent)) {
            showErrorSnackbar(AppError.SMS)
        }
    }

    override fun openEmailApp() {
        val emailSubject = getString(R.string.email_subject)
        val message = edittext_viewprofile_message.text.toString()
        val emailIntent = intentUtils.getEmailIntent(EMAIL, emailSubject, message)
        if (!openApp(emailIntent)) {
            showErrorSnackbar(AppError.EMAIL)
        }
    }

    override fun openTelegramApp() {
        val telegramIntent = intentUtils.getSpecificIntent(SocialNetworkApp.TELEGRAM)
        val telegramxIntent = intentUtils.getSpecificIntent(SocialNetworkApp.TELEGRAM_X)
        if (openApp(telegramIntent) || openApp(telegramxIntent)) {
            return
        }
        val browserIntent = intentUtils.getBrowserIntent(SocialNetworkApp.TELEGRAM.accountUrl)
        if (!openApp(browserIntent)) {
            showErrorSnackbar(AppError.BROWSER)
        }
    }

    override fun openIstagramApp() {
        val instagramIntent = intentUtils.getSpecificIntent(SocialNetworkApp.INSTAGRAM)
        if (openApp(instagramIntent)) {
            return
        }
        val browserIntent = intentUtils.getBrowserIntent(SocialNetworkApp.INSTAGRAM.accountUrl)
        if (!openApp(browserIntent)) {
            showErrorSnackbar(AppError.BROWSER)
        }
    }

    private fun addDisclaimerTextView(linearLayout: LinearLayout) {
        val disclaimerTv = TextView(this)

        disclaimerTv.setText(R.string.disclaimer)
        disclaimerTv.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        disclaimerTv.gravity = Gravity.CENTER

        linearLayout.addView(disclaimerTv)
    }

    private fun showErrorSnackbar(appError: AppError) {
        val messageId = when (appError) {
            AppError.SMS -> R.string.snackbar_no_sms_app
            AppError.EMAIL -> R.string.snackbar_no_email_app
            AppError.BROWSER -> R.string.snackbar_no_browser
        }
        val message = resources.getString(messageId)
        showSnackbar(message)
    }

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }

    private fun openApp(intent: Intent?): Boolean {
        return if (intent != null) {
            startActivity(intent)
            true
        } else {
            false
        }
    }
}
