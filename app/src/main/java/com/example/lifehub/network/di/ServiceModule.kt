package com.example.lifehub.network.di

import com.example.lifehub.features.todo.network.TodoService
import com.example.lifehub.network.auth.AuthService
import com.example.lifehub.network.auth.FirebaseAuthService
import com.example.lifehub.network.user.MoodService
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

    @Provides
    fun provideMoodService(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuthService: FirebaseAuthService
    ): MoodService = MoodService(firebaseFirestore, firebaseAuthService)

    @Provides
    fun provideTodoService(
        service: FirebaseAuthService,
        firebaseFirestore: FirebaseFirestore
    ): TodoService = TodoService(service, firebaseFirestore)
}