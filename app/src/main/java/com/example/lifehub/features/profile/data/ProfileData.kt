package com.example.lifehub.features.profile.data

data class ProfileData(
    val profileBackGroundPictureUrl: ByteArray?,
    val profilePictureUrl: ByteArray?,
    val firstName: String,
    val lastName: String?,
    val email: String,
    val dob: Long,
    val memberSince: String,
    val userId: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileData

        if (dob != other.dob) return false
        if (!profileBackGroundPictureUrl.contentEquals(other.profileBackGroundPictureUrl)) return false
        if (!profilePictureUrl.contentEquals(other.profilePictureUrl)) return false
        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (memberSince != other.memberSince) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dob.hashCode()
        result = 31 * result + (profileBackGroundPictureUrl?.contentHashCode() ?: 0)
        result = 31 * result + (profilePictureUrl?.contentHashCode() ?: 0)
        result = 31 * result + firstName.hashCode()
        result = 31 * result + (lastName?.hashCode() ?: 0)
        result = 31 * result + email.hashCode()
        result = 31 * result + memberSince.hashCode()
        result = 31 * result + userId.hashCode()
        return result
    }
}