package com.kaikeventura.pbp.service

import com.kaikeventura.pbp.controller.request.AccessRequest
import com.kaikeventura.pbp.entity.AccessEntity
import com.kaikeventura.pbp.repository.AccessRepository
import com.kaikeventura.pbp.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AccessService(
    private val userRepository: UserRepository,
    private val accessRepository: AccessRepository
) {
    fun createAccess(
        userEmail: String,
        accessRequest: AccessRequest
    ) {
        val user = userRepository.findByEmail(userEmail) ?: throw UsernameNotFoundException("User $userEmail not found")
        accessRequest.let {
            AccessEntity(
                login = it.login,
                password = it.password,
                userEntity = user
            )
        }.let {
            accessRepository.save(it)
        }
    }
}
