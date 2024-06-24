package com.unsri.newsapp.data.di

import android.content.Context
import com.unsri.newsapp.data.NewsRepository
import com.unsri.newsapp.data.local.room.NewsDatabase
import com.unsri.newsapp.data.remote.retrofit.ApiConfig


object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}