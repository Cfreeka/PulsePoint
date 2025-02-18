package com.ceph.pulsepoint.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.ceph.pulsepoint.presentation.authentication.GoogleAuthClient
import com.ceph.pulsepoint.presentation.authentication.SignInScreen
import com.ceph.pulsepoint.presentation.favorite.FavoriteScreen
import com.ceph.pulsepoint.presentation.favorite.FavoriteViewModel
import com.ceph.pulsepoint.presentation.home.HomeScreen
import com.ceph.pulsepoint.presentation.home.HomeViewModel
import com.ceph.pulsepoint.presentation.profile.ProfileScreen
import com.ceph.pulsepoint.presentation.search.SearchScreen
import com.ceph.pulsepoint.presentation.search.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraphSetUp(
    navController: NavHostController,
    listState: LazyListState,
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    paddingValues: PaddingValues,
    googleAuthClient: GoogleAuthClient,
    onSignInClick: () -> Unit,
    onSignOut: () -> Unit

) {
    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Home.route) {
            val homeViewModel = koinViewModel<HomeViewModel>()
            val articles = homeViewModel.articles.collectAsLazyPagingItems()
            val favoriteArticlesUrls by
            homeViewModel.favoriteArticlesUrls.collectAsStateWithLifecycle()

            HomeScreen(
                articles = articles,
                listState = listState,
                onToggleStatus = { article ->
                    homeViewModel.toggleFavoriteStatus(article)
                },
                favoriteArticlesUrls = favoriteArticlesUrls,
                paddingValues = paddingValues

            )
        }
        composable(Routes.Favorite.route) {
            val favoriteViewModel = koinViewModel<FavoriteViewModel>()
            val favoriteNewsArticles = favoriteViewModel.favoriteArticles.collectAsLazyPagingItems()
            val favArticlesUrls by
            favoriteViewModel.favoriteArticlesUrls.collectAsStateWithLifecycle()

            FavoriteScreen(
                listState = listState,
                favoriteArticles = favoriteNewsArticles,
                favoriteArticlesUrls = favArticlesUrls,
                onToggleStatus = { article ->
                    favoriteViewModel.toggleFavoriteStatus(article)
                }
            )
        }
        composable(Routes.Search.route) {

            val searchViewModel = koinViewModel<SearchViewModel>()
            val searchedNews = searchViewModel.searchedNews.collectAsLazyPagingItems()
            val favArticlesUrls by searchViewModel.favoriteArticlesUrls.collectAsStateWithLifecycle()
            SearchScreen(
                listState = listState,
                searchQuery = searchQuery,
                searchedNews = searchedNews,
                onQueryChange = onQueryChange,
                onSearch = { query ->
                    searchViewModel.searchedForNews(query)
                },
                onToggleStatus = { article ->
                    searchViewModel.toggleFavoriteStatus(article)
                },
                favoriteArticlesUrls = favArticlesUrls

            )
        }
        composable(Routes.Profile.route) {
            ProfileScreen(
                userData = googleAuthClient.getCurrentUser(),
                onSignOut = onSignOut

            )
        }
        composable(Routes.Login.route) {

            SignInScreen(
                onSignInClick = onSignInClick
            )

        }
    }
}