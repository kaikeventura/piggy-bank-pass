package com.kaikeventura.pbp.service

import com.kaikeventura.pbp.configuration.JwtService
import com.kaikeventura.pbp.controller.request.AuthenticationRequest
import com.kaikeventura.pbp.controller.request.RegisterRequest
import com.kaikeventura.pbp.controller.response.AuthenticationResponse
import com.kaikeventura.pbp.entity.Role
import com.kaikeventura.pbp.entity.UserEntity
import com.kaikeventura.pbp.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
) {

    fun register(request: RegisterRequest): AuthenticationResponse =
        request.let {
            UserEntity(
                name = it.username,
                email = it.email,
                pass = passwordEncoder.encode(it.password),
                role = Role.USER
            )
        }.let {
            userRepository.save(it)
        }.let {
            AuthenticationResponse(
                token = jwtService.generateToken(it)
            )
        }

    fun login(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        val user = userRepository.findByEmail(request.email) ?: throw UsernameNotFoundException("User $request.email not found")

        return AuthenticationResponse(
            token = jwtService.generateToken(user)
        )
    }
}
