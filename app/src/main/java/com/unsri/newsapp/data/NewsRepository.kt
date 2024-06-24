package com.unsri.newsapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.unsri.newsapp.BuildConfig
import com.unsri.newsapp.data.local.entity.NewsEntity
import com.unsri.newsapp.data.local.room.NewsDao
import com.unsri.newsapp.data.remote.retrofit.ApiService
import java.net.UnknownHostException

class NewsRepository(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) {

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews(BuildConfig.API_KEY)
            val articles = response.articles
            val newsList = articles.map { article ->
                val isBookmarked = newsDao.isNewsBookmarked(article.title)
                NewsEntity(
                    article.title,
                    article.publishedAt,
                    article.urlToImage,
                    article.url,
                    isBookmarked
                )
            }
            newsDao.deleteAll()
            newsDao.insertNews(newsList)
        } catch (e: Exception) {
            val errorMessage = if (e is UnknownHostException) {
                "Koneksi internet tidak ada. Silahkan periksa koneksi internet Anda."
            } else {
                "Terjadi kesalahan: ${e.message.toString()}"
            }
            emit(Result.Error(errorMessage))
        }
        val localData: LiveData<Result<List<NewsEntity>>> = newsDao.getNews().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getNewsBookmarked(): LiveData<List<NewsEntity>> {
       return newsDao.getBookmarkedNews()
    }

    suspend fun setBookmarkNews(news: NewsEntity, bookmarkState: Boolean) {
        news.isBookmarked = bookmarkState
        newsDao.updateNews(news)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao)
            }.also { instance = it }
    }
}