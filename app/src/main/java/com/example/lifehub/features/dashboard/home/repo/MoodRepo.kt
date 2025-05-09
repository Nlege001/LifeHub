package com.example.lifehub.features.dashboard.home.repo

import com.example.core.data.PostResult
import com.example.core.data.ViewState
import com.example.lifehub.features.dashboard.home.data.Mood
import com.example.lifehub.features.dashboard.home.data.MoodData
import com.example.lifehub.network.user.MoodService
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@ViewModelScoped
class MoodRepo @Inject constructor(
    private val service: MoodService,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun saveMood(
        mood: Mood,
        reflection: String?,
        intensity: Float,
    ): PostResult<Unit> {
        service.saveMood(
            mood,
            reflection,
            intensity
        ) ?: return PostResult.Error()

        return PostResult.Success(Unit)
    }

    suspend fun getMoodEntries(): ViewState<MoodData> {
        return withContext(ioDispatcher) {
            val entries = async { service.getMoodEntries() }
            val streak = async { service.getMoodStreak() }

            val entriesDeferred = entries.await()
            val streakDeferred = streak.await()

            entriesDeferred?.let {
                val data = it
                    .sortedByDescending { entry ->
                        Instant.ofEpochMilli(entry.timestamp)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    .take(7)
                ViewState.Content(
                    MoodData(
                        entries = data,
                        streak = streakDeferred
                    )
                )
            } ?: ViewState.Error()
        }
    }

    suspend fun markStreakModalShown() {
        service.markStreakModalShown()
    }
}