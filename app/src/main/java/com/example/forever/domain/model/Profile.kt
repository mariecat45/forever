package com.example.forever.domain.model

data class Profile (
    val userName: UserName,
    val userId: UserId,
    val createdAt: Long = System.currentTimeMillis()
)