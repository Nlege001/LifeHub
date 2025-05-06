package com.example.lifehub.features.di

import com.example.core.room.user.UserDao
import com.example.lifehub.features.dashboard.home.repo.DashboardFeedRepo
import com.example.lifehub.features.dashboard.home.repo.MoodRepo
import com.example.lifehub.features.dashboard.sidemenu.SideMenuRepo
import com.example.lifehub.features.profile.ProfileRepo
import com.example.lifehub.network.quoteoftheday.QuoteOfTheDayService
import com.example.lifehub.network.user.MoodService
import com.example.lifehub.network.user.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class MainRepoModule {

    @Provides
    fun provideDashboardFeedRepo(
        quoteOfTheDayService: QuoteOfTheDayService,
        ioDispatcher: CoroutineDispatcher,
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): DashboardFeedRepo =
        DashboardFeedRepo(
            quoteOfTheDayService = quoteOfTheDayService,
            ioDispatcher = ioDispatcher,
            firebaseFirestore = firebaseFirestore,
            firebaseAuth = firebaseAuth
        )

    @Provides
    fun provideProfileRepo(
        firebaseAuth: FirebaseAuth,
        userService: UserService,
        userDao: UserDao
    ): ProfileRepo = ProfileRepo(
        firebaseAuth,
        userService,
        userDao
    )

    @Provides
    fun provideSideMenuRepo(
        firebaseAuth: FirebaseAuth,
        userDao: UserDao
    ) = SideMenuRepo(firebaseAuth, userDao)

    @Provides
    fun provideMoodRepo(
        service: MoodService,
        ioDispatcher: CoroutineDispatcher,
    ): MoodRepo = MoodRepo(service, ioDispatcher)

}