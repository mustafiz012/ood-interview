package restaurant.reservation

import restaurant.table.Table
import java.time.LocalDateTime

data class Reservation(
    val partyName: String,
    val partySize: Int,
    val time: LocalDateTime,
    val assignedTable: Table
)
