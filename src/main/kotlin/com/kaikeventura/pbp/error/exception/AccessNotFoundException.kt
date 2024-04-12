package com.kaikeventura.pbp.error.exception

data class AccessNotFoundException(
    override val message: String
) : RuntimeException(message)
