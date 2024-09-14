package com.otsembo.portfolio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.rememberNavController
import com.otsembo.portfolio.infrastructure.di.desktopJVMdiModule
import com.otsembo.portfolio.presentation.DesktopNav
import org.koin.core.context.GlobalContext.startKoin

fun main() =
    application {
        startKoin {
            modules(desktopJVMdiModule())
        }

        val navController = rememberNavController()
        var isAppArrowShown by remember { mutableStateOf(false) }

        Window(
            onCloseRequest = ::exitApplication,
            title = "otsembo-portfolio",
        ) {
            App(
                isAppArrowShown = true,
                controller = navController,
            ) {
                DesktopNav(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    onShowAppArrow = { isAppArrowShown = it },
                )
            }
        }
    }
