package com.kaikeventura.pbp.controller

import com.kaikeventura.pbp.configuration.JwtService
import com.kaikeventura.pbp.controller.request.AccessRequest
import com.kaikeventura.pbp.service.AccessService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/access")
class AccessController(
    private val jwtService: JwtService,
    private val accessService: AccessService
) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createAccess(
        @RequestHeader("Authorization") token: String,
        @RequestBody accessRequest: AccessRequest
    ) = accessService.createAccess(
        userEmail = jwtService.extractUsername(token.substring(7)),
        accessRequest = accessRequest
    )

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")
    fun updateAccess(
        @RequestHeader("Authorization") token: String,
        @PathVariable("id") id: UUID,
        @RequestBody accessRequest: AccessRequest
    ) = accessService.updateAccess(
        userEmail = jwtService.extractUsername(token.substring(7)),
        accessId = id,
        accessRequest = accessRequest
    )

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    fun getAllAccess(
        @RequestHeader("Authorization") token: String
    ) = accessService.getAllAccessByUser(
        userEmail = jwtService.extractUsername(token.substring(7))
    )

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun deleteAccess(
        @RequestHeader("Authorization") token: String,
        @PathVariable("id") id: UUID
    ) = accessService.deleteAccess(
        userEmail = jwtService.extractUsername(token.substring(7)),
        accessId = id
    )
}
