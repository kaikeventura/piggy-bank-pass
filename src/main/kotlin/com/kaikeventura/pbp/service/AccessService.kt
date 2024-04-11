package com.kaikeventura.pbp.service

import com.kaikeventura.pbp.controller.request.AccessRequest
import com.kaikeventura.pbp.controller.response.AccessResponse
import com.kaikeventura.pbp.entity.AccessEntity
import com.kaikeventura.pbp.repository.AccessRepository
import com.kaikeventura.pbp.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccessService(
    private val userRepository: UserRepository,
    private val accessRepository: AccessRepository,
    private val encoderService: EncoderService
) {
    fun createAccess(
        userEmail: String,
        accessRequest: AccessRequest
    ) {
        val user = userRepository.findByEmail(userEmail) ?: throw UsernameNotFoundException("User $userEmail not found")
        accessRequest.let {
            AccessEntity(
                account = it.account,
                login = encoderService.encrypt(it.login),
                password = encoderService.encrypt(it.password),
                user = user
            )
        }.let {
            accessRepository.save(it)
        }
    }

    fun getAllAccessByUser(userEmail: String): List<AccessResponse> =
        accessRepository.findAllByUserEmail(userEmail).map {
            AccessResponse(
                id = UUID.fromString(it.id),
                account = it.account,
                login = encoderService.decrypt(it.login),
                password = encoderService.decrypt(it.password)
            )
        }
}
