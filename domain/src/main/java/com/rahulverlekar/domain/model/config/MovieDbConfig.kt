package com.rahulverlekar.domain.model.config

data class MovieDbConfig(val baseUrl: String, val secBaseUrl: String, val imageSize: String) {

    fun attachBasePath(filePath: String): String {
        return secBaseUrl + imageSize + filePath
    }
}