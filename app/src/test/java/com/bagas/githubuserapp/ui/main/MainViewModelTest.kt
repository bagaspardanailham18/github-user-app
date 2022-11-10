package com.bagas.githubuserapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bagas.githubuserapp.DummyData
import com.bagas.githubuserapp.MainDispatcherRule
import com.bagas.githubuserapp.data.GithubUserAppRepository
import com.bagas.githubuserapp.data.local.UserEntity
import com.bagas.githubuserapp.data.remote.responses.ListUserResponseItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import com.bagas.githubuserapp.data.remote.Result
import com.bagas.githubuserapp.data.remote.responses.SearchUserResponse
import com.bagas.githubuserapp.getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubUserAppRepository: GithubUserAppRepository
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(githubUserAppRepository)
    }

    @Test
    fun `when Get User List Should Not Null and Return Success`() = runTest {
        val dummyUsers = DummyData.generateDummyUserEntity()
        val expectedUsers = MutableLiveData<Result<List<UserEntity>>>()
        expectedUsers.value = Result.Success(dummyUsers)

        `when`(githubUserAppRepository.getAllUsers()).thenReturn(expectedUsers)

        val actual = mainViewModel.getAllUsers().getOrAwaitValue()
        verify(githubUserAppRepository).getAllUsers()
        assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(dummyUsers, (actual as Result.Success).data)
        assertEquals(dummyUsers.size, (actual as Result.Success).data.size)
    }

    @Test
    fun `when Get Searched User List Should Not Null and Return Success`() = runTest {
        val dummyUsers = DummyData.generateDummySearchedUsersResponse()
        val expectedUsers = MutableLiveData<Result<SearchUserResponse>>()
        expectedUsers.value = Result.Success(dummyUsers)

        `when`(githubUserAppRepository.getSearchedUsers("query")).thenReturn(expectedUsers)

        val actual = mainViewModel.getSearchedUsers("query").getOrAwaitValue()
        verify(githubUserAppRepository).getSearchedUsers("query")
        assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(dummyUsers.items, (actual as Result.Success).data.items)
        assertEquals(dummyUsers.items?.size, (actual as Result.Success).data.items?.size)
    }
}