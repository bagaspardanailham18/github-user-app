package com.bagas.githubuserapp.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bagas.githubuserapp.DummyData
import com.bagas.githubuserapp.MainDispatcherRule
import com.bagas.githubuserapp.data.GithubUserAppRepository
import com.bagas.githubuserapp.data.remote.responses.DetailUserResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.bagas.githubuserapp.data.remote.Result
import com.bagas.githubuserapp.data.remote.responses.ListRepositoriesForAUserResponse
import com.bagas.githubuserapp.data.remote.responses.ListRepositoriesForAUserResponseItem
import com.bagas.githubuserapp.getOrAwaitValue
import org.junit.Assert.*
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
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubUserAppRepository: GithubUserAppRepository
    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(githubUserAppRepository)
    }

    @Test
    fun `when Get Detail User Should Return Success`() {
        val dummyUserDetail = DummyData.generateDummyUserDetailResponse()
        val expectedData = MutableLiveData<Result<DetailUserResponse>>()
        expectedData.value = Result.Success(dummyUserDetail)

        `when`(githubUserAppRepository.getDetailUser("username")).thenReturn(expectedData)

        val actual = detailViewModel.getDetailUser("username").getOrAwaitValue()
        verify(githubUserAppRepository).getDetailUser("username")
        assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(dummyUserDetail.login, (actual as Result.Success).data.login)
    }

    @Test
    fun `when Get Repos For A User Should Return Success`() {
        val dummyRepos = DummyData.generateDummyUserReposResponse()
        val expectedRepos = MutableLiveData<Result<ListRepositoriesForAUserResponse>>()
        expectedRepos.value = Result.Success(dummyRepos)

        `when`(githubUserAppRepository.getReposForAUser("username")).thenReturn(expectedRepos)

        val actual = detailViewModel.getUserRepos("username").getOrAwaitValue()
        verify(githubUserAppRepository).getReposForAUser("username")
        assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(dummyRepos, (actual as Result.Success).data)
        assertEquals(dummyRepos.listRepositoriesForAUserResponse?.size, (actual as Result.Success).data.listRepositoriesForAUserResponse?.size)
    }

}