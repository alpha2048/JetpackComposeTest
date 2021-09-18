package com.alpha2048.jetpackcomposetest.common.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

abstract class UseCaseInterface<in T, out E>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    protected abstract suspend fun buildExecutable(param: T): E

    suspend fun execute(param: T): UseCaseResult<E> = withContext(dispatcher) {
        return@withContext try {
            UseCaseResult.Success(data = buildExecutable(param))
        } catch (e: Exception) {
            UseCaseResult.Failure(e)
        }
    }
}

sealed class UseCaseResult<out T> {
    data class Success<out T>(val data: T) : UseCaseResult<T>()
    data class Failure<out T>(val e: Exception) : UseCaseResult<T>()
}