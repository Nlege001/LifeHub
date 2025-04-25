package com.example.lifehub.features.login

import com.example.core.data.PostResult
import com.example.lifehub.features.auth.login.LoginRepo
import com.example.lifehub.features.auth.login.LoginViewModel
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
class LoginViewModelTest {

    private val repo: LoginRepo = mock()
    private val testDispatcher = StandardTestDispatcher()
    lateinit var vm: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        vm = LoginViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testContent() = runTest {
        // given
        val expected = PostResult.Success(Unit)
        whenever(repo.signIn(any(), any())).thenReturn(PostResult.Success(Unit))

        // when
        vm.signIn("test@email.com", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.postResult.value)
    }

    @Test
    fun testError() = runTest {
        // given
        val expected = PostResult.Error("")
        whenever(repo.signIn(any(), any())).thenReturn(PostResult.Error(""))

        // when
        vm.signIn("test@email.com", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        // then
        assertEquals(expected, vm.postResult.value)
    }

}