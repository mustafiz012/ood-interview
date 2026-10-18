package movie_ticket.showing

import movie_ticket.location.Room
import java.time.Duration
import java.time.LocalDateTime

class Screening(
    val movie: Movie,
    val room: Room,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
) {
    val duration: Duration
        get() = Duration.between(startTime, endTime)
}
