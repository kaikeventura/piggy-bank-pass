package com.kaikeventura.pbp.error.handler

import com.kaikeventura.pbp.error.exception.AccessNotFoundException
import com.kaikeventura.pbp.error.exception.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException::class)
    fun exception(ex: UserNotFoundException) =
        ResponseError(ex.message)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AccessNotFoundException::class)
    fun exception(ex: AccessNotFoundException) =
        ResponseError(ex.message)

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UsernameNotFoundException::class)
    fun exception(ex: UsernameNotFoundException) =
        ResponseError(ex.message ?: "Unexpected error")

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun exception(ex: Exception) =
        ResponseError(ex.message ?: "Unexpected error")
}

data class ResponseError(
    val error: String
)
