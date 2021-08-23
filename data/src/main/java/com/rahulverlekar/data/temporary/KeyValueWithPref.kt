package com.rahulverlekar.data.temporary

import android.content.Context
import android.content.SharedPreferences
import com.rahulverlekar.domain.KeyValueStorage
import com.rahulverlekar.domain.model.config.MovieDbConfig
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class KeyValueWithPref @Inject constructor(@ApplicationContext val applicationContext: Context) :
    KeyValueStorage {

    private val fileName = "prefs"

    private val localStore: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    private val TOKEN_KEY = "token"
    override var token: String?
        get() = localStore.getString(TOKEN_KEY, null)
        set(value) = localStore.edit().putString(TOKEN_KEY, value).apply()

    private val OFFSET = "offset"
    override var lastOffset: Int
        get() = localStore.getInt(OFFSET, -1)
        set(value) = localStore.edit().putInt(OFFSET, value).apply()

    private val COUNT = "count"
    override var count: Int?
        get() = localStore.getInt(COUNT, 0)
        set(value) = localStore.edit().putInt(COUNT, value?:0).apply()


    private val BASE_URL = "base_url"
    private val SECURED_URL = "secured_url"
    private val IMAGE_WIDTH = "image-width"
    override var imageBasePath: String?
        get() {
            return localStore.getString(BASE_URL, null)
        }
        set(value) {
            localStore.edit().putString(BASE_URL, value).apply()
        }

    override var securedImageBasePath: String?
        get() {
            return localStore.getString(SECURED_URL, null)
        }
        set(value) {
            localStore.edit().putString(SECURED_URL, value).apply()
        }

    override var imageWidth: String?
        get() {
            return localStore.getString(IMAGE_WIDTH, null)
        }
        set(value) {
            localStore.edit().putString(IMAGE_WIDTH, value).apply()
        }

    override fun saveConfig(config: MovieDbConfig) {
        imageBasePath = config.baseUrl
        securedImageBasePath = config.secBaseUrl
        imageWidth = config.imageSize
    }

    override fun getConfig(): MovieDbConfig? {
        if (imageBasePath == null) {
            return null
        }
        return MovieDbConfig(
            imageBasePath ?: "",
            securedImageBasePath ?: "",
            imageWidth ?: "original"
        )
    }
}