package com.alpha2048.jetpackcomposetest.domain.usecase

import com.alpha2048.jetpackcomposetest.common.entity.OwnerEntity
import com.alpha2048.jetpackcomposetest.common.entity.RepositoryEntity
import com.alpha2048.jetpackcomposetest.common.entity.SearchRepositoryEntity
import com.alpha2048.jetpackcomposetest.common.usecase.base.UseCaseResult
import com.alpha2048.jetpackcomposetest.domain.repository.SearchRepository
import com.alpha2048.jetpackcomposetest.feature_main.usecase.SearchRepositoryUseCaseParam
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SearchRepositoryUseCaseImplTest {

    private val query = "test"
    private val page = 1

    private val mockRepository = mockk<SearchRepository> {
        coEvery { search(q = query, page = page) } returns
                SearchRepositoryEntity(
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
                        ), RepositoryEntity(
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
    }

    private val errorRepository = mockk<SearchRepository> {
        coEvery { search(q = query, page = page) } throws Exception("エラーテスト")
    }

    //TODO: use runTest if experimental is removed

    @Test
    fun `正常系のチェック`() = runBlocking {
        val useCase = SearchRepositoryUseCaseImpl(mockRepository)
        val result = useCase.execute(SearchRepositoryUseCaseParam(query, page))

        assertThat(result).isInstanceOf(UseCaseResult.Success::class.java)

        val entity = (result as UseCaseResult.Success).data

        assertThat(entity).isInstanceOf(SearchRepositoryEntity::class.java)
        assertThat(entity.items).hasSize(2)
        assertThat(entity.items.first().id).isEqualTo(1)

        coVerify { mockRepository.search(q = query, page = page) }
    }

    @Test
    fun `APIエラー時のチェック`() = runBlocking {
        val useCase = SearchRepositoryUseCaseImpl(errorRepository)
        val result = useCase.execute(SearchRepositoryUseCaseParam(query, page))

        assertThat(result).isInstanceOf(UseCaseResult.Failure::class.java)

        val e = (result as UseCaseResult.Failure).e

        assertThat(e.message).isEqualTo("エラーテスト")

        coVerify { errorRepository.search(q = query, page = page) }
    }
}