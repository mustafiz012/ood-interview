package movie_ticket

import movie_ticket.location.Cinema
import movie_ticket.location.Layout
import movie_ticket.location.Room
import movie_ticket.rate.NormalRate
import movie_ticket.showing.Movie
import movie_ticket.showing.Screening
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class MovieBookingSystemTest {
    @Test
    fun testBrowseAndBuy() {
        println("\n=== Testing Movie Booking System: Browse and Buy Flow ===")
        val bookingSystem = MovieBookingSystem()
        println("\n--- Creating Room and Setting Up Seat Pricing ---")
        val room = Room("1", Layout(10, 10))
        for (i in 0 until 10) {
            for (j in 0 until 10) {
                room.layout.getSeatByPosition(i, j)?.pricingStrategy = NormalRate(BigDecimal.valueOf(10.00))
            }
        }
        println("✓ Created room '1' with 10x10 seats, each seat priced at $10.00")

        println("\n--- Adding Cinema to Booking System ---")
        bookingSystem.addCinema(Cinema("Test Cinema", "Test Location"))
        println("✓ Added cinema: 'Test Cinema' at 'Test Location'")

        println("\n--- Creating Movie and Screening ---")
        val length = 180
        val movie = Movie("Test Movie", "Test Description", length)
        val screening = Screening(movie, room, LocalDateTime.now(), LocalDateTime.now().plusMinutes(length.toLong()))
        println("✓ Created movie: 'Test Movie' (180 min)")
        println("✓ Created screening for 'Test Movie' in room '1'")

        println("\n--- Adding Movie and Screening to Booking System ---")
        bookingSystem.addMovie(movie)
        bookingSystem.addScreening(movie, screening)
        println("✓ Added movie and screening to booking system")

        println("\n--- Verifying Movies and Screenings in System ---")
        assertEquals(1, bookingSystem.getAllMovies().size)
        assertEquals(1, bookingSystem.getScreeningsForMovie(movie).size)
        println("✓ Verified: 1 movie and 1 screening present in system")

        println("\n--- Checking Available Seats ---")
        assertEquals(100, bookingSystem.getAvailableSeats(screening).size)
        println("✓ Verified: 100 seats available for the screening")

        println("\n--- Booking a Ticket ---")
        bookingSystem.bookTicket(screening, room.layout.getSeatByPosition(0, 0)!!)
        assertEquals(1, bookingSystem.getTicketCount(screening))
        println("✓ Booked seat (0,0) for 'Test Movie' screening")
        println("✓ Verified: 1 ticket booked for the screening")

        println("\n--- Checking Ticket Price ---")
        assertEquals(BigDecimal.valueOf(10.00), bookingSystem.getTicketsForScreening(screening)[0].price)
        println("✓ Verified: Ticket price is $10.00")
        println("=== Movie Booking System Test Completed Successfully ===\n")
    }
}
