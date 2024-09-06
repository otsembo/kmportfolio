package com.otsembo.portfolio.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun AppLinearProgressBar(
    modifier: Modifier = Modifier,
    progress: () -> Int,
    barTitle: String?,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            barTitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                )
            } ?: Text(
                text = "0%",
                style = MaterialTheme.typography.titleMedium,
            )

            if (barTitle == null) {
                Text(
                    text = "100%",
                    style = MaterialTheme.typography.titleSmall,
                )
            } else {
                Text(
                    text = "${progress()}%",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(55),
                    ),
        ) {
            LinearProgressIndicator(
                progress = { progress() / 100f },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                strokeCap = StrokeCap.Round,
            )
        }
    }
}
