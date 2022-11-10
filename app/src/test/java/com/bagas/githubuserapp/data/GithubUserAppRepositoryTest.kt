package com.bagas.githubuserapp.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.bagas.githubuserapp.DummyData
import com.bagas.githubuserapp.MainDispatcherRule
import com.bagas.githubuserapp.data.local.GithubUserRoomDatabase
import com.bagas.githubuserapp.data.local.UserEntity
import com.bagas.githubuserapp.data.remote.ApiService
import com.bagas.githubuserapp.data.remote.Result
import com.bagas.githubuserapp.data.remote.responses.*
import com.bagas.githubuserapp.getOrAwaitValue
import com.bagas.githubuserapp.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GithubUserAppRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var githubUserAppRepository: GithubUserAppRepository

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var githubUserRoomDatabase: GithubUserRoomDatabase

    private val context = Mockito.mock(Context::class.java)

    @Before
    fun setUp() {
        apiService = FakeApiService()
        githubUserRoomDatabase = Room.inMemoryDatabaseBuilder(context, GithubUserRoomDatabase::class.java).build()
        githubUserAppRepository = GithubUserAppRepository(apiService, githubUserRoomDatabase)
    }

    @Test
    fun `when getAllUsers is Success`() = runTest {
        val dummyUsers = DummyData.generateDummyUserEntity()
        val expectedUsers = MutableLiveData<Result<List<UserEntity>>>()
        expectedUsers.value = Result.Success(dummyUsers)

        val actualUsers = githubUserAppRepository.getAllUsers().getOrAwaitValue()

        assertNotNull(actualUsers)
        assertEquals(dummyUsers.size, (actualUsers as Result.Success).data.size)
    }

    @Test
    fun `when getSearchedUsers is Success`() = runTest {
        val dummyUsers = DummyData.generateDummySearchedUsersResponse()
        val expectedUsers = MutableLiveData<Result<SearchUserResponse>>()
        expectedUsers.value = Result.Success(dummyUsers)

        val actual = githubUserAppRepository.getSearchedUsers("q").getOrAwaitValue()

        assertNotNull(actual)
        assertEquals(dummyUsers.items?.size, (actual as Result.Success).data.items?.size)
    }

    @Test
    fun `when getDetailUser is Success`() = runTest {
        val dummyDetail = DummyData.generateDummyUserDetailResponse()
        val expected = MutableLiveData<Result<DetailUserResponse>>()
        expected.value = Result.Success(dummyDetail)

        val actual = githubUserAppRepository.getDetailUser("username").getOrAwaitValue()

        assertNotNull(actual)
        assertEquals(dummyDetail.login, (actual as Result.Success).data.login)
    }

    @Test
    fun `when getUserRespos is Success`() = runTest {
        val dummyRepos = DummyData.generateDummyUserReposResponse()
        val expected = MutableLiveData<Result<ListRepositoriesForAUserResponse>>()
        expected.value = Result.Success(dummyRepos)

        val actual = githubUserAppRepository.getReposForAUser("username").getOrAwaitValue()

        assertNotNull(actual)
        assertEquals(dummyRepos.listRepositoriesForAUserResponse?.size, (actual as Result.Success).data.listRepositoriesForAUserResponse?.size)
    }
}