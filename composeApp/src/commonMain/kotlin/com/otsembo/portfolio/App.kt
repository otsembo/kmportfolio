@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.otsembo.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.otsembo.portfolio.presentation.components.BottomAppBar
import com.otsembo.portfolio.presentation.components.ThemeToggle
import com.otsembo.portfolio.presentation.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    isAppArrowShown: Boolean = false,
    controller: NavHostController = rememberNavController(),
    content: @Composable (PaddingValues) -> Unit = {},
) {
    var isDarkMode by remember { mutableStateOf(true) }

    AppTheme(
        darkTheme = isDarkMode,
    ) {
        Scaffold(
            modifier =
                Modifier
                    .fillMaxSize(),
            topBar = {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                            ).padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = if (isAppArrowShown.not()) Arrangement.End else Arrangement.SpaceBetween,
                ) {
                    if (isAppArrowShown) {
                        Icon(
                            imageVector = Icons.AutoMirrored.TwoTone.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp).clickable { controller.popBackStack() },
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    ThemeToggle(
                        isDarkMode = isDarkMode,
                        onToggle = { isDarkMode = it },
                    )
                }
            },
            bottomBar = {
                BottomAppBar(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 8.dp, end = 16.dp, start = 16.dp),
                )
            },
            content = content,
        )
    }
}
