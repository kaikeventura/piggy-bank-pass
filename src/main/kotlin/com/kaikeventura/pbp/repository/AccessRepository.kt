package com.kaikeventura.pbp.repository

import com.kaikeventura.pbp.entity.AccessEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessRepository : JpaRepository<AccessEntity, String> {
    fun findAllByUserEmail(email: String): List<AccessEntity>
    fun findByIdAndUserEmail(id: String, email: String): AccessEntity?
}
