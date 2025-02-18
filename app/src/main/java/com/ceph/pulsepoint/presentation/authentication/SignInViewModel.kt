package com.ceph.pulsepoint.presentation.authentication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {


    private val _signInState = MutableStateFlow(SignInState())
    val signInstate = _signInState.asStateFlow()


    fun onSignInResult(result: SignInResult) {

        _signInState.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }

    }

    fun resetState() {
        _signInState.update { SignInState() }
    }
}