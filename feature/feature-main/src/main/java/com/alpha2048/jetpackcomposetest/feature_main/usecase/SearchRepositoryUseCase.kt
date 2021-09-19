package com.alpha2048.jetpackcomposetest.feature_main.usecase

import com.alpha2048.jetpackcomposetest.common.entity.SearchRepositoryEntity
import com.alpha2048.jetpackcomposetest.common.usecase.base.UseCaseInterface

abstract class SearchRepositoryUseCase: UseCaseInterface<SearchRepositoryUseCaseParam, SearchRepositoryEntity>()

data class SearchRepositoryUseCaseParam(
    val q: String,
    val page: Int,
)