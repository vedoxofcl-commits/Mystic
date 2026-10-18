package com.mystic.browser

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var web: WebView
    private lateinit var address: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(18, 18, 25)
        window.navigationBarColor = Color.rgb(18, 18, 25)

        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 18, 16, 10)
            setBackgroundColor(Color.rgb(18, 18, 25))
        }
        val brand = TextView(this).apply {
            text = "MYSTIC  ✦"
            textSize = 22f
            setTextColor(Color.rgb(255, 70, 80))
            gravity = Gravity.CENTER_VERTICAL
            setPadding(8, 4, 8, 14)
        }
        val bar = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(10, 7, 10, 7)
            background = android.graphics.drawable.GradientDrawable().apply {
                setColor(Color.rgb(39, 39, 51))
                cornerRadius = 36f
                setStroke(1, Color.rgb(86, 72, 88))
            }
        }
        address = EditText(this).apply {
            hint = "Search or enter website"
            setHintTextColor(Color.LTGRAY)
            setTextColor(Color.WHITE)
            textSize = 15f
            setSingleLine(true)
            imeOptions = EditorInfo.IME_ACTION_GO
            setBackgroundColor(Color.TRANSPARENT)
            setPadding(12, 4, 6, 4)
            layoutParams = LinearLayout.LayoutParams(0, -2, 1f)
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO) { navigate(); true } else false
            }
        }
        val go = Button(this).apply {
            text = "Go"
            setTextColor(Color.WHITE)
            setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(220, 45, 58)))
            setOnClickListener { navigate() }
        }
        bar.addView(address); bar.addView(go)
        web = WebView(this).apply {
            setBackgroundColor(Color.rgb(18, 18, 25))
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
        }
        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }
        fun navButton(label: String, action: () -> Unit) {
            nav.addView(Button(this).apply {
                text = label
                setTextColor(Color.WHITE)
                setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(48, 43, 56)))
                setOnClickListener { action() }
                layoutParams = LinearLayout.LayoutParams(0, -2, 1f)
            })
        }
        navButton("‹") { if (web.canGoBack()) web.goBack() }
        navButton("Home") { address.setText("https://duckduckgo.com"); navigate() }
        navButton("›") { if (web.canGoForward()) web.goForward() }
        page.addView(brand)
        page.addView(bar)
        page.addView(web, LinearLayout.LayoutParams(-1, 0, 1f))
        page.addView(nav)
        setContentView(page)
        address.setText("https://duckduckgo.com")
        web.loadUrl("https://duckduckgo.com")
    }

    private fun navigate() {
        var value = address.text.toString().trim()
        if (value.isEmpty()) return
        if (!value.contains("://")) {
            value = if (value.contains(".") && !value.contains(" ")) "https://$value"
                    else "https://duckduckgo.com/?q=" + java.net.URLEncoder.encode(value, "UTF-8")
        }
        web.loadUrl(value)
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        if (::web.isInitialized && web.canGoBack()) web.goBack() else super.onBackPressed()
    }
}
