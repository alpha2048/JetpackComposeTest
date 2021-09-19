package com.alpha2048.jetpackcomposetest.feature_main.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpha2048.jetpackcomposetest.common.entity.OwnerEntity
import com.alpha2048.jetpackcomposetest.common.entity.RepositoryEntity
import com.alpha2048.jetpackcomposetest.ui_component.component.organisms.EmptyLayout
import com.alpha2048.jetpackcomposetest.ui_component.component.organisms.ErrorLayout
import com.alpha2048.jetpackcomposetest.ui_component.component.organisms.LoadingLayout
import com.alpha2048.jetpackcomposetest.ui_component.component.parts.RepositoryCard
import com.alpha2048.jetpackcomposetest.ui_component.resource.MyThema
import com.alpha2048.jetpackcomposetest.ui_component.util.OnBottomReached
import com.alpha2048.jetpackcomposetest.feature_main.viewmodel.HomeScreenViewModel
import com.alpha2048.jetpackcomposetest.feature_main.R
import com.alpha2048.jetpackcomposetest.common.R as commonResource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navigateToRepositoryDetail: (RepositoryEntity) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
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
fun HomeScreen(
    uiState: HomeScreenViewModel.HomeUiState,
    onRefresh: () -> Unit,
    onSearch: (String) -> Unit,
    loadMore: () -> Unit,
    navigateToRepositoryDetail: (RepositoryEntity) -> Unit,
) {
    var text by remember {
        mutableStateOf(HomeScreenViewModel.INITIAL_WORD)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // SearchBar
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextField(
                            modifier = Modifier.weight(weight = 1f),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search Icon"
                                )
                            },
                            label = {
                                Text(text = stringResource(id = R.string.search_bar_label))
                            },
                            value = text,
                            onValueChange = {
                                text = it
                            },
                            maxLines = 1,
                            singleLine = true,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { onSearch(text) }) {
                            Text(text = stringResource(id = commonResource.string.decide))
                        }
                    }
                },
            )
        },
    ) {
        when (uiState.state) {
            is HomeScreenViewModel.UiState.Loading -> {
                LoadingLayout()
            }
            is HomeScreenViewModel.UiState.Loaded -> {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(uiState.state == HomeScreenViewModel.UiState.Loading),
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
                                        Box(modifier = Modifier
                                            .fillMaxWidth()
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
            is HomeScreenViewModel.UiState.Error -> {
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
fun PreviewHomeScreen() {
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
        HomeScreen(
            uiState = HomeScreenViewModel.HomeUiState(items = items, state = HomeScreenViewModel.UiState.Loaded),
            onRefresh = { },
            onSearch = { },
            loadMore = {},
            navigateToRepositoryDetail = { },
        )
    }
}
