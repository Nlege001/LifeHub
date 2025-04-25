package com.example.lifehub.features.signup.repo

import com.example.core.data.PostResult
import com.example.lifehub.features.auth.signup.repo.SignUpRepo
import com.example.lifehub.network.auth.FirebaseAuthService
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpRepoTest {

    private val firebaseAuthService: FirebaseAuthService = mock()
    private val testDispatcher = StandardTestDispatcher()
    lateinit var repo: SignUpRepo

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repo = SignUpRepo(firebaseAuthService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testHappyPath() = runTest {
        // given
        val expected = PostResult.Success(Unit)
        val mockAuthResult = mock<AuthResult>()
        whenever(
            firebaseAuthService.signUp(
                any(),
                any()
            )
        ).thenReturn(Tasks.forResult(mockAuthResult))

        // when
        testDispatcher.scheduler.advanceUntilIdle()
        val result = repo.signUp("", "")

        // then
        assertEquals(expected, result)
    }

    @Test
    fun testErrorPath() = runTest {
        // given
        val expected = PostResult.Error("LoginRepo failed with java.lang.Exception: some error")
        whenever(
            firebaseAuthService.signUp(
                any(),
                any()
            )
        ).thenReturn(Tasks.forException(Exception("some error")))

        // when
        testDispatcher.scheduler.advanceUntilIdle()
        val result = repo.signUp("", "")

        // then
        assertEquals(expected, result)
    }
}