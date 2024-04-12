package com.kaikeventura.pbp.error.exception

data class UserNotFoundException(
    override val message: String
) : RuntimeException(message)
