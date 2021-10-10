package com.cheng.transactionterminal.usecase

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.cheng.transactionterminal.MainApplication


object SharedPrefHelper {

    private const val PREFERENCES_NAME = "encrypted_terminal"
    val defaultSharedPref: SharedPreferences by lazy {
        getEncryptedSharedPref(PREFERENCES_NAME)
    }

    private fun getEncryptedSharedPref(sharedPreferencesName: String): SharedPreferences {
        val context: Context = MainApplication.appContext!!
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        return EncryptedSharedPreferences.create(
            sharedPreferencesName,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}
