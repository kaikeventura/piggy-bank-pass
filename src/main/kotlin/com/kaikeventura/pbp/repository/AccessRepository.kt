package com.kaikeventura.pbp.repository

import com.kaikeventura.pbp.entity.AccessEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AccessRepository : JpaRepository<AccessEntity, UUID> {
    fun findAllByUserEmail(email: String): List<AccessEntity>
}
