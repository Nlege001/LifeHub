package com.example.lifehub.network.auth

import com.example.lifehub.network.Service
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthService : Service {
    fun signUp(email: String, password: String): Task<AuthResult>
    fun signIn(email: String, password: String): Task<AuthResult>
    fun signOut()
    fun currentUserId(): String?
    fun isUserSignedIn(): Boolean
}