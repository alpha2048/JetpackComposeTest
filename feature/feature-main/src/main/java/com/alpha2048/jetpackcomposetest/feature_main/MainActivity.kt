package com.alpha2048.jetpackcomposetest.feature_main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.alpha2048.jetpackcomposetest.feature_main.navhost.MyNavHost
import com.alpha2048.jetpackcomposetest.ui_component.resource.MyThema
import dagger.hilt.android.AndroidEntryPoint

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
