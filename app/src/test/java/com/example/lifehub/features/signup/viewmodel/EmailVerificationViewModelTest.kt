package com.example.lifehub.features.signup.viewmodel

import com.example.core.data.PostResult
import com.example.lifehub.features.signup.repo.EmailVerificationRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class EmailVerificationViewModelTest {

    private val repo: EmailVerificationRepo = mock()
    private val testDispatcher = StandardTestDispatcher()
    lateinit var vm: EmailVerificationViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = EmailVerificationViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testHappyPath_sendEmailVerification() = runTest {
        // given
        val expected = PostResult.Success(Unit)
        whenever(repo.sendEmailVerification()).thenReturn(PostResult.Success(Unit))

        // when
        vm.sendEmailVerification()
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.postResult.value)
    }

    @Test
    fun testErrorPath_sendEmailVerification() = runTest {
        // given
        val expected = PostResult.Error()
        whenever(repo.sendEmailVerification()).thenReturn(PostResult.Error())

        // when
        vm.sendEmailVerification()
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.postResult.value)
    }

    @Test
    fun testHappyPath_resendVerification() = runTest {
        // given
        val expected = PostResult.Success(Unit)
        whenever(repo.sendEmailVerification()).thenReturn(PostResult.Success(Unit))

        // when
        vm.resendVerification()
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.postResult.value)
    }

    @Test
    fun testErrorPath_resendVerification() = runTest {
        // given
        val expected = PostResult.Error()
        whenever(repo.sendEmailVerification()).thenReturn(PostResult.Error())

        // when
        vm.resendVerification()
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.postResult.value)
    }

    @Test
    fun testHappyPath_isEmailVerified() = runTest {
        // given
        val expected = PostResult.Success(true)
        whenever(repo.isEmailVerified()).thenReturn(PostResult.Success(true))

        // when
        vm.isEmailVerified()
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.isEmailVerified.value)
    }

    @Test
    fun testEmailNotVerified() = runTest {
        // given
        val expected = PostResult.Success(false)
        whenever(repo.isEmailVerified()).thenReturn(PostResult.Success(false))

        // when
        vm.isEmailVerified()
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.isEmailVerified.value)
    }

}