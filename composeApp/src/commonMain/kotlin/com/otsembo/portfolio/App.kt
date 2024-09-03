@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.otsembo.portfolio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otsembo.portfolio.presentation.components.AppButton
import com.otsembo.portfolio.presentation.components.AppButtonType
import com.otsembo.portfolio.presentation.components.AppTextField
import com.otsembo.portfolio.presentation.components.AppTextFieldType
import com.otsembo.portfolio.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("ktlint:standard:function-naming")
@Composable
@Preview
fun App() {
    AppTheme {
        Scaffold(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(
                            horizontal = 16.dp,
                        ).padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                AppButton(
                    modifier = Modifier.height(ButtonDefaults.MinHeight),
                    onClick = {},
                    content = {
                        Text("Button")
                    },
                )

                AppButton(
                    modifier = Modifier.height(ButtonDefaults.MinHeight),
                    onClick = {},
                    content = {
                        Text("Button")
                    },
                    type = AppButtonType.SECONDARY,
                )

                var password by remember { mutableStateOf("") }

                AppTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text("Enter your name")
                    },
                )

                AppTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text("Enter your password")
                    },
                    fieldType = AppTextFieldType.Password,
                )

                AppTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text("Enter your name")
                    },
                    fieldType = AppTextFieldType.TextArea,
                )
            }
        }
    }
}
