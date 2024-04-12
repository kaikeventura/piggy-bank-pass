package com.kaikeventura.pbp.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

private const val ALGORITHM = "AES"

@Service
class EncoderService(
    @Value("\${encryption.key}") private val encryptionKey: String
) {

    private val key: ByteArray = encryptionKey.toByteArray()

    fun encrypt(value: String): String =
        runCatching {
            val key: Key = SecretKeySpec(key, ALGORITHM)
            val cipher: Cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val encryptedBytes: ByteArray = cipher.doFinal(value.toByteArray())
            Base64.getEncoder().encodeToString(encryptedBytes)
        }.onFailure {
            throw RuntimeException("Error encrypting value", it)
        }.getOrThrow()

    fun decrypt(encryptedValue: String): String =
        runCatching {
            val key: Key = SecretKeySpec(key, ALGORITHM)
            val cipher: Cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, key)
            val decryptedBytes: ByteArray = cipher.doFinal(Base64.getDecoder().decode(encryptedValue))
            String(decryptedBytes)
        }.onFailure {
            throw RuntimeException("Error decrypting value", it)
        }.getOrThrow()
    }
