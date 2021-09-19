package com.alpha2048.jetpackcomposetest.feature_main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

import androidx.activity.compose.setContent
import com.alpha2048.jetpackcomposetest.ui_component.resource.MyThema
import com.alpha2048.jetpackcomposetest.feature_main.navhost.MyNavHost

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyThema {
                MyNavHost()
            }
        }
    }
}