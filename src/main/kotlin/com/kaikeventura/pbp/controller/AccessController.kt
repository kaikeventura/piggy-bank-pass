package com.kaikeventura.pbp.controller

import com.kaikeventura.pbp.configuration.JwtService
import com.kaikeventura.pbp.controller.request.AccessRequest
import com.kaikeventura.pbp.service.AccessService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController("/access")
class AccessController(
    private val jwtService: JwtService,
    private val accessService: AccessService
) {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    fun createAccess(
        @RequestHeader("Authorization") token: String,
        @RequestBody accessRequest: AccessRequest
    ) = accessService.createAccess(
        userEmail = jwtService.extractUsername(token),
        accessRequest = accessRequest
    )
}
