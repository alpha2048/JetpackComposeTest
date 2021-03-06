package com.alpha2048.jetpackcomposetest.domain.usecase

import com.alpha2048.jetpackcomposetest.common.entity.SearchRepositoryEntity
import com.alpha2048.jetpackcomposetest.domain.repository.SearchRepository
import com.alpha2048.jetpackcomposetest.feature_main.usecase.SearchRepositoryUseCase
import com.alpha2048.jetpackcomposetest.feature_main.usecase.SearchRepositoryUseCaseParam
import javax.inject.Inject

class SearchRepositoryUseCaseImpl @Inject constructor(private val repository: SearchRepository) :
    SearchRepositoryUseCase() {

    override suspend fun buildExecutable(param: SearchRepositoryUseCaseParam): SearchRepositoryEntity =
        repository.search(param.q, param.page)
}
