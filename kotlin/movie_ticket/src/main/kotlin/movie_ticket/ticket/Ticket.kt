package movie_ticket.ticket

import movie_ticket.location.Seat
import movie_ticket.showing.Screening
import java.math.BigDecimal

class Ticket(
    val screening: Screening,
    val seat: Seat,
    val price: BigDecimal
)
