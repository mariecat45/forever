package com.example.forever.domain.model

data class Note (
    val id: Long,
    val ownerId: String,
    val text: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
)