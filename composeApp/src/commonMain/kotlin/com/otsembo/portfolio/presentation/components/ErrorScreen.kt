package com.otsembo.portfolio.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onBack: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.TwoTone.Warning,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(150.dp),
        )

        SectionTitle(
            modifier =
                Modifier
                    .wrapContentWidth(unbounded = true)
                    .padding(top = 16.dp)
                    .padding(horizontal = 64.dp),
            title = errorMessage,
        )

        AppButton(
            modifier =
                Modifier
                    .padding(top = 16.dp),
            type = AppButtonType.SECONDARY,
            onClick = onBack,
            content = {
                Text("Go Back")
            },
        )
    }
}
