package com.otsembo.portfolio.presentation.pages.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Login
import androidx.compose.material.icons.twotone.Key
import androidx.compose.material.icons.twotone.SupervisedUserCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.otsembo.portfolio.business.viewmodels.LoginVM
import com.otsembo.portfolio.domain.mappers.AppState
import com.otsembo.portfolio.presentation.components.AppButton
import com.otsembo.portfolio.presentation.components.AppTextField
import com.otsembo.portfolio.presentation.components.AppTextFieldType
import com.otsembo.portfolio.presentation.components.SectionTitle
import org.jetbrains.compose.resources.painterResource
import otsembo_portfolio.composeapp.generated.resources.Res
import otsembo_portfolio.composeapp.generated.resources.compose_multiplatform

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginVM: LoginVM,
    controller: NavHostController,
    onShowAppArrow: (Boolean) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginState by loginVM.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        onShowAppArrow(false)
    }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "",
            modifier =
                Modifier
                    .padding(bottom = 4.dp)
                    .size(150.dp),
        )

        SectionTitle(
            modifier = Modifier.padding(bottom = 32.dp),
            title = "Welcome, please login.",
        )

        AppTextField(
            modifier =
                Modifier
                    .fillMaxWidth(0.45f)
                    .padding(bottom = 8.dp),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = "Enter your username")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.TwoTone.SupervisedUserCircle,
                    contentDescription = "Email",
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            },
        )

        AppTextField(
            modifier =
                Modifier
                    .fillMaxWidth(0.45f)
                    .padding(bottom = 16.dp),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = "Enter your secret password")
            },
            fieldType = AppTextFieldType.Password,
            leadingIcon = {
                Icon(
                    imageVector = Icons.TwoTone.Key,
                    contentDescription = "Password",
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            },
        )

        Box(
            modifier = Modifier.fillMaxWidth(0.45f),
        ) {
            AppButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                content = {
                    if (loginState is AppState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(48.dp).padding(2.dp))
                    } else {
                        Text("Login")
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.Login,
                            contentDescription = "Login",
                            modifier = Modifier.padding(start = 8.dp),
                        )
                    }
                },
                onClick = {
                    loginVM.login(
                        username = email,
                        password = password,
                        navHostController = controller,
                    )
                },
                enabled = loginState !is AppState.Loading,
            )
        }
    }
}
