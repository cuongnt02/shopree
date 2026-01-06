package com.ntc.shopree.feature.auth.data

import com.google.firebase.auth.FirebaseUser
import com.ntc.shopree.feature.auth.data.remote.service.FirebaseService
import com.ntc.shopree.feature.auth.domain.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : FirebaseRepository {
    override suspend fun currentUser(): FirebaseUser? {
        return firebaseService.currentUser()
    }

    override suspend fun getTokenId(): String? {
        return firebaseService.getFirebaseIdToken()
    }
}



