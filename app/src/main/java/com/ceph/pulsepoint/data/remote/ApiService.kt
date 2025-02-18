package com.ceph.pulsepoint.data.remote

import com.ceph.pulsepoint.domain.model.NewsResponse
import com.ceph.pulsepoint.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String= Constants.API_KEY,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse

    @GET("v2/everything")
    suspend fun getSearchedNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String=Constants.API_KEY,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NewsResponse

}