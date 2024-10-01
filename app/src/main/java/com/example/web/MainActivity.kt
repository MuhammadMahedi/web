package com.example.web

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        binding.webView.setPadding(0,getStatusBarHeight(),0,0)





       // val url = "https://main--gilded-swan-1f3c3c.netlify.app/"
        val url = "https://www.figma.com/proto/qDG4SVPlRwTIFFLUKJJ4Qm/LMS-Prototyping?content-scaling=fixed&embed-host=share&kind=proto&node-id=1-663&page-id=0%3A1&scaling=scale-down&starting-point-node-id=1%3A663&theme=light&version=2"

        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true  //this one made the url load after completing the  loading
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            loadsImagesAutomatically = true
        }

//// Enable DOM storage
//        webSettings.domStorageEnabled = true


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
                    view?.loadUrl(url)
                }

                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                //setProgressDialogVisibility(true)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                //isLoaded = true
                //setProgressDialogVisibility(false)
                super.onPageFinished(view, url)
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
                Log.d("errorMessage", errorMessage)
            }
        }

        if(AppUtil.isInternetAvailable(this)){
            binding.webView.loadUrl(url)
        }else{
            Toast.makeText(this, "No Internet, Please try again", Toast.LENGTH_SHORT).show()
        }


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

}