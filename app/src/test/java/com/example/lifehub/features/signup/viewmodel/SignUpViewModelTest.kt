package com.example.lifehub.features.signup.viewmodel

import com.example.core.data.PostResult
import com.example.lifehub.features.signup.repo.SignUpRepo
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
class SignUpViewModelTest {

    private val repo: SignUpRepo = mock()
    private val testDispatcher = StandardTestDispatcher()
    lateinit var vm: SignUpViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = SignUpViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testHappyPath() = runTest {
        // given
        val expected = PostResult.Success(Unit)
        whenever(repo.signUp(any(), any())).thenReturn(PostResult.Success(Unit))

        // when
        vm.signUp("", "")
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.postResult.value)
    }

    @Test
    fun tesError() = runTest {
        // given
        val expected = PostResult.Error()
        whenever(repo.signUp(any(), any())).thenReturn(PostResult.Error())

        // when
        vm.signUp("", "")
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.postResult.value)
    }

}