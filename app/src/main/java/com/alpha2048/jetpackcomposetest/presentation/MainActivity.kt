package com.alpha2048.jetpackcomposetest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import com.alpha2048.jetpackcomposetest.presentation.ui.thema.MyThema
import androidx.navigation.compose.rememberNavController
import com.alpha2048.jetpackcomposetest.presentation.ui.navhost.MyNavHost

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyThema {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()

                MyNavHost(navController = navController, scaffoldState = scaffoldState)
            }
        }
    }
}