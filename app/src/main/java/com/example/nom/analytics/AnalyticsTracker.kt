package com.example.nom.analytics

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Stub analytics tracker. Logs events to Timber for MVP.
 * TODO: Wire up Firebase Analytics post-launch.
 */
@Singleton
class AnalyticsTracker @Inject constructor() {

    companion object {
        const val PLANT_SCANNED = "plant_scanned"
        const val SPIRIT_FED = "spirit_fed"
        const val LEVEL_UP = "level_up"
        const val EVOLUTION = "evolution"
        const val DISCOVERY = "discovery"
        const val MINIGAME_PLAYED = "minigame_played"
        const val MINIGAME_COMPLETED = "minigame_completed"
        const val ONBOARDING_COMPLETED = "onboarding_completed"
    }

    fun trackEvent(name: String, params: Map<String, Any> = emptyMap()) {
        Timber.d("Analytics: $name | $params")
    }
}
