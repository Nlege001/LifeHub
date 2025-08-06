package com.example.lifehub.features.moodhabits

import com.example.core.data.ViewState
import com.example.lifehub.network.data.MoodEntry
import com.example.lifehub.network.user.MoodService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class MoodHabitsRepo @Inject constructor(
    private val moodService: MoodService
) {
    suspend fun getMoodEntries(): ViewState<List<MoodEntry>> {
        return moodService.getMoodEntries()?.let {
            ViewState.Content(it)
        } ?: ViewState.Error()
    }
}