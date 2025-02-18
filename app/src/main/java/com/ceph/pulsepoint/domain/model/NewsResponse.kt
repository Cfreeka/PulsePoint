package com.ceph.pulsepoint.domain.model


data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)