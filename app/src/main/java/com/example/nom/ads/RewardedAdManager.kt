package com.example.nom.ads

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Stub rewarded ad manager for MVP.
 * TODO: Wire up AdMob post-launch.
 * Rules: Optional rewarded video only. NEVER interstitial. NEVER paywall scanning.
 */
@Singleton
class RewardedAdManager @Inject constructor() {
    fun loadAd() { Timber.d("RewardedAdManager: loadAd stub") }
    fun showAd(): Boolean { Timber.d("RewardedAdManager: showAd stub"); return false }
    fun isAdReady(): Boolean = false
}
