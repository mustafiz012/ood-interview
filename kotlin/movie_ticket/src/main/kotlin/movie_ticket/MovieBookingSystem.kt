package movie_ticket

import movie_ticket.location.Cinema
import movie_ticket.location.Seat
import movie_ticket.showing.Movie
import movie_ticket.showing.Screening
import movie_ticket.ticket.ScreeningManager
import movie_ticket.ticket.Ticket
import java.math.BigDecimal

class MovieBookingSystem {
    private val movies: MutableList<Movie> = mutableListOf()
    private val cinemas: MutableList<Cinema> = mutableListOf()
    val screeningManager: ScreeningManager = ScreeningManager()

    fun addMovie(movie: Movie) {
        movies.add(movie)
    }

    fun addCinema(cinema: Cinema) {
        cinemas.add(cinema)
    }

    fun getAllMovies(): List<Movie> = movies

    fun getAllCinemas(): List<Cinema> = cinemas

    fun getScreeningsForMovie(movie: Movie): List<Screening> {
        return screeningManager.getScreeningsForMovie(movie)
    }

    fun getAvailableSeats(screening: Screening): List<Seat> {
        return screeningManager.getAvailableSeats(screening)
    }

    fun bookTicket(screening: Screening, seat: Seat) {
        val price = seat.pricingStrategy?.price ?: BigDecimal.ZERO
        val ticket = Ticket(screening, seat, price)
        screeningManager.addTicket(screening, ticket)
    }

    fun addScreening(movie: Movie, screening: Screening) {
        screeningManager.addScreening(movie, screening)
    }

    fun getTicketCount(screening: Screening): Int {
        return screeningManager.getTicketsForScreening(screening).size
    }

    fun getTicketsForScreening(screening: Screening): List<Ticket> {
        return screeningManager.getTicketsForScreening(screening)
    }
}
