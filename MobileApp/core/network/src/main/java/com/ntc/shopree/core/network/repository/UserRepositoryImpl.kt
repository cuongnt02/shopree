package com.ntc.shopree.core.network.repository

import com.ntc.shopree.core.model.User
import com.ntc.shopree.core.model.repository.UserRepository
import com.ntc.shopree.core.network.dto.toUser
import com.ntc.shopree.core.network.service.UserService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun getProfile(): User = userService.getProfile().toUser()
}
