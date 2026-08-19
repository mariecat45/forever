package com.example.forever.domain.model

data class Note (
    val id: Long,
    val text: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
)