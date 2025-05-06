package com.example.lifehub.features.dashboard.home.repo

import com.example.core.data.PostResult
import com.example.core.data.ViewState
import com.example.lifehub.features.dashboard.home.data.Mood
import com.example.lifehub.network.data.MoodEntry
import com.example.lifehub.network.user.MoodService
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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

    suspend fun getMoodEntries(): ViewState<List<MoodEntry>> {
        return withContext(ioDispatcher) {
            service.getMoodEntries()?.let {
                ViewState.Content(it.sortedByDescending { it.timestamp }.takeLast(7))
            } ?: ViewState.Error()
        }

    }
}