package com.ceph.pulsepoint.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.ceph.pulsepoint.domain.model.Article
import com.ceph.pulsepoint.presentation.components.ArticleItem


@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    listState: LazyListState,
    favoriteArticlesUrls: List<String>,
    onToggleStatus: (Article) -> Unit,
    paddingValues: PaddingValues
) {
    if (articles.itemCount == 0) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(1f)
                .padding(paddingValues),
            contentPadding = PaddingValues(5.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(articles.itemSnapshotList.size) { index ->
                articles[index]?.let { article ->
                    ArticleItem(
                        article = article,
                        onFavoriteClick = { onToggleStatus(article) },
                        isFavorite = favoriteArticlesUrls.contains(
                            article.url
                        )
                    )
                }
            }

        }
    }
}