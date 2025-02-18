package com.ceph.pulsepoint.presentation.authentication

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)