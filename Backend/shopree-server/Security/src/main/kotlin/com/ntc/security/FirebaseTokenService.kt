package com.ntc.security

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import org.springframework.stereotype.Service

@Service
class FirebaseTokenService(private val firebaseApp: FirebaseApp) {
    fun verifyToken(idToken: String): String? = try {
        FirebaseAuth.getInstance(firebaseApp).verifyIdToken(idToken).uid
    } catch (e: FirebaseAuthException) {
        null
    }
}