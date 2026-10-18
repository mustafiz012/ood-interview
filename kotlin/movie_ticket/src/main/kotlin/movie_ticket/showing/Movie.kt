package movie_ticket.showing

import java.time.Duration

class Movie(
    val title: String,
    val genre: String,
    val durationInMinutes: Int
) {
    val duration: Duration
        get() = Duration.ofMinutes(durationInMinutes.toLong())
}
