package com.roberto_product.local.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object LocalSharedPrefProvider {

    fun create(application: Application, isDebug: Boolean): SharedPreferences {
        val filename = application.packageName + "_preferences"

        return if (isDebug) {
            application.getSharedPreferences(filename, Context.MODE_PRIVATE)
        } else {
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            EncryptedSharedPreferences
                .create(
                    filename,
                    masterKeyAlias,
                    application,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
        }
    }
}