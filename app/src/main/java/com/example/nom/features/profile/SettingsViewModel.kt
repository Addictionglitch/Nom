package com.example.nom.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nom.core.data.local.NomDatabase
import com.example.nom.core.data.local.SecurePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val securePreferences: SecurePreferences,
    private val nomDatabase: NomDatabase
) : ViewModel() {

    fun onNotificationToggle(enabled: Boolean) {
        securePreferences.notificationPrefs = enabled
    }

    fun onDeleteAllData() {
        viewModelScope.launch {
            nomDatabase.spiritDao().clear()
            nomDatabase.plantDao().clear()
            nomDatabase.scanHistoryDao().clear()
            securePreferences.clear()
            // TODO: Navigate to Onboarding
        }
    }
}
