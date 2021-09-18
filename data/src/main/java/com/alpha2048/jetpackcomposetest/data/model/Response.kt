package com.alpha2048.jetpackcomposetest.data.model

import com.alpha2048.jetpackcomposetest.domain.entity.OwnerEntity
import com.alpha2048.jetpackcomposetest.domain.entity.RepositoryEntity
import com.alpha2048.jetpackcomposetest.domain.entity.SearchRepositoryEntity

data class SearchRepositoryResponse (
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RepositoryResponse>,
) {
    fun toEntity(): SearchRepositoryEntity = SearchRepositoryEntity(
        totalCount = total_count,
        incompleteResults = incomplete_results,
        items = items.map { it.toEntity() },
    )
}

data class RepositoryResponse (
    val id: Int,
    val name: String,
    val html_url: String,
    val stargazers_count: Int,
    val owner: OwnerResponse,
) {
    fun toEntity(): RepositoryEntity = RepositoryEntity(
        id = id,
        name = name,
        htmlUrl = html_url,
        stargazersCount = stargazers_count,
        owner = owner.toEntity(),
    )
}

data class OwnerResponse(
    val id: Int,
    val avatar_url: String,
) {
    fun toEntity(): OwnerEntity = OwnerEntity(
        id = id,
        avatarUrl = avatar_url,
    )
}