package com.example.lifehub.encryptedsharedpreferences

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurePreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveUserPin(userId: String, pin: String) {
        prefs.edit { putString(buildKeyFromUserId(userId), pin) }
    }

    fun getUserPin(userId: String): String? {
        return prefs.getString(buildKeyFromUserId(userId), null)
    }

    fun hasPin(userId: String): Boolean {
        return prefs.contains(buildKeyFromUserId(userId))
    }

    fun clearUserPin(userId: String) {
        prefs.edit { remove(buildKeyFromUserId(userId)) }
    }

    fun clearAll() {
        prefs.edit { clear() }
    }

    companion object {
        fun buildKeyFromUserId(userId: String) = "pin_$userId"
    }
}