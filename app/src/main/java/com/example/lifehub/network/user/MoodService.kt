package com.example.lifehub.network.user

import com.example.lifehub.features.dashboard.home.data.Mood
import com.example.lifehub.features.dashboard.home.data.MoodStreak
import com.example.lifehub.network.Service
import com.example.lifehub.network.auth.FirebaseAuthService
import com.example.lifehub.network.data.MoodEntry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneOffset
import javax.inject.Inject

class MoodService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authService: FirebaseAuthService
) : Service {
    private val moodCollection = firestore.collection("mood")

    suspend fun saveMood(
        mood: Mood,
        reflection: String?,
        intensity: Float,
    ): Unit? {
        val userId = authService.currentUserId() ?: return null

        val todayEpochDay = LocalDate.now(ZoneOffset.UTC).toEpochDay()

        val moodEntry = MoodEntry(
            mood = mood,
            reflection = reflection,
            intensity = intensity
        )

        moodCollection
            .document(userId)
            .collection("entries")
            .add(moodEntry)
            .await()

        val streakDoc = moodCollection
            .document(userId)
            .collection("streak")
            .document("metadata")
            .get()
            .await()

        val currentStreak = streakDoc.toObject(MoodStreak::class.java) ?: MoodStreak()

        val updatedStreak = when {
            todayEpochDay == currentStreak.lastEntryEpochDay -> currentStreak
            todayEpochDay == currentStreak.lastEntryEpochDay + 1 -> MoodStreak(
                lastEntryEpochDay = todayEpochDay,
                currentStreak = currentStreak.currentStreak + 1,
                shouldShowModal = true
            )

            else -> MoodStreak(
                lastEntryEpochDay = todayEpochDay,
                currentStreak = 1,
                shouldShowModal = true
            )
        }

        moodCollection
            .document(userId)
            .collection("streak")
            .document("metadata")
            .set(updatedStreak)
            .await()

        return Unit
    }

    suspend fun getMoodEntries(): List<MoodEntry>? {
        val userId = authService.currentUserId() ?: return null

        return moodCollection
            .document(userId)
            .collection("entries")
            .get()
            .await()
            .documents
            .mapNotNull { it.toObject(MoodEntry::class.java) }
    }

    suspend fun getMoodStreak(): MoodStreak? {
        val userId = authService.currentUserId() ?: return null

        return moodCollection
            .document(userId)
            .collection("streak")
            .document("metadata")
            .get()
            .await()
            .toObject(MoodStreak::class.java)
    }

    suspend fun markStreakModalShown() {
        val userId = authService.currentUserId() ?: return
        val streakRef = moodCollection
            .document(userId)
            .collection("streak")
            .document("metadata")

        val current = streakRef.get().await().toObject(MoodStreak::class.java) ?: return

        val updated = current.copy(shouldShowModal = false)

        streakRef.set(updated).await()
    }
}