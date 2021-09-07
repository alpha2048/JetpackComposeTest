package com.alpha2048.jetpackcomposetest.domain.entity

import com.alpha2048.jetpackcomposetest.data.model.OwnerResponse
import com.alpha2048.jetpackcomposetest.data.model.RepositoryResponse
import com.alpha2048.jetpackcomposetest.data.model.SearchRepositoryResponse

data class SearchRepositoryEntity (
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<RepositoryEntity>
) {
    constructor(response: SearchRepositoryResponse) : this(
        totalCount = response.total_count,
        incompleteResults = response.incomplete_results,
        items = response.items.map { RepositoryEntity(it) },
    )
}

data class RepositoryEntity (
    val id: Int,
    val name: String,
    val htmlUrl: String,
    val stargazersCount: Int,
    val owner: OwnerEntity,
) {
    constructor(response: RepositoryResponse) : this(
        id = response.id,
        name = response.name,
        htmlUrl = response.html_url,
        stargazersCount = response.stargazers_count,
        owner = OwnerEntity(response.owner),
    )
}

data class OwnerEntity (
    val id: Int,
    val avatarUrl: String,
) {
    constructor(response: OwnerResponse) : this(
        id = response.id,
        avatarUrl = response.avatar_url,
    )
}