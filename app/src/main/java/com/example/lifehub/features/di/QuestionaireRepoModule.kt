package com.example.lifehub.features.di

import com.example.lifehub.features.questionaire.repo.UserRepo
import com.example.lifehub.network.user.UserService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class QuestionaireRepoModule {

    @Provides
    fun provideUserRepo(
        userService: UserService,
        auth: FirebaseAuth
    ): UserRepo {
        return UserRepo(userService, auth)
    }
}