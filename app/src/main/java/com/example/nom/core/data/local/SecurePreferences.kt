package com.example.nom.core.data.local
 
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
 
@Singleton
class SecurePreferences @Inject constructor(@ApplicationContext context: Context) {
 
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
 
    var authToken: String?
        get() = sharedPreferences.getString("auth_token", null)
        set(value) = sharedPreferences.edit().putString("auth_token", value).apply()
 
    var onboardingCompleted: Boolean
        get() = sharedPreferences.getBoolean("onboarding_completed", false)
        set(value) = sharedPreferences.edit().putBoolean("onboarding_completed", value).apply()
 
    var notificationPrefs: Boolean
        get() = sharedPreferences.getBoolean("notification_prefs", true)
        set(value) = sharedPreferences.edit().putBoolean("notification_prefs", value).apply()
 
    var lastSyncTimestamp: Long
        get() = sharedPreferences.getLong("last_sync_timestamp", 0)
        set(value) = sharedPreferences.edit().putLong("last_sync_timestamp", value).apply()
 
    var lastGardenHarvestTimestamp: Long
        get() = sharedPreferences.getLong("last_garden_harvest_timestamp", 0)
        set(value) = sharedPreferences.edit().putLong("last_garden_harvest_timestamp", value).apply()
 
    fun getLastNotificationTimestamp(): Long {
        return sharedPreferences.getLong("last_notification_timestamp", 0)
    }
 
    fun setLastNotificationTimestamp(timestamp: Long) {
        sharedPreferences.edit().putLong("last_notification_timestamp", timestamp).apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
