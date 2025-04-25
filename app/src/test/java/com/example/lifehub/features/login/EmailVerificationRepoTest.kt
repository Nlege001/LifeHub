package com.example.lifehub.features.login

import com.example.core.data.PostResult
import com.example.lifehub.features.auth.signup.repo.EmailVerificationRepo
import com.example.lifehub.network.auth.FirebaseAuthService
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class EmailVerificationRepoTest {

    private val firebaseAuthService: FirebaseAuthService = mock()
    private val testDispatcher = StandardTestDispatcher()
    lateinit var repo: EmailVerificationRepo

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repo = EmailVerificationRepo(firebaseAuthService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testHappyPath_sendEmailVerification() = runTest {
        // given
        val expected = PostResult.Success(Unit)
        whenever(firebaseAuthService.emailVerification()).thenReturn(Tasks.forResult(null))

        // when
        val result = repo.sendEmailVerification()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun testError_sendEmailVerification() = runTest {
        // given
        val expected =
            PostResult.Error("EmailVerificationRepo failed to send verification email java.lang.Exception: some error")
        whenever(firebaseAuthService.emailVerification()).thenReturn(
            Tasks.forException(Exception("some error"))
        )

        // when
        val result = repo.sendEmailVerification()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun testHappyPath_isEmailVerified() = runTest {
        // given
        val expected = PostResult.Success<Boolean>(true)
        val mockFirebaseUser = mock<FirebaseUser>()

        whenever(firebaseAuthService.getCurrentUser()).thenReturn(mockFirebaseUser)
        whenever(mockFirebaseUser.reload()).thenReturn(Tasks.forResult(null))
        whenever(mockFirebaseUser.isEmailVerified).thenReturn(true)

        // when
        val result = repo.isEmailVerified()

        // then
        assertEquals(expected, result)
    }

    @Test
    fun testError_isEmailVerified() = runTest {
        // given
        val expected =
            PostResult.Error("No user signed in")
        val mockFirebaseUser = mock<FirebaseUser>()

        whenever(firebaseAuthService.getCurrentUser()).thenReturn(null)
        whenever(mockFirebaseUser.reload()).thenReturn(Tasks.forResult(null))
        whenever(mockFirebaseUser.isEmailVerified).thenReturn(true)

        // when
        val result = repo.isEmailVerified()
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, result)
    }

}