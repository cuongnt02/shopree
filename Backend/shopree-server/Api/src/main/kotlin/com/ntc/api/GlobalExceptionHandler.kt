package com.ntc.api

import com.ntc.api.payload.error.NotFoundErrorResponse
import com.ntc.api.payload.error.UnexpectedErrorResponse
import com.ntc.domain.exception.AuthenticationException
import com.ntc.domain.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<UnexpectedErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(UnexpectedErrorResponse(ex.message ?: "Authentication failed"))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<NotFoundErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(NotFoundErrorResponse(ex.message ?: "Resource not found"))
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<UnexpectedErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(UnexpectedErrorResponse("An unexpected error occurred"))
    }
}