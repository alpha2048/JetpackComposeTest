package com.alpha2048.jetpackcomposetest.data.api

import com.alpha2048.jetpackcomposetest.data.model.SearchRepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiInterface {
    @GET("/search/repositories")
    suspend fun getGithubRepository(@Query("q") q: String,
                                    @Query("page") page: Int): SearchRepositoryResponse
}