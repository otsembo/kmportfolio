package com.otsembo.portfolio.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.DarkMode
import androidx.compose.material.icons.twotone.Lightbulb
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThemeToggle(
    isDarkMode: Boolean = false,
    onToggle: (Boolean) -> Unit = {},
) {
    Switch(
        checked = isDarkMode,
        onCheckedChange = { onToggle(it) },
        modifier = Modifier.width(50.dp),
        thumbContent = {
            if (isDarkMode) {
                Icon(Icons.TwoTone.DarkMode, contentDescription = null, modifier = Modifier.padding(4.dp))
            } else {
                Icon(Icons.TwoTone.Lightbulb, contentDescription = null, modifier = Modifier.padding(4.dp))
            }
        },
        colors =
            SwitchDefaults.colors().copy(
                uncheckedThumbColor = MaterialTheme.colorScheme.tertiary,
                uncheckedIconColor = MaterialTheme.colorScheme.onTertiary,
                uncheckedTrackColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
            ),
    )
}
