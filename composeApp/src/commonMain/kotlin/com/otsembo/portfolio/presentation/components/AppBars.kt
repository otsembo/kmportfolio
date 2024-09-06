package com.otsembo.portfolio.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import otsembo_portfolio.composeapp.generated.resources.Res
import otsembo_portfolio.composeapp.generated.resources.compose_multiplatform

@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Logo
        Logo(
            modifier = Modifier.weight(1f),
        )

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            MenuItem(
                label = "Home",
                onClick = {},
            )
            MenuItem(
                isSelected = true,
                label = "About",
                onClick = {},
            )
            MenuItem(
                label = "Projects",
                onClick = {},
            )
            MenuItem(
                label = "Contact",
                onClick = {},
            )
        }
    }
}

@Composable
fun BottomAppBar(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(bottom = 4.dp),
            thickness = 1.dp,
            color = Color.Gray,
        )
        Row {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                MenuItemWithIcon(
                    label = "LinkedIn",
                    onClick = {},
                    icon = {},
                )
                MenuItemWithIcon(
                    label = "X",
                    onClick = {},
                    isSelected = true,
                    icon = {},
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Text(
                    text = "Copyright @2023",
                    color = Color.Gray,
                )
            }
        }
    }
}

@Composable
fun Logo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(.5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "",
            modifier = Modifier.size(100.dp),
        )
        Text(
            text = "kmportfolio",
            fontFamily = FontFamily.Cursive,
            textAlign = TextAlign.Left,
        )
    }
}

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    label: String,
    onClick: () -> Unit,
) {
    Text(
        text = label,
        modifier = modifier.clickable(onClick = onClick),
        color =
            if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.Gray
            },
    )
}

@Composable
fun MenuItemWithIcon(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    label: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "",
            modifier = Modifier.size(36.dp),
        )
        MenuItem(
            isSelected = isSelected,
            label = label,
            onClick = onClick,
        )
    }
}
