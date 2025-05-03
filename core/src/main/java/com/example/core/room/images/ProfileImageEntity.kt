package com.example.core.room.images

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_images")
data class ProfileImageEntity(
    @PrimaryKey val userId: String,
    val profilePicture: ByteArray? = null,
    val profileBackground: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileImageEntity

        if (userId != other.userId) return false
        if (!profilePicture.contentEquals(other.profilePicture)) return false
        if (!profileBackground.contentEquals(other.profileBackground)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + (profilePicture?.contentHashCode() ?: 0)
        result = 31 * result + (profileBackground?.contentHashCode() ?: 0)
        return result
    }
}