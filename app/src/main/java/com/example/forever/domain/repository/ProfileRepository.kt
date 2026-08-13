package com.example.forever.domain.repository

import com.example.forever.domain.model.Profile
import com.example.forever.domain.model.UserId
import com.example.forever.domain.model.UserName
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun observeProfile(): Flow<Profile?>
    suspend fun createProfile(profile: Profile)
    suspend fun updateName(profileId: UserId, name: UserName, updatedAt: Long)
}