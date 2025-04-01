package com.isakaro.qwik.webview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class WebViewModel @Inject constructor() : ViewModel() {
    private val _webViewEvents = MutableStateFlow<WebViewEvent?>(null)
    val webViewEvents = _webViewEvents.asStateFlow()

    fun onWebViewEvent(event: WebViewEvent) {
        _webViewEvents.value = event
    }

    fun clearEvent() {
        _webViewEvents.value = null
    }
}

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