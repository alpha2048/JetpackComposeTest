package com.alpha2048.jetpackcomposetest.feature_main.viewmodel

import androidx.lifecycle.*
import com.alpha2048.jetpackcomposetest.common.entity.RepositoryEntity
import com.alpha2048.jetpackcomposetest.common.usecase.base.UseCaseResult
import com.alpha2048.jetpackcomposetest.feature_main.usecase.SearchRepositoryUseCase
import com.alpha2048.jetpackcomposetest.feature_main.usecase.SearchRepositoryUseCaseParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val useCase: SearchRepositoryUseCase
) : ViewModel() {

    sealed class UiState(val value: Int){
        object Loading: UiState(0)
        object Loaded: UiState(1)
        data class Error(val e: Exception): UiState(2)
    }

    data class HomeUiState(
        val items: List<RepositoryEntity> = listOf(),
        val isComplete: Boolean = false,
        val state: UiState = UiState.Loading,
    )

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var searchWord = INITIAL_WORD

    private var page = 1

    private var fetchJob: Job? = null

    init {
        fetch()
    }

    override fun onCleared() {
        fetchJob?.cancel()
        super.onCleared()
    }

    private fun fetch() {
        fetchJob = viewModelScope.launch {
            when(val result = useCase.execute(SearchRepositoryUseCaseParam(searchWord, page))) {
                is UseCaseResult.Success -> {
                    _uiState.update {
                        it.copy(
                            state = UiState.Loaded,
                            items = it.items + result.data.items,
                            // 呼びすぎると403になるので3ページで止める
                            isComplete = result.data.items.isEmpty() || page >= 3
                        )
                    }
                }
                is UseCaseResult.Failure -> {
                    _uiState.update {
                        it.copy(state = UiState.Error(result.e))
                    }
                }
            }
        }
    }

    fun loadMore() {
        if (fetchJob?.isActive == true) {
            return
        }
        if (uiState.value.isComplete) {
            return
        }

        page++
        fetch()
    }

    fun reload() {
        page = 0
        _uiState.update {
            HomeUiState(state = UiState.Loading, items = listOf(), isComplete = false)
        }
        fetchJob?.cancel()
        fetch()
    }

    fun search(searchWord: String) {
        this.searchWord = searchWord
        reload()
    }

    companion object {
        const val INITIAL_WORD = "Jetpack Compose"
    }
}