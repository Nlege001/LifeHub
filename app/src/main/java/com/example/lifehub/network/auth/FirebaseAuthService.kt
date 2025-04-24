package com.example.lifehub.network.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthService {

    override fun signUp(
        email: String,
        password: String
    ): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override fun signIn(
        email: String,
        password: String
    ): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun currentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}