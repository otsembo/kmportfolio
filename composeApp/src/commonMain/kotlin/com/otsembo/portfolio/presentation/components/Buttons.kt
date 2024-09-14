package com.otsembo.portfolio.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    type: AppButtonType = AppButtonType.PRIMARY,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val (btnColors, border) =
        when (type) {
            AppButtonType.PRIMARY -> (
                ButtonDefaults
                    .outlinedButtonColors()
                    .copy(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    )
                    to
                    BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                    )
            )
            AppButtonType.SECONDARY -> (
                ButtonDefaults
                    .outlinedButtonColors()
                    .copy(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    )
                    to
                    BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
            )
            AppButtonType.TERTIARY -> TODO()
        }

    val elevation =
        when (type) {
            AppButtonType.PRIMARY -> ButtonDefaults.elevatedButtonElevation()
            else -> null
        }

    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        content = content,
        shape = RoundedCornerShape(percent = 50),
        colors = btnColors,
        border = border,
        elevation = elevation,
        enabled = enabled,
    )
}

enum class AppButtonType {
    PRIMARY,
    SECONDARY,
    TERTIARY,
}
