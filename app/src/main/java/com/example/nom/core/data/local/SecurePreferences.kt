package com.example.nom.core.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * A wrapper around EncryptedSharedPreferences to provide secure storage for sensitive data.
 *
 * @param context The application context.
 */
class SecurePreferences(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /**
     * The authentication token.
     */
    var authToken: String?
        get() = sharedPreferences.getString("auth_token", null)
        set(value) = sharedPreferences.edit().putString("auth_token", value).apply()

    /**
     * Whether the onboarding process has been completed.
     */
    var onboardingCompleted: Boolean
        get() = sharedPreferences.getBoolean("onboarding_completed", false)
        set(value) = sharedPreferences.edit().putBoolean("onboarding_completed", value).apply()

    /**
     * The notification preferences.
     */
    var notificationPrefs: Boolean
        get() = sharedPreferences.getBoolean("notification_prefs", true)
        set(value) = sharedPreferences.edit().putBoolean("notification_prefs", value).apply()

    /**
     * The timestamp of the last sync.
     */
    var lastSyncTimestamp: Long
        get() = sharedPreferences.getLong("last_sync_timestamp", 0)
        set(value) = sharedPreferences.edit().putLong("last_sync_timestamp", value).apply()
}
