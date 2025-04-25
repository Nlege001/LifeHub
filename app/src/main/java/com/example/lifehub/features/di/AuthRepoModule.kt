package com.example.lifehub.features.di

import com.example.lifehub.features.login.LoginRepo
import com.example.lifehub.features.signup.SignUpRepo
import com.example.lifehub.network.auth.FirebaseAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthRepoModule {

    @Provides
    fun provideLoginRepo(firebaseAuthService: FirebaseAuthService): LoginRepo =
        LoginRepo(firebaseAuthService)

    @Provides
    fun provideSignUpRepo(firebaseAuthService: FirebaseAuthService): SignUpRepo =
        SignUpRepo(firebaseAuthService)

}