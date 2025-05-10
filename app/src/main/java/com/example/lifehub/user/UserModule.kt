package com.example.lifehub.user

import com.example.core.room.user.UserDao
import com.example.lifehub.network.auth.FirebaseAuthService
import com.example.lifehub.network.user.UserService
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
        firebaseAuthService: FirebaseAuthService,
        userService: UserService
    ): UserRepo = UserRepo(userDao, firebaseAuthService, userService)
}