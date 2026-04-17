package com.ntc.service

import java.io.InputStream

interface ImageStorageService {
    fun upload(key: String, inputStream: InputStream, contentType: String, size: Long): String
    fun delete(url: String)
}