package com.alpha2048.jetpackcomposetest.domain.repository

import com.alpha2048.jetpackcomposetest.entity.SearchRepositoryEntity

interface SearchRepository {
    suspend fun search(q: String, page: Int): SearchRepositoryEntity
}