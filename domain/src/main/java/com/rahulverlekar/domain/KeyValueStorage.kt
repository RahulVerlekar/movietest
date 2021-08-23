package com.rahulverlekar.domain

import com.rahulverlekar.domain.model.config.MovieDbConfig

interface KeyValueStorage {
    var token: String?
    var lastOffset: Int
    var count: Int?

    var imageBasePath: String?
    var securedImageBasePath: String?
    var imageWidth: String?
    fun saveConfig(config: MovieDbConfig)
    fun getConfig(): MovieDbConfig?
}