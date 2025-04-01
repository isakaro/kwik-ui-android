package com.isakaro.qwik.webview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface

sealed class WebViewEvent {
    data object ListingCreated: WebViewEvent()
    data object ListingCreationCancelled: WebViewEvent()
    data object GoBack: WebViewEvent()
}

class IsakaroWebBridge internal constructor(
    private val context: Context,
    private val webViewModel: WebViewModel
) {
    @JavascriptInterface
    fun onListingCreated() {
        Handler(Looper.getMainLooper()).post {
            webViewModel.onWebViewEvent(WebViewEvent.ListingCreated)
        }
    }

    @JavascriptInterface
    fun onListingCreationCancelled() {
        Handler(Looper.getMainLooper()).post {
            webViewModel.onWebViewEvent(WebViewEvent.ListingCreationCancelled)
        }
    }

    @JavascriptInterface
    fun goBack() {
        Handler(Looper.getMainLooper()).post {
            webViewModel.onWebViewEvent(WebViewEvent.GoBack)
        }
    }
}