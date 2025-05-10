package com.example.core.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.core.R

fun Context.openEmailApp() {
    try {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_EMAIL)
        }
        val chooser = Intent.createChooser(intent, getString(R.string.choose_email_app))
        startActivity(chooser)
    } catch (e: Exception) {
        Toast.makeText(
            this,
            getString(R.string.no_email_app),
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Context.shareLink(url: String, title: String? = null) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, if (title.isNullOrBlank()) url else "$title\n$url")
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_via))
    startActivity(shareIntent)
}