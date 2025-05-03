package com.example.core.room.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.room.images.ProfileImageEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE userId = :userId LIMIT 1")
    suspend fun getUser(userId: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserEntity)

    @Query("UPDATE user SET hasCompletedQuestionaire = :completed WHERE userId = :userId")
    suspend fun updateQuestionnaireStatus(userId: String, completed: Boolean)

    @Query("SELECT profileBackground FROM profile_images WHERE userId = :userId")
    suspend fun getProfileBackground(userId: String): ByteArray?

    @Query("SELECT profilePicture FROM profile_images WHERE userId = :userId")
    suspend fun getProfilePicture(userId: String): ByteArray?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfileImages(entity: ProfileImageEntity)

    @Query("UPDATE profile_images SET profilePicture = :picture WHERE userId = :userId")
    suspend fun updateProfilePicture(userId: String, picture: ByteArray)

    @Query("UPDATE profile_images SET profileBackground = :background WHERE userId = :userId")
    suspend fun updateProfileBackground(userId: String, background: ByteArray)
}