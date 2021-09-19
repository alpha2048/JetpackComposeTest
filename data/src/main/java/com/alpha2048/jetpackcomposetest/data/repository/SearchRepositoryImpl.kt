package com.alpha2048.jetpackcomposetest.data.repository

import com.alpha2048.jetpackcomposetest.data.api.GithubApiInterface
import com.alpha2048.jetpackcomposetest.common.entity.SearchRepositoryEntity
import com.alpha2048.jetpackcomposetest.domain.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val retrofitApi: GithubApiInterface): SearchRepository {
    override suspend fun search(q: String, page: Int): SearchRepositoryEntity = withContext(Dispatchers.IO) {
        return@withContext retrofitApi.getGithubRepository(q, page).toEntity()
    }
}