package com.isakaro.qwik.webview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import timber.log.Timber

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun QwikWebView(
    modifier: Modifier = Modifier,
    url: String,
    hideProgressAfterLoading: Boolean = true,
    cookies: List<QwikCookie> = emptyList(),
    webViewModel: WebViewModel = hiltViewModel(),
    userAgent: String = "Qwik-Android-WebView",
    failedToOpenLink: () -> Unit = {}
) {
    var valueCallback by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }
    val pageLoadingProgress = remember { mutableIntStateOf(0) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        valueCallback?.onReceiveValue(uris.toTypedArray())
        valueCallback = null
    }
    var progressVisible by remember { mutableStateOf(true) }
    var loaded by remember { mutableStateOf(true) }

    Box {
        Column(modifier = modifier) {
            if (progressVisible) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    progress = {
                        pageLoadingProgress.intValue / 100f
                    }
                )
            }

            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                if (request?.url.toString().startsWith("https://") || request?.url.toString().startsWith("http://")) {
                                    return false
                                }
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, request?.url.toString().toUri())
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    failedToOpenLink()
                                }
                                return true
                            }
                        }

                        settings.userAgentString = userAgent
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.allowFileAccess = true
                        settings.allowContentAccess = true
                        settings.allowFileAccessFromFileURLs = true
                        settings.allowUniversalAccessFromFileURLs = true
                        settings.javaScriptCanOpenWindowsAutomatically = true
                        settings.setSupportMultipleWindows(true)

                        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)

                        //addJavascriptInterface(IsakaroWebBridge(context, webViewModel), userAgent)

                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView, newProgress: Int) {
                                pageLoadingProgress.intValue = newProgress
                                if (newProgress == 100) {
                                    if(hideProgressAfterLoading){
                                        progressVisible = false
                                    }
                                    loaded = true
                                }
                            }

                            override fun onShowFileChooser(
                                webView: WebView,
                                filePathCallback: ValueCallback<Array<Uri>>,
                                fileChooserParams: FileChooserParams
                            ): Boolean {
                                try {
                                    valueCallback?.onReceiveValue(null)
                                    valueCallback = filePathCallback

                                    /**
                                     * image / * uses gallery picker which sometimes causes ERR_UPLOAD_FILE_CHANGED issue on some devices
                                     * we'll use * / * for now
                                     */
                                    val mimeType = if (fileChooserParams.acceptTypes.all { it.lowercase() in listOf(".jpg", ".jpeg", ".png") }) {
                                        "image/*"
                                    } else {
                                        "*/*"
                                    }

                                    launcher.launch("*/*")
                                } catch (e: Exception) {
                                    Timber.e(e)
                                    return false
                                }
                                return true
                            }

                            override fun onCreateWindow(
                                view: WebView?,
                                isDialog: Boolean,
                                isUserGesture: Boolean,
                                resultMsg: android.os.Message?
                            ): Boolean {
                                val newWebView = WebView(view?.context!!).apply {
                                    settings.javaScriptEnabled = true
                                    settings.domStorageEnabled = true
                                    settings.allowFileAccess = true
                                    settings.allowContentAccess = true
                                    settings.allowFileAccessFromFileURLs = true
                                    settings.allowUniversalAccessFromFileURLs = true
                                    settings.javaScriptCanOpenWindowsAutomatically = true
                                    settings.setSupportMultipleWindows(true)

                                    webViewClient = object : WebViewClient() {
                                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                            if (request?.url.toString().startsWith("https://") || request?.url.toString().startsWith("http://")) {
                                                return false
                                            }
                                            try {
                                                val intent = Intent(Intent.ACTION_VIEW, request?.url.toString().toUri())
                                                context.startActivity(intent)
                                            } catch (e: Exception) {
                                                failedToOpenLink()
                                            }
                                            return true
                                        }
                                    }

                                    webChromeClient = this@apply.webChromeClient
                                }

                                val transport = resultMsg?.obj as? WebView.WebViewTransport
                                transport?.webView = newWebView
                                resultMsg?.sendToTarget()

                                return true
                            }
                        }
                        setCookie(cookies)
                        loadUrl(url)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        if(!loaded){
            QwikCircularLoading(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

fun WebView.setCookie(cookies: List<QwikCookie>){
    val cookieManager = CookieManager.getInstance()
    cookieManager.acceptCookie()
    cookies.forEach { cookie ->
        cookieManager.setCookie(cookie.domain, "${cookie.name}=${cookie.value}; path=${cookie.path}; expires=${cookie.expires}; name=${cookie.name}")
    }
    cookieManager.flush()
    cookieManager.setAcceptThirdPartyCookies(this, true)
}

data class QwikCookie(
    val name: String,
    val value: String,
    val domain: String,
    val path: String = "/",
    val expires: String = (30 * 24 * 60 * 60).toString() // 30 days
)

@Preview
@Composable
fun QwikWebViewPreview() {
    QwikWebView(url = "https://www.google.com")
}
