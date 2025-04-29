package com.example.lifehub.network.di

import com.example.lifehub.network.auth.AuthService
import com.example.lifehub.network.auth.FirebaseAuthService
import com.example.lifehub.network.user.UserService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideUserService(
        firebaseFireStore: FirebaseFirestore
    ): UserService = UserService(firebaseFireStore)
}