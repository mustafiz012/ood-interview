package restaurant.reservation

import restaurant.table.Layout
import restaurant.table.Table
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ReservationManager(private val layout: Layout) {
    private val reservations: MutableSet<Reservation> = mutableSetOf()

    fun findAvailableTimeSlots(rangeStart: LocalDateTime, rangeEnd: LocalDateTime, partySize: Int): Array<LocalDateTime> {
        var current = rangeStart
        val possibleReservations = mutableListOf<LocalDateTime>()
        while (!current.isAfter(rangeEnd)) {
            val availableTable = layout.findAvailableTable(partySize, current)
            if (availableTable != null) {
                possibleReservations.add(current)
            }
            current = current.plusHours(1)
        }
        return possibleReservations.toTypedArray()
    }

    fun createReservation(partyName: String, partySize: Int, desiredTime: LocalDateTime): Reservation {
        val truncatedTime = desiredTime.truncatedTo(ChronoUnit.HOURS)
        val table = layout.findAvailableTable(partySize, truncatedTime)
            ?: throw IllegalStateException("No available table for party of size $partySize at $truncatedTime")
        val reservation = Reservation(partyName, partySize, truncatedTime, table)
        table.addReservation(reservation)
        reservations.add(reservation)
        return reservation
    }

    fun removeReservation(partyName: String, partySize: Int, reservationTime: LocalDateTime) {
        for (reservation in reservations.toSet()) {
            if (reservation.time == reservationTime &&
                reservation.partySize == partySize &&
                reservation.partyName == partyName
            ) {
                val table = reservation.assignedTable
                table.removeReservation(reservationTime)
                reservations.remove(reservation)
                return
            }
        }
    }

    fun getReservations(): Set<Reservation> = reservations.toSet()
}
