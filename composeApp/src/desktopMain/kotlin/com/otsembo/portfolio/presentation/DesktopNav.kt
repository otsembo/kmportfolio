package com.otsembo.portfolio.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.otsembo.portfolio.business.viewmodels.LoginVM
import com.otsembo.portfolio.presentation.components.ErrorScreen
import com.otsembo.portfolio.presentation.pages.auth.LoginScreen
import com.otsembo.portfolio.presentation.pages.dashboard.DashboardScreen
import kotlinx.serialization.Serializable
import org.koin.java.KoinJavaComponent.inject

@Composable
fun DesktopNav(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onShowAppArrow: (Boolean) -> Unit = {},
) {
    // init view models
    val loginVM: LoginVM by inject(LoginVM::class.java)

    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = NavDestinations.Auth,
    ) {
        composable<NavDestinations.Dashboard> {
            DashboardScreen(
                modifier = modifier,
                onShowAppArrow = onShowAppArrow,
            )
        }

        composable<NavDestinations.Auth> {
            LoginScreen(
                modifier = modifier,
                loginVM = loginVM,
                controller = navController,
                onShowAppArrow = onShowAppArrow,
            )
        }

        composable<NavDestinations.Error> { backStackEntry ->
            val message = backStackEntry.savedStateHandle.get<String>("message")
            onShowAppArrow(true)
            ErrorScreen(
                modifier = modifier,
                errorMessage = message ?: "Something went wrong!!",
                onBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}

object NavDestinations {
    @Serializable
    data object Dashboard

    @Serializable
    data object Auth

    @Serializable
    data class Error(
        val message: String,
    )
}
