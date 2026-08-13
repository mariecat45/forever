package com.example.forever.domain.model

data class Profile (
    val userName: UserName,
    val createdAt: Long = System.currentTimeMillis()
)