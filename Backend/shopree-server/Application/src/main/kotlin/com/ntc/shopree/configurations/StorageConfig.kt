package com.ntc.shopree.configurations

import com.ntc.service.ImageStorageService
import com.ntc.service.impl.ImageStorageServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StorageConfig(private val s3Properties: S3Properties) {
    @Bean
    fun imageStorageService(): ImageStorageService {
        return ImageStorageServiceImpl(
            bucket = s3Properties.bucket,
            region = s3Properties.region,
            accessKey = s3Properties.accessKey,
            secretKey = s3Properties.secretKey
        )
    }
}