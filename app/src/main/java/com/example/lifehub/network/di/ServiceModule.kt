package com.example.lifehub.network.di

import com.example.lifehub.network.auth.AuthService
import com.example.lifehub.network.auth.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseAuthService(
        firebaseAuth: FirebaseAuth
    ): AuthService = FirebaseAuthService(firebaseAuth)
}