package com.example.web

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.ClientCertRequest
import android.webkit.HttpAuthHandler
import android.webkit.RenderProcessGoneDetail
import android.webkit.SafeBrowsingResponse
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.web.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        hideSystemUI()
        binding.webView.setPadding(0,getStatusBarHeight(),0,0)





       // val url = "https://main--gilded-swan-1f3c3c.netlify.app/"
        //val url = "https://www.figma.com/proto/qDG4SVPlRwTIFFLUKJJ4Qm/LMS-Prototyping?content-scaling=fixed&embed-host=share&kind=proto&node-id=1-663&page-id=0%3A1&scaling=scale-down&starting-point-node-id=1%3A663&theme=light&version=2"
        val url = "http://kingdom.elivehive.com:8080/three_kingdoms/index.html?app_id=hive&uid=15&token=\$2y\$10\$8faC2gIH9cXm/N36rYIek.jsvMA8mzaFIfrZlY0Tq/oG.QvK0WxVa"
     //   val url = "https://www.google.com"

        /*binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true  //this one made the url load after completing the  loading
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            loadsImagesAutomatically = true
        }*/

//// Enable DOM storage
//        webSettings.domStorageEnabled = true


/*        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?,
            ): Boolean {
                val url = request?.url.toString()
                view?.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                //setProgressDialogVisibility(true)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
//                isLoaded = true
//                isGameLoaded = true
//                setProgressDialogVisibility(false)
//                binding.noNetLay.visibility = View.GONE
//                binding.noDataLay.visibility = View.GONE
                super.onPageFinished(view, url)
            }

            *//* @RequiresApi(Build.VERSION_CODES.M)
             override fun onReceivedError(
                 view: WebView,
                 request: WebResourceRequest,
                 error: WebResourceError,
             ) {
                 isLoaded = false
                 binding.noDataLay.visibility = View.VISIBLE
                 val errorMessage = "Got Error! $error"
                 Timber.tag("errorMessage").e(errorMessage)
                 setProgressDialogVisibility(false)
                 // handleError(error.errorCode, view)
                 super.onReceivedError(view, request, error)
             }*//*

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?,
            ) {
                try {
                    //isLoaded = false
                   // binding.noDataLay.visibility = View.VISIBLE
                    val errorMessage = "Got Error! $description"
                    Log.d("errorMessage", "errorMessage: $errorMessage \n $errorCode")
                    //setProgressDialogVisibility(false)
                    super.onReceivedError(view, errorCode, description, failingUrl)
                } catch (e: Exception) {
                    e.message
                }
            }
        }*/



       binding.webView.settings.javaScriptEnabled =true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.loadWithOverviewMode = true
        binding.webView.settings.defaultTextEncodingName = "UTF-8"
        binding.webView.settings.allowFileAccess = true
        binding.webView.settings.allowUniversalAccessFromFileURLs = true
        binding.webView.settings.databaseEnabled = true
        binding.webView.settings.useWideViewPort = true

        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.settings.allowFileAccess = true
        binding.webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        binding.webView.settings.setSupportZoom(false) // Optional: Disable zoom
        binding.webView.settings.setSupportMultipleWindows(true)


        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?,
            ): Boolean {
                val url = request?.url.toString()
                if (url.contains("mailto:")) {
                    view?.context?.startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    )
                    return true
                } else {
                    runOnUiThread {
                       // view?.loadUrl(url)
                    }

                }

                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                //setProgressDialogVisibility(true)
                super.onPageStarted(view, url, favicon)
                Log.d("GameErrorMessage", "1")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                //isLoaded = true
                //setProgressDialogVisibility(false)
                super.onPageFinished(view, url)
                Log.d("GameErrorMessage", "2")
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.d("GameErrorMessage", "3")
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                Log.d("GameErrorMessage", "4")
            }

            override fun onRenderProcessGone(
                view: WebView?,
                detail: RenderProcessGoneDetail?
            ): Boolean {
                Log.d("GameErrorMessage", "5")
                return super.onRenderProcessGone(view, detail)

            }

            override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
                super.onUnhandledKeyEvent(view, event)
                Log.d("GameErrorMessage", "6")
            }

            override fun onFormResubmission(
                view: WebView?,
                dontResend: Message?,
                resend: Message?
            ) {
                super.onFormResubmission(view, dontResend, resend)
                Log.d("GameErrorMessage", "7")
            }

            override fun onReceivedHttpAuthRequest(
                view: WebView?,
                handler: HttpAuthHandler?,
                host: String?,
                realm: String?
            ) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm)
                Log.d("GameErrorMessage", "8")
            }

            override fun onReceivedLoginRequest(
                view: WebView?,
                realm: String?,
                account: String?,
                args: String?
            ) {
                super.onReceivedLoginRequest(view, realm, account, args)
                Log.d("GameErrorMessage", "9")
            }

            override fun onSafeBrowsingHit(
                view: WebView?,
                request: WebResourceRequest?,
                threatType: Int,
                callback: SafeBrowsingResponse?
            ) {
                super.onSafeBrowsingHit(view, request, threatType, callback)
                Log.d("GameErrorMessage", "10")
            }

            override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
                super.onScaleChanged(view, oldScale, newScale)
                Log.d("GameErrorMessage", "11")
            }

            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                Log.d("GameErrorMessage", "12")
            }

            override fun onTooManyRedirects(
                view: WebView?,
                cancelMsg: Message?,
                continueMsg: Message?
            ) {
                super.onTooManyRedirects(view, cancelMsg, continueMsg)
            }
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError,
            ) {
                //isLoaded = false
                val errorMessage = "Got Error! $error"
                //setProgressDialogVisibility(false)
                super.onReceivedError(view, request, error)
                Log.d("GameErrorMessage", errorMessage)
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                Log.d("GameErrorMessage", errorResponse.toString())
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                super.onReceivedSslError(view, handler, error)
                Log.d("GameErrorMessage", error.toString())
            }
//            override fun onReceivedClientCertRequest(
//                view: WebView?,
//                request: ClientCertRequest?
//            ) {
//                Log.d("GameErrorMessage", "13")
//                request?.cancel() // Ignore the certificate request
//            }
        }

        if(AppUtil.isInternetAvailable(this)){

            runOnUiThread{
                try {
                    binding.webView.loadUrl(url)
                } catch (e: Exception) {
                    Toast.makeText(this, "No Internet, Please try again", Toast.LENGTH_SHORT).show()
                }
            }



        }else{
            Toast.makeText(this, "No Internet, Please try again", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }


    @SuppressLint("InternalInsetResource")
    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // Use sticky immersive mode
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                   // or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  //commen
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                   // or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Call this to show the system bars
    private fun showSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

}