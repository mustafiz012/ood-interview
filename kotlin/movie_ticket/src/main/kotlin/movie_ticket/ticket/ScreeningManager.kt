package movie_ticket.ticket

import movie_ticket.location.Seat
import movie_ticket.showing.Movie
import movie_ticket.showing.Screening

class ScreeningManager {
    private val screeningsByMovie: MutableMap<Movie, MutableList<Screening>> = mutableMapOf()
    private val ticketsByScreening: MutableMap<Screening, MutableList<Ticket>> = mutableMapOf()

    fun addScreening(movie: Movie, screening: Screening) {
        screeningsByMovie.computeIfAbsent(movie) { mutableListOf() }.add(screening)
    }

    fun getScreeningsForMovie(movie: Movie): List<Screening> {
        return screeningsByMovie.getOrDefault(movie, emptyList())
    }

    fun addTicket(screening: Screening, ticket: Ticket) {
        ticketsByScreening.computeIfAbsent(screening) { mutableListOf() }.add(ticket)
    }

    fun getTicketsForScreening(screening: Screening): List<Ticket> {
        return ticketsByScreening.getOrDefault(screening, emptyList())
    }

    fun getAvailableSeats(screening: Screening): List<Seat> {
        val allSeats = screening.room.layout.getAllSeats()
        val bookedTickets = getTicketsForScreening(screening)
        val availableSeats = allSeats.toMutableList()
        for (ticket in bookedTickets) {
            availableSeats.remove(ticket.seat)
        }
        return availableSeats
    }
}
