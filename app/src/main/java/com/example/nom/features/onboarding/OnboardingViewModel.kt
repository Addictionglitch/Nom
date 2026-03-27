package com.example.nom.features.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.data.local.SecurePreferences
import com.example.nom.core.domain.repositories.SpiritRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val spiritRepository: SpiritRepository,
    private val securePreferences: SecurePreferences
) : ViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    private val _showNamingDialog = MutableStateFlow(false)
    val showNamingDialog = _showNamingDialog.asStateFlow()

    fun onPageChanged(page: Int) {
        _currentPage.value = page
    }

    fun onShowNamingDialog() {
        _showNamingDialog.value = true
    }

    fun onDismissNamingDialog() {
        _showNamingDialog.value = false
    }

    fun onNameSpirit(name: String) {
        viewModelScope.launch {
            spiritRepository.createSpirit(name)
            securePreferences.onboardingCompleted = true
            // TODO: Navigate to Spirit home and clear backstack
        }
    }
}
