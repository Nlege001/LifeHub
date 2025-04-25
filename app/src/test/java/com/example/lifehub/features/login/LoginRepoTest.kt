package com.example.lifehub.features.login

import com.example.core.data.PostResult
import com.example.lifehub.network.auth.FirebaseAuthService
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginRepoTest {

    private val firebaseAuthService: FirebaseAuthService = mock()
    lateinit var repo: LoginRepo

    @Before
    fun setUp() {
        repo = LoginRepo(firebaseAuthService)
    }

    @Test
    fun testContent() = runTest {
        // given
        val expected = PostResult.Success(Unit)
        val mockAuthResult = mock<AuthResult>()
        whenever(firebaseAuthService.signIn(any(), any()))
            .thenReturn(Tasks.forResult(mockAuthResult))

        // when
        val result = repo.signIn("test@email.com", "password123")

        // then
        assertEquals(expected, result)
    }

    @Test
    fun testError() = runTest {
        // given
        val expected = PostResult.Error("LoginRepo failed with java.lang.Exception: some error")
        whenever(firebaseAuthService.signIn(any(), any()))
            .thenReturn(Tasks.forException(Exception("some error")))

        // when
        val result = repo.signIn("test@email.com", "password123")

        // then
        assertEquals(expected, result)
    }

}