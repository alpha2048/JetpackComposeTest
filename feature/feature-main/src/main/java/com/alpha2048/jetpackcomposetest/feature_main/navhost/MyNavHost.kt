package com.alpha2048.jetpackcomposetest.feature_main.navhost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alpha2048.jetpackcomposetest.common.entity.RepositoryEntity
import com.alpha2048.jetpackcomposetest.feature_main.screen.HomeScreen
import com.alpha2048.jetpackcomposetest.feature_main.screen.RepositoryDetailScreen
import com.alpha2048.jetpackcomposetest.feature_main.viewmodel.HomeScreenViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val REPOSITORY_DETAIL_ROUTE = "repository_detail"
}

@Composable
fun MyNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_ROUTE
) {
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.HOME_ROUTE) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(
                viewModel = viewModel,
                navigateToRepositoryDetail = actions.navigateToRepositoryDetail,
            )
        }
        composable(
            route = "${MainDestinations.REPOSITORY_DETAIL_ROUTE}/?repository={repository}",
            arguments = listOf(navArgument("repository") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("repository")?.let { repositoryJson ->
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(RepositoryEntity::class.java).fromJson(repositoryJson)?.let {
                    RepositoryDetailScreen(
                        repository = it,
                        onBackPress = actions.upPress
                    )
                }
            }
        }
    }
}

class MainActions(navController: NavHostController) {
    val navigateToRepositoryDetail: (RepositoryEntity) -> Unit = { entity ->
        val repositoryJson = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter(
            RepositoryEntity::class.java
        ).toJson(entity)
        navController.navigate(MainDestinations.REPOSITORY_DETAIL_ROUTE + "/?repository=" + repositoryJson)
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}
