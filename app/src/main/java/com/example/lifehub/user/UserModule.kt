package com.example.lifehub.user

import com.example.core.room.user.UserDao
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    fun provideUserRepo(
        userDao: UserDao,
        firebaseAuth: FirebaseAuth
    ): UserRepo = UserRepo(userDao, firebaseAuth)
}