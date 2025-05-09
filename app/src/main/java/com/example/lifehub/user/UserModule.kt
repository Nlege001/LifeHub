package com.example.lifehub.user

import com.example.core.room.user.UserDao
import com.example.lifehub.network.auth.FirebaseAuthService
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
        firebaseAuthService: FirebaseAuthService
    ): UserRepo = UserRepo(userDao, firebaseAuthService)
}