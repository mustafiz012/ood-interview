package movie_ticket.location

import movie_ticket.rate.PricingStrategy

class Seat(
    val seatNumber: String,
    var pricingStrategy: PricingStrategy?
)
