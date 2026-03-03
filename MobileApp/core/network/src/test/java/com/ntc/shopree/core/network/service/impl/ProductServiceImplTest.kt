package com.ntc.shopree.core.network.service.impl

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductServiceImplTest {

    @Test
    fun `getProducts returns list of products`() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = """
                    [
                        {"id": "1", "title": "Product 1", "slug": "product-1", "price": 100.0, "description": "Desc 1", "category": "Cat 1", "image": "img1.png"},
                        {"id": "2", "title": "Product 2", "slug": "product-2", "price": 200.0, "description": "Desc 2", "category": "Cat 2", "image": "img2.png"}
                    ]
                """.trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
        val service = ProductServiceImpl(client, "http://localhost")

        val products = service.getProducts()

        assertEquals(2, products.size)
        assertEquals("Product 1", products[0].title)
        assertEquals("Product 2", products[1].title)
    }

    @Test
    fun `getProduct returns a single product`() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = """
                    {"id": "1", "title": "Product 1", "slug": "product-1", "price": 100.0, "description": "Desc 1", "category": "Cat 1", "image": "img1.png"}
                """.trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
        val service = ProductServiceImpl(client, "http://localhost")

        val product = service.getProduct("product-1")

        assertEquals("Product 1", product.title)
        assertEquals("product-1", product.slug)
    }
}
