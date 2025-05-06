package com.example.lifehub.network.user

import com.example.lifehub.features.dashboard.home.data.Mood
import com.example.lifehub.network.Service
import com.example.lifehub.network.data.MoodEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MoodService @Inject constructor(
    firebaseFirestore: FirebaseFirestore,
    firebaseAuth: FirebaseAuth
) : Service {
    private val moodCollection = firebaseFirestore.collection("mood")
    private val currentId = firebaseAuth.currentUser?.uid

    suspend fun saveMood(
        mood: Mood,
        reflection: String?,
        intensity: Float,
    ): Unit? {
        val moodEntry = MoodEntry(
            mood = mood,
            reflection = reflection,
            intensity = intensity
        )
        val result = currentId?.let {
            moodCollection
                .document(currentId)
                .collection("entries")
                .add(moodEntry)
                .await()
        }
        return if (result != null) Unit else null
    }

    suspend fun getMoodEntries(): List<MoodEntry>? {
        return currentId?.let { uid ->
            moodCollection
                .document(uid)
                .collection("entries")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(MoodEntry::class.java) }
        }
    }
}