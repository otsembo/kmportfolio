package com.otsembo.portfolio.business.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.otsembo.portfolio.business.LoginUC
import com.otsembo.portfolio.domain.mappers.AppState
import com.otsembo.portfolio.presentation.NavDestinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginVM(
    private val useCase: LoginUC,
) : ViewModel() {
    private val _state: MutableStateFlow<AppState?> = MutableStateFlow(null)
    val state: StateFlow<AppState?> = _state

    fun login(
        username: String,
        password: String,
        navHostController: NavHostController,
    ) {
        viewModelScope.launch {
            useCase(
                username = username,
                password = password,
            ).collectLatest {
                _state.value = it
                when (it) {
                    is AppState.Error -> {
                        navHostController.navigate(NavDestinations.Error(message = it.message))
                    }
                    is AppState.Success<*> -> {
                        navHostController.navigate(NavDestinations.Dashboard) {
                            popUpTo(NavDestinations.Auth) {
                                inclusive = true
                            }
                        }
                    }
                    AppState.Loading -> Unit
                }
            }
        }
    }
}
