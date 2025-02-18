package com.ceph.pulsepoint


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ceph.pulsepoint.presentation.authentication.GoogleAuthClient
import com.ceph.pulsepoint.presentation.authentication.SignInViewModel
import com.ceph.pulsepoint.presentation.components.PulseBottomBar
import com.ceph.pulsepoint.presentation.components.PulseTopBar
import com.ceph.pulsepoint.presentation.navigation.NavGraphSetUp
import com.ceph.pulsepoint.presentation.navigation.Routes
import com.ceph.pulsepoint.ui.theme.ThePulsePointTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val googleAuthClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        hideSystemBars()
        enableEdgeToEdge()
        setContent {
            ThePulsePointTheme {
                val navController = rememberNavController()


//      Google sign in authentication.
                val viewModel = viewModel<SignInViewModel>()
                val state by viewModel.signInstate.collectAsStateWithLifecycle()

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->

                        if (result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = googleAuthClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                viewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )
                LaunchedEffect(key1 = Unit) {
                    if (googleAuthClient.getCurrentUser() != null) {
                        navController.navigate(Routes.Home.route)
                    }

                }


                LaunchedEffect(key1 = state.isSignInSuccessful) {
                    if (state.isSignInSuccessful) {
                        Toast.makeText(applicationContext, "Sign in successful", Toast.LENGTH_SHORT)
                            .show()

                        navController.navigate(Routes.Home.route)
                        viewModel.resetState()
                    }
                }

                var searchQuery by remember { mutableStateOf("") }

                val topAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

//                Controlling the bottom bar visibility.
                val scrollState = rememberLazyListState()
                var isBottomBarVisible by remember { mutableStateOf(true) }
                var lastScrollIndex by remember { mutableIntStateOf(0) }

                LaunchedEffect(key1 = scrollState.firstVisibleItemIndex) {
                    if (scrollState.firstVisibleItemIndex < lastScrollIndex) {
                        isBottomBarVisible = true
                    } else if (scrollState.firstVisibleItemIndex > lastScrollIndex) {
                        isBottomBarVisible = false
                    }
                    lastScrollIndex = scrollState.firstVisibleItemIndex
                }


                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route


                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                    topBar = {


                        if (currentRoute == Routes.Home.route) {
                            PulseTopBar(scrollBehavior = topAppBarScrollBehavior)
                        }


                    },
                    bottomBar = {

                        if (currentRoute != Routes.Login.route) {
                            PulseBottomBar(
                                navController = navController,
                                isVisible = isBottomBarVisible
                            )
                        }
                    }
                ) { paddingValues ->


                    NavGraphSetUp(
                        navController = navController,
                        listState = scrollState,
                        searchQuery = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                        },
                        paddingValues = paddingValues,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntentSender = googleAuthClient.signIn()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntentSender ?: return@launch
                                    ).build()
                                )
                            }
                        },
                        googleAuthClient = googleAuthClient,
                        onSignOut = {
                            lifecycleScope.launch {
                                googleAuthClient.signOut()
                                navController.navigate(Routes.Login.route)
                            }
                        }
                    )
                }


            }
        }
    }


//    private fun hideSystemBars() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.apply {
//                hide(WindowInsets.Type.statusBars())
//                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        } else {
//            @Suppress("DEPRECATION")
//            window.decorView.systemUiVisibility = (
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    )
//        }
//    }

}