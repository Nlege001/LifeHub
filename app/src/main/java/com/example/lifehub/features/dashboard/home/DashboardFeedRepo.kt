package com.example.lifehub.features.dashboard.home

import android.util.Log
import com.example.core.data.ViewState
import com.example.core.utils.fetch
import com.example.lifehub.features.dashboard.home.data.Greeting
import com.example.lifehub.features.dashboard.home.data.TimeOfDay
import com.example.lifehub.network.quoteoftheday.QuoteOfTheDayService
import com.example.lifehub.network.quoteoftheday.data.QuoteFetchType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import java.util.Calendar
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
        val greeting = firstName?.let {
            getRandomGreeting()?.message?.replace("{userName}", it)
        } ?: run {
            Greeting.getFallBackGreeting(firstName).message
        }

        return ViewState.Content(
            DashboardFeedData(greeting = greeting, quoteOfTheDay = qod)
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

    suspend fun getRandomGreeting(): Greeting? {
        val firestore = FirebaseFirestore.getInstance()

        val timeOfDay = getTimeOfDay()

        val querySnapshot = firestore.collection("greetings")
            .whereEqualTo("timeOfDay", timeOfDay.label)
            .get()
            .await()

        val documents = querySnapshot.documents
        if (documents.isNotEmpty()) {
            val randomDoc = documents.random()
            val x = randomDoc.toObject(Greeting::class.java)
            Log.d("Naol", "return is $x")
            return x
        }
        return null
    }

    fun getTimeOfDay(): TimeOfDay {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 5..11 -> TimeOfDay.MORNING
            in 12..16 -> TimeOfDay.AFTERNOON
            in 17..20 -> TimeOfDay.EVENING
            else -> TimeOfDay.NIGHT
        }
    }
}