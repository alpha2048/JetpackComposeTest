package com.alpha2048.jetpackcomposetest.data.model

data class SearchRepositoryResponse (
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<RepositoryResponse>,
)

data class RepositoryResponse (
    val id: Int,
    val name: String,
    val html_url: String,
    val stargazers_count: Int,
    val owner: OwnerResponse,
)

data class OwnerResponse(
    val id: Int,
    val avatar_url: String,
)