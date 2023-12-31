package com.roberto_product.local.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import net.sqlcipher.BuildConfig
import net.sqlcipher.database.SupportFactory
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64
import javax.inject.Inject
import kotlin.reflect.KClass

class DatabaseHelper @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val sharedPreferences: SharedPreferences,
) {

    private val random by lazy {
        try {
            SecureRandom.getInstanceStrong()
        } catch (e: NoSuchAlgorithmException) {
            SecureRandom()
        }
    }

    /**
     * Creates an encrypted room database
     *
     * @param name name of the database
     * @param kClass database class
     * @param encrypted whether we enable encryption, defaults to encrypt on release builds
     * @param builder lambda used when you want to add options to builder
     */
    fun <T : RoomDatabase> createDatabase(
        name: String,
        kClass: KClass<T>,
        encrypted: Boolean = !BuildConfig.DEBUG,
        builder: RoomDatabase.Builder<T>.() -> Unit = {},
    ): T {
        require(name.isNotBlank()) {
            "Database name should not be blank"
        }

        val dbBuilder = Room.databaseBuilder(appContext, kClass.java, name)
            .fallbackToDestructiveMigration()
            .apply(builder)

        if (encrypted) {
            val passphrase = getOrCreateDatabasePassphrase(name)
            dbBuilder.openHelperFactory(SupportFactory(passphrase))
        }

        return dbBuilder.build()
    }

    /**
     * Generates a cryptographically strong random byte key.
     *
     * @param size size of the byte array, defaults to 32
     * @return a byte array containing random values
     */
    fun generateRandomKey(size: Int = 32): ByteArray = ByteArray(size).apply {
        random.nextBytes(this)
    }

    private fun getOrCreateDatabasePassphrase(name: String): ByteArray {
        check(sharedPreferences is EncryptedSharedPreferences) {
            "SharedPreferences is not encrypted!"
        }

        val dbPassphraseKey = "_$$name-dbKey"

        fun tryGetKey(): String? = sharedPreferences.getString(dbPassphraseKey, null)

        val tryDbKey = tryGetKey()

        val decoder = Base64.getDecoder()
        val encoder = Base64.getEncoder()

        return if (tryDbKey != null) {
            decoder.decode(tryDbKey)
        } else {
            val newDbPassphrase = generateRandomKey()

            sharedPreferences.edit {
                putString(dbPassphraseKey, encoder.encodeToString(newDbPassphrase))
            }

            // We fetch again from shared prefs rather than using the variable
            // to make sure it's available and also for single source of truth
            tryGetKey()?.let {
                decoder.decode(it)
            } ?: throw IllegalStateException("Cannot create database key")
        }
    }
}
