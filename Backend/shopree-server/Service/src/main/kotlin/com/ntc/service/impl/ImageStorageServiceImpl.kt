package com.ntc.service.impl

import com.ntc.service.ImageStorageService
import org.springframework.stereotype.Service
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.InputStream

class ImageStorageServiceImpl(
    private val bucket: String,
    private val region: String,
    private val accessKey: String,
    private val secretKey: String
) : ImageStorageService {

    private val client: S3Client = S3Client.builder()
        .region(Region.of(region))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
            )
        ).build()

    override fun upload(
        key: String,
        inputStream: InputStream,
        contentType: String,
        size: Long
    ): String {
        client.putObject(
            PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .contentLength(size)
                .build(),
            RequestBody.fromInputStream(inputStream, size)
        )
        return "https://s3.$region.amazonaws.com/$bucket/$key"
    }

    override fun delete(url: String) {
        val key = url.substringAfter(".amazonaws.com/$bucket/")
        client.deleteObject(
            DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build()
        )
    }
}