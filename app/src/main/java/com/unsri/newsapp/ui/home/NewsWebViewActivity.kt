package com.unsri.newsapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unsri.newsapp.databinding.ActivityNewsWebViewBinding

class NewsWebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsUrl = intent.getStringExtra(NEWS_URL)

        if (newsUrl != null) {
            binding.webview.loadUrl(newsUrl)
        }
    }

    companion object {
        const val NEWS_URL = "news_url"
    }
}