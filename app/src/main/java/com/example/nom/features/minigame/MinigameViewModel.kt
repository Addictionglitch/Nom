package com.example.nom.features.minigame

import androidx.lifecycle.ViewModel
import com.example.nom.core.data.local.SecurePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class MinigameViewModel @Inject constructor(
    private val securePreferences: SecurePreferences
) : ViewModel() {

    private val _canPlayGardenMinigame = MutableStateFlow(false)
    val canPlayGardenMinigame = _canPlayGardenMinigame.asStateFlow()

    init {
        checkDailyReset()
    }

    private fun checkDailyReset() {
        val lastPlayed = securePreferences.lastGardenHarvestTimestamp
        val today = Calendar.getInstance()
        val lastPlayedCal = Calendar.getInstance().apply { timeInMillis = lastPlayed }

        _canPlayGardenMinigame.value = lastPlayed == 0L || !isSameDay(today, lastPlayedCal)
    }

    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
}
