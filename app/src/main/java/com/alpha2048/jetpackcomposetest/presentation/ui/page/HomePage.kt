package com.alpha2048.jetpackcomposetest.presentation.ui.page

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpha2048.jetpackcomposetest.domain.entity.OwnerEntity
import com.alpha2048.jetpackcomposetest.domain.entity.SearchRepositoryEntity
import com.alpha2048.jetpackcomposetest.domain.entity.RepositoryEntity
import com.alpha2048.jetpackcomposetest.presentation.ui.component.EmptyLayout
import com.alpha2048.jetpackcomposetest.presentation.ui.component.ErrorLayout
import com.alpha2048.jetpackcomposetest.presentation.ui.component.LoadingLayout
import com.alpha2048.jetpackcomposetest.presentation.ui.component.RepositoryCard
import com.alpha2048.jetpackcomposetest.presentation.ui.thema.MyThema
import com.alpha2048.jetpackcomposetest.presentation.ui.util.OnBottomReached
import com.alpha2048.jetpackcomposetest.presentation.viewmodel.HomePageViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomePage(
    viewModel: HomePageViewModel,
    navigateToRepositoryDetail: (RepositoryEntity) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    HomePage(
        uiState = uiState,
        onRefresh = { viewModel.reload() },
        onSearch = { searchWord ->
            viewModel.search(searchWord)
        },
        loadMore = { viewModel.loadMore() },
        navigateToRepositoryDetail = navigateToRepositoryDetail,
    )
}

@Composable
fun HomePage(
    uiState: HomePageViewModel.HomeUiState,
    onRefresh: () -> Unit,
    onSearch: (String) -> Unit,
    loadMore: () -> Unit,
    navigateToRepositoryDetail: (RepositoryEntity) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    var text by remember {
        mutableStateOf(HomePageViewModel.INITIAL_WORD)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    // SearchBar
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TextField(
                                modifier = Modifier.weight(weight= 1f),
                                leadingIcon = {
                                    Icon(Icons.Default.Search, contentDescription = "")
                                },
                                value = text,
                                onValueChange = {
                                    text = it
                                },
                                maxLines = 1,
                                singleLine = true,
                            )
                            Button(onClick = { onSearch(text) }) {
                                Text(text = "決定")
                            }
                        }
                    }
                    
                    
                },
            )
        },
    ) {
        when (uiState.state) {
            is HomePageViewModel.UiState.Loading -> {
                LoadingLayout()
            }
            is HomePageViewModel.UiState.Loaded -> {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(uiState.state == HomePageViewModel.UiState.Loading),
                    onRefresh = onRefresh,
                    content = {
                        if (uiState.items.isNotEmpty()) {
                            val listState = rememberLazyListState()

                            LazyColumn(state = listState) {
                                uiState.items.forEach { repository ->
                                    item {
                                        RepositoryCard(repository = repository, onClick = {
                                            navigateToRepositoryDetail(
                                                repository
                                            )
                                        })
                                    }
                                }
                                if (!uiState.isComplete) {
                                    item {
                                        Box(modifier = Modifier.fillMaxWidth()
                                            .padding(8.dp)
                                            .wrapContentSize(Alignment.Center)) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                            }

                            listState.OnBottomReached {
                                loadMore()
                            }
                        } else {
                            EmptyLayout()
                        }
                    },
                )
            }
            is HomePageViewModel.UiState.Error -> {
                ErrorLayout(e = uiState.state.e, onClickRetry = onRefresh)
            }
        }
    }
}

@Preview("Home screen")
@Preview("Home screen (dark)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("Home screen (big font)", fontScale = 1.5f)
@Preview("Home screen (large screen)", device = Devices.PIXEL_C)
@Composable
fun PreviewHomePage() {
    val items = listOf(
        RepositoryEntity(
            id = 1,
            name = "test",
            htmlUrl = "test",
            stargazersCount = 1,
            owner = OwnerEntity(
                id = 1,
                avatarUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png"
            )
        ),
        RepositoryEntity(
            id = 2,
            name = "test2",
            htmlUrl = "test2",
            stargazersCount = 2,
            owner = OwnerEntity(
                id = 2,
                avatarUrl = "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png"
            )
        )
    )

    MyThema {
        HomePage(
            uiState = HomePageViewModel.HomeUiState(items = items, state = HomePageViewModel.UiState.Loaded),
            onRefresh = { },
            onSearch = { },
            loadMore = {},
            navigateToRepositoryDetail = { },
        )
    }
}
