package com.unsri.newsapp.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unsri.newsapp.data.NewsRepository
import com.unsri.newsapp.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class BookmarkViewModel(private val newsRepository: NewsRepository): ViewModel() {
    fun getBookmarkedNews() = newsRepository.getNewsBookmarked()

    fun deleteNews(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setBookmarkNews(news, false)
        }
    }
}