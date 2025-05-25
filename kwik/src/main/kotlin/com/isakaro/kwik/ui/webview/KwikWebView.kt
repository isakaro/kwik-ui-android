package com.isakaro.kwik.ui.webview

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
import com.isakaro.kwik.ui.loading.KwikCircularLoading

/**
 * Settings for the KwikWebView.
 *
 * @property userAgent The user agent string to be used by the WebView.
 * @property cookies A list of [KwikCookie] objects to be set as cookies in the WebView.
 * @property debug Whether to enable WebView debugging, default is false.
 * @property javaScriptEnabled Whether to enable JavaScript in the WebView, default is true.
 * @property domStorageEnabled Whether to enable DOM storage in the WebView, default is true.
 * @property allowFileAccess Whether to allow file access in the WebView, default is true.
 * @property allowContentAccess Whether to allow content access in the WebView, default is true.
 * @property allowFileAccessFromFileURLs Whether to allow file access from file URLs, default is false.
 * @property allowUniversalAccessFromFileURLs Whether to allow universal access from file URLs, default is false.
 * @property javaScriptCanOpenWindowsAutomatically Whether to allow JavaScript to open windows automatically, default is true.
 * @property supportMultipleWindows Whether to support multiple windows in the WebView, default is true.
 *
 * Example usage:
 *
 * ```
* KwikWebview(
*   url = "https://app.domain.com",
*   webViewSettings = {
*        cookies = mapOf(
*            "cookieName" to "cookieValue",
*            "anotherCookie" to "anotherValue"
*        ),
 *       userAgent = "Running on Kwik Android WebView"
*    }
* )
* ```
* */
data class KwikWebViewSettings(
    var userAgent: String = "Kwik-Android-WebView",
    var cookies: List<KwikCookie> = emptyList(),
    var debug: Boolean = false,
    var javaScriptEnabled: Boolean = true,
    var domStorageEnabled: Boolean = true,
    var allowFileAccess: Boolean = true,
    var allowContentAccess: Boolean = true,
    var allowFileAccessFromFileURLs: Boolean = false,
    var allowUniversalAccessFromFileURLs: Boolean = false,
    var javaScriptCanOpenWindowsAutomatically: Boolean = true,
    var supportMultipleWindows: Boolean = true
)

/**
 * A web view component that allows you to load a URL and interact with it.
 * Supports JavaScript, file access, and custom settings such as cookies and user agent parameters.
 *
 * @param modifier The modifier to be applied to the web view.
 * @param url The URL to be loaded in the web view.
 * @param javaScriptBridge An optional JavaScript bridge object to be used for communication between the web view and the app.
 * @param javaScriptBridgeName The name of the JavaScript bridge object.
 * @param hideProgressAfterLoading Whether to hide the progress bar after the page has loaded.
 * @param pageLoaded A callback function to be called when the page has finished loading.
 * @param failedToOpenLink A callback function to be called when a link fails to open.
 * @param webViewSettings A lambda function to configure the web view settings.
 * */
@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
fun KwikWebView(
    modifier: Modifier = Modifier,
    url: String,
    javaScriptBridge: Any? = null,
    javaScriptBridgeName: String = "KwikBridge",
    hideProgressAfterLoading: Boolean = true,
    pageLoaded: () -> Unit = {},
    failedToOpenLink: () -> Unit = {},
    webViewSettings: KwikWebViewSettings.() -> Unit = {}
) {
    var valueCallback by remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }
    val pageLoadingProgress = remember { mutableIntStateOf(0) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        valueCallback?.onReceiveValue(uris.toTypedArray())
        valueCallback = null
    }
    var progressVisible by remember { mutableStateOf(true) }
    var loaded by remember { mutableStateOf(false) }
    val _webViewSettings = KwikWebViewSettings().apply(webViewSettings)

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

                        settings.javaScriptEnabled = _webViewSettings.javaScriptEnabled
                        settings.domStorageEnabled = _webViewSettings.domStorageEnabled
                        settings.allowFileAccess = _webViewSettings.allowFileAccess
                        settings.allowContentAccess = _webViewSettings.allowContentAccess
                        settings.allowFileAccessFromFileURLs = _webViewSettings.allowFileAccessFromFileURLs
                        settings.allowUniversalAccessFromFileURLs = _webViewSettings.allowFileAccessFromFileURLs
                        settings.javaScriptCanOpenWindowsAutomatically = _webViewSettings.javaScriptCanOpenWindowsAutomatically
                        settings.setSupportMultipleWindows(_webViewSettings.supportMultipleWindows)

                        WebView.setWebContentsDebuggingEnabled(_webViewSettings.debug)

                        javaScriptBridge?.let {
                            addJavascriptInterface(it, javaScriptBridgeName)
                        }

                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView, newProgress: Int) {
                                pageLoadingProgress.intValue = newProgress
                                if (newProgress == 100) {
                                    pageLoaded()
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
                                     *
                                     * See <a href="https://issues.chromium.org/issues/40123366">https://issues.chromium.org/issues/40123366</a>
                                     */
                                    val mimeType = if (fileChooserParams.acceptTypes.all { it.lowercase() in listOf(".jpg", ".jpeg", ".png") }) {
                                        "image/*"
                                    } else {
                                        "*/*"
                                    }

                                    launcher.launch("*/*")
                                } catch (e: Exception) {
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
                                    settings.javaScriptEnabled = _webViewSettings.javaScriptEnabled
                                    settings.domStorageEnabled = _webViewSettings.domStorageEnabled
                                    settings.allowFileAccess = _webViewSettings.allowFileAccess
                                    settings.allowContentAccess = _webViewSettings.allowContentAccess
                                    settings.allowFileAccessFromFileURLs = _webViewSettings.allowFileAccessFromFileURLs
                                    settings.allowUniversalAccessFromFileURLs = _webViewSettings.allowFileAccessFromFileURLs
                                    settings.javaScriptCanOpenWindowsAutomatically = _webViewSettings.javaScriptCanOpenWindowsAutomatically
                                    settings.setSupportMultipleWindows(_webViewSettings.supportMultipleWindows)

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

                                    webChromeClient = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                        this@apply.webChromeClient
                                    } else {
                                        object : WebChromeClient() {

                                        }
                                    }
                                }

                                val transport = resultMsg?.obj as? WebView.WebViewTransport
                                transport?.webView = newWebView
                                resultMsg?.sendToTarget()

                                return true
                            }
                        }
                        setCookie(_webViewSettings.cookies)
                        loadUrl(url)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        if(!loaded){
            KwikCircularLoading(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

/**
 * This function sets cookies for the WebView using the provided list of `KwikCookie` objects.
 *
 * @param cookies A list of [KwikCookie]` objects to be set as cookies in the [WebView].
 * */
fun WebView.setCookie(
    cookies: List<KwikCookie>
){
    val cookieManager = CookieManager.getInstance()
    cookieManager.acceptCookie()
    cookies.forEach { cookie ->
        cookieManager.setCookie(cookie.domain, "${cookie.name}=${cookie.value}; path=${cookie.path}; expires=${cookie.expires ?: 0}; name=${cookie.name}")
    }
    cookieManager.flush()
    cookieManager.setAcceptThirdPartyCookies(this, true)
}

/**
 * Cookie data class for KwikWebView.
 *
 * @property name The name of the cookie.
 * @property value The value of the cookie.
 * @property domain The domain for which the cookie is valid.
 * @property path The path for which the cookie is valid, default is "/".
 * @property expires The expiration date of the cookie in milliseconds.
 * @property secure Whether the cookie is secure (only sent over HTTPS), default is true.
 * @property httpOnly Whether the cookie is HTTP only (not accessible via JavaScript), default is false.
 * Wouldn't make sense to set it to true since we're running these cookies are set from the app, not from the web server.
 * */
data class KwikCookie(
    val name: String,
    val value: String,
    val domain: String,
    val path: String = "/",
    val expires: Int? = null,
    val secure: Boolean = true,
    val httpOnly: Boolean = false
)
