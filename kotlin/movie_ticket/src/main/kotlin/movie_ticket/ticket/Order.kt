package movie_ticket.ticket

import java.math.BigDecimal
import java.time.LocalDateTime

class Order(val orderDate: LocalDateTime) {
    val tickets: MutableList<Ticket> = mutableListOf()

    fun addTicket(ticket: Ticket) {
        tickets.add(ticket)
    }

    fun getAllTickets(): List<Ticket> = tickets

    fun calculateTotalPrice(): BigDecimal {
        return tickets.map { it.price }.fold(BigDecimal.ZERO, BigDecimal::add)
    }
}
