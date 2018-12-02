package com.androidacademy.msk.exerciseproject.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.androidacademy.msk.exerciseproject.data.network.SocialNetworkApp

import javax.inject.Inject

class IntentUtils
@Inject constructor(private val appContext: Context) {

    fun getEmailIntent(email: String, subject: String, message: String): Intent? {
        val mailto = "mailto:$email" +
                "?cc=" +
                "&subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(message)

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse(mailto)

        return getIntentForExistingApp(emailIntent)
    }

    fun getSpecificIntent(app: SocialNetworkApp): Intent? {
        val uri = Uri.parse(app.accountUrl)
        val specificIntent = Intent(Intent.ACTION_VIEW, uri).setPackage(app.appPackage)
        return getIntentForExistingApp(specificIntent)
    }

    fun getBrowserIntent(stringUri: String): Intent? {
        val uri = Uri.parse(stringUri)
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        return getIntentForExistingApp(browserIntent)
    }

    fun getSmsAppIntent(phoneNumber: String, message: String): Intent? {
        val uri = Uri.parse("smsto:$phoneNumber")
        val smsIntent = Intent(Intent.ACTION_SENDTO, uri).putExtra("sms_body", message)
        return getIntentForExistingApp(smsIntent)
    }

    private fun getIntentForExistingApp(intent: Intent): Intent? {
        return if (intent.resolveActivity(appContext.packageManager) == null) {
            null
        } else {
            intent
        }
    }
}
