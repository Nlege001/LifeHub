package com.example.lifehub.features.dashboard.home

import com.example.core.data.ViewState
import com.example.core.utils.fetch
import com.example.lifehub.network.quoteoftheday.QuoteOfTheDayService
import com.example.lifehub.network.quoteoftheday.data.QuoteFetchType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ViewModelScoped
class DashboardFeedRepo @Inject constructor(
    private val quoteOfTheDayService: QuoteOfTheDayService,
    private val ioDispatcher: CoroutineDispatcher,
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {
    suspend fun getData(): ViewState<DashboardFeedData> {
        val qod = fetch(
            api = { quoteOfTheDayService.getQuote(QuoteFetchType.TODAY.label) },
            ioDispatcher = ioDispatcher
        )?.firstOrNull()

        val firstName = getFirstName()

        return ViewState.Content(
            DashboardFeedData(firstName = firstName, quoteOfTheDay = qod)
        )
    }

    suspend fun getFirstName(): String? {
        val currentUserId = firebaseAuth.currentUser?.uid ?: return null

        return try {
            val snapshot = firebaseFirestore
                .collection("users")
                .document(currentUserId)
                .get()
                .await()

            snapshot.getString("firstName")
        } catch (e: Exception) {
            null
        }
    }
}