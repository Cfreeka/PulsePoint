package com.ceph.pulsepoint.domain.repository

import androidx.paging.PagingData
import com.ceph.pulsepoint.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface PulseRepository {

    fun getNewsHeadlines(): Flow<PagingData<Article>>

    fun getSearchedNews(query: String): Flow<PagingData<Article>>

    fun getFavoriteNews(): Flow<PagingData<Article>>

    fun getFavoriteArticlesUrls(): Flow<List<String>>

    suspend fun toggleFavoriteStatus(article: Article)
}