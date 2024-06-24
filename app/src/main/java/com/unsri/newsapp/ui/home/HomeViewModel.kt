package com.unsri.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsri.newsapp.data.NewsRepository
import com.unsri.newsapp.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class HomeViewModel(private val newsRepository: NewsRepository): ViewModel() {
    fun getHeadlinesNews() = newsRepository.getHeadlineNews()

    fun saveNews(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setBookmarkNews(news, true)
        }
    }

    fun deleteNews(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setBookmarkNews(news, false)
        }
    }
}