package com.kaikeventura.pbp.service

import com.kaikeventura.pbp.controller.request.AccessRequest
import com.kaikeventura.pbp.entity.AccessEntity
import com.kaikeventura.pbp.repository.AccessRepository
import org.springframework.stereotype.Service

@Service
class AccessService(
    private val accessRepository: AccessRepository
) {
    fun createAccess(
        userEmail: String,
        accessRequest: AccessRequest
    ) {
        accessRequest.let {
            AccessEntity(
                login = it.login,
                password = it.password
            )
        }.let {
            accessRepository.save(it)
        }
    }
}
