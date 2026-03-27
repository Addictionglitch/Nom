package com.example.nom.features.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    fun onContinueWithoutAccount() {
        // TODO: Navigate to Spirit home
    }
}
