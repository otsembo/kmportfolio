package com.otsembo.portfolio.infrastructure.repository

import kotlinx.datetime.LocalDateTime

interface BaseRepository {
    fun currentTime(): LocalDateTime =
        LocalDateTime.parse(
            input =
                java.time.LocalDateTime
                    .now()
                    .toString(),
        )
}
