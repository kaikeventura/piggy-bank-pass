package com.kaikeventura.pbp.controller.request

data class AccessRequest(
    val account: String,
    val login: String,
    val password: String
)
