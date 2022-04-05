package com.alpha2048.jetpackcomposetest.feature_main.viewmodel

import app.cash.turbine.test
import com.alpha2048.jetpackcomposetest.common.entity.OwnerEntity
import com.alpha2048.jetpackcomposetest.common.entity.RepositoryEntity
import com.alpha2048.jetpackcomposetest.common.entity.SearchRepositoryEntity
import com.alpha2048.jetpackcomposetest.common.usecase.base.UseCaseResult
import com.alpha2048.jetpackcomposetest.feature_main.usecase.SearchRepositoryUseCase
import com.alpha2048.jetpackcomposetest.feature_main.usecase.SearchRepositoryUseCaseParam
import com.alpha2048.jetpackcomposetest.feature_main.viewmodel.HomeScreenViewModel.Companion.INITIAL_WORD
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeScreenViewModelTest {
    private val searchWord = "test"
    private val param = SearchRepositoryUseCaseParam(
        q = searchWord,
        page = 1
    )

    private val defaultParam = SearchRepositoryUseCaseParam(
        q = INITIAL_WORD,
        page = 1
    )

    private val mockUseCase = mockk<SearchRepositoryUseCase> {
        coEvery { execute(param) } returns
            UseCaseResult.Success(
                data = SearchRepositoryEntity(
                    totalCount = 2,
                    incompleteResults = true,
                    items = listOf(
                        RepositoryEntity(
                            id = 1,
                            name = "1個目",
                            htmlUrl = "https://placehold.jp/150x150.png",
                            stargazersCount = 111,
                            owner = OwnerEntity(
                                id = 11,
                                avatarUrl = "https://placehold.jp/150x150.png"
                            )
                        ),
                        RepositoryEntity(
                            id = 2,
                            name = "2個目",
                            htmlUrl = "https://placehold.jp/150x150.png",
                            stargazersCount = 222,
                            owner = OwnerEntity(
                                id = 12,
                                avatarUrl = "https://placehold.jp/150x150.png"
                            )
                        )
                    )
                )
            )
        coEvery { execute(defaultParam) } returns
            UseCaseResult.Success(
                data = SearchRepositoryEntity(
                    totalCount = 0,
                    incompleteResults = false,
                    items = listOf()
                )
            )
    }

    private val errorUseCase = mockk<SearchRepositoryUseCase> {
        coEvery { execute(param) } returns
            UseCaseResult.Failure(
                e = Exception("エラーテスト")
            )
        coEvery { execute(defaultParam) } returns
            UseCaseResult.Success(
                data = SearchRepositoryEntity(
                    totalCount = 0,
                    incompleteResults = false,
                    items = listOf()
                )
            )
    }

    // https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    // TODO: use runTest if experimental is removed

    @Test
    fun `正常系のチェック`() = runBlocking {
        val viewModel = HomeScreenViewModel(mockUseCase)
        delay(1000)

        viewModel.uiState.test {
            viewModel.search(searchWord)
            delay(1000)

            coVerify { mockUseCase.execute(param) }

            val state1 = awaitItem()
            val state2 = awaitItem()
            val state3 = awaitItem()

            assertThat(state1.state).isInstanceOf(HomeScreenViewModel.UiState.Loaded::class.java)
            assertThat(state2.state).isInstanceOf(HomeScreenViewModel.UiState.Loading::class.java)
            assertThat(state3.state).isInstanceOf(HomeScreenViewModel.UiState.Loaded::class.java)
            assertThat(state3.items).hasSize(2)
            assertThat(state3.isComplete).isEqualTo(false)

            cancelAndConsumeRemainingEvents()
        }

        // Turbineを使わない場合は以下

//        viewModel.search(searchWord)
//        delay(1000)
//
//        coVerify { mockUseCase.execute(param) }
//
//        val state = viewModel.uiState.value
//
//        assertThat(state.state).isInstanceOf(HomeScreenViewModel.UiState.Loaded::class.java)
//        assertThat(state.items).hasSize(2)
//        assertThat(state.isComplete).isEqualTo(false)
    }

    @Test
    fun `APIエラー時のチェック`() = runBlocking {
        val viewModel = HomeScreenViewModel(errorUseCase)
        delay(1000)

        viewModel.uiState.test {
            viewModel.search(searchWord)
            delay(1000)

            coVerify { errorUseCase.execute(param) }

            val state1 = awaitItem()
            val state2 = awaitItem()
            val state3 = awaitItem()

            assertThat(state1.state).isInstanceOf(HomeScreenViewModel.UiState.Loaded::class.java)
            assertThat(state2.state).isInstanceOf(HomeScreenViewModel.UiState.Loading::class.java)
            assertThat(state3.state).isInstanceOf(HomeScreenViewModel.UiState.Error::class.java)
            assertThat((state3.state as HomeScreenViewModel.UiState.Error).e.message).isEqualTo("エラーテスト")

            cancelAndConsumeRemainingEvents()
        }

        // Turbineを使わない場合は以下

//        viewModel.search(searchWord)
//        delay(1000)
//
//        coVerify { errorUseCase.execute(param) }
//
//        val state = viewModel.uiState.value
//
//        assertThat(state.state).isInstanceOf(HomeScreenViewModel.UiState.Error::class.java)
//        assertThat((state.state as HomeScreenViewModel.UiState.Error).e.message).isEqualTo("エラーテスト")
    }
}
