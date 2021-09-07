package com.alpha2048.jetpackcomposetest.domain.repository

import com.alpha2048.jetpackcomposetest.data.model.SearchRepositoryResponse

interface SearchRepository {
    suspend fun search(q: String, page: Int): SearchRepositoryResponse
}