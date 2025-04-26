package com.example.lifehub.network.auth

import com.example.lifehub.network.Service
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthService : Service {
    fun signUp(email: String, password: String): Task<AuthResult>
    fun signIn(email: String, password: String): Task<AuthResult>
    fun emailVerification(actionCodeSettings: ActionCodeSettings): Task<Void>
    fun signOut()
    fun currentUserId(): String?
    fun isUserSignedIn(): Boolean
    fun getCurrentUser(): FirebaseUser?
    fun sendPasswordResetEmail(email: String, actionCodeSettings: ActionCodeSettings): Task<Void>
}