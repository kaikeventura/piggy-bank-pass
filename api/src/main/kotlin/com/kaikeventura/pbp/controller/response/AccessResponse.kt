package com.kaikeventura.pbp.controller.response

import java.util.UUID

data class AccessResponse(
    val id: UUID,
    val account: String,
    val login: String,
    val password: String
)
