package com.example.lifehub.network.user

import com.example.lifehub.network.Service
import com.example.lifehub.network.data.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(
    firestore: FirebaseFirestore
) : Service {
    private val usersCollection = firestore.collection("users")

    suspend fun saveUserProfile(userProfile: UserProfile) {
        usersCollection
            .document(userProfile.userId)
            .set(userProfile)
            .await()
    }

    suspend fun getUserProfile(userId: String): UserProfile? {
        val snapshot = usersCollection
            .document(userId)
            .get()
            .await()

        return snapshot.toObject(UserProfile::class.java)
    }

    suspend fun updateProfilePictureUrl(userId: String, url: String) {
        usersCollection
            .document(userId)
            .update("profilePictureUrl", url)
            .await()
    }

    suspend fun updateBackgroundPictureUrl(userId: String, url: String) {
        usersCollection
            .document(userId)
            .update("BackgroundPictureUrl", url)
            .await()
    }
}