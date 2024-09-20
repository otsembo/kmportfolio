package com.otsembo.portfolio.infrastructure.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.LocalDateTime

interface BaseRepository {
    fun currentTime(): LocalDateTime =
        LocalDateTime.parse(
            input =
                java.time.LocalDateTime
                    .now()
                    .toString(),
        )

    fun repositoryScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)
}
