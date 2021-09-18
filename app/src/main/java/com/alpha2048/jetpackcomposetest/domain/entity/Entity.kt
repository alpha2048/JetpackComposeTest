package com.alpha2048.jetpackcomposetest.domain.entity

data class SearchRepositoryEntity (
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<RepositoryEntity>
)

data class RepositoryEntity (
    val id: Int,
    val name: String,
    val htmlUrl: String,
    val stargazersCount: Int,
    val owner: OwnerEntity,
)

data class OwnerEntity (
    val id: Int,
    val avatarUrl: String,
)