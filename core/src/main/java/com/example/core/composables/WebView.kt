package com.example.core.composables

import android.annotation.SuppressLint
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.core.analytics.Page

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(
    url: String,
    modifier: Modifier = Modifier,
    page: Page
) {
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        if (hasError) {
            ErrorView(
                page = page,
                refresh = {
                    hasError = false
                    isLoading = true
                }
            )
        } else {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                isLoading = false
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                hasError = true
                                isLoading = false
                            }
                        }
                        loadUrl(url)
                    }
                }
            )
        }

        if (isLoading) {
            LoadingView()
        }
    }
}

@Preview
@Composable
private fun PreviewArticleWebView() {
    WebView(
        url = "https://www.manilatimes.net/2025/05/09/tmt-newswire/globenewswire/revitag-skin-tag-remover-under-review-the-oatmeal-hack-to-remove-skin-tags-moles-for-clear-smooth-skin-appearance/2109755",
        page = Page.VERIFY_PIN
    )
}