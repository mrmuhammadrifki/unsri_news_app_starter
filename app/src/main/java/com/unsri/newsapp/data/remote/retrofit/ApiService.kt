package com.unsri.newsapp.data.remote.retrofit

import com.unsri.newsapp.data.remote.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=us&category=business")
    suspend fun getNews(@Query("apiKey") apiKey: String): NewsResponse
}