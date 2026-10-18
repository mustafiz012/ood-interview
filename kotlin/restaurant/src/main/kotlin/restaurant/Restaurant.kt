package restaurant

import restaurant.menu.Menu
import restaurant.menu.MenuItem
import restaurant.reservation.Reservation
import restaurant.reservation.ReservationManager
import restaurant.table.Layout
import restaurant.table.Table
import java.math.BigDecimal
import java.time.LocalDateTime

class Restaurant(
    val name: String,
    val menu: Menu,
    val layout: Layout
) {
    val reservationManager: ReservationManager = ReservationManager(layout)

    fun findAvailableTimeSlots(rangeStart: LocalDateTime, rangeEnd: LocalDateTime, partySize: Int): Array<LocalDateTime> {
        return reservationManager.findAvailableTimeSlots(rangeStart, rangeEnd, partySize)
    }

    fun createScheduledReservation(partyName: String, partySize: Int, time: LocalDateTime): Reservation {
        return reservationManager.createReservation(partyName, partySize, time)
    }

    fun removeReservation(partyName: String, partySize: Int, reservationTime: LocalDateTime) {
        reservationManager.removeReservation(partyName, partySize, reservationTime)
    }

    fun createWalkInReservation(partyName: String, partySize: Int): Reservation {
        return reservationManager.createReservation(partyName, partySize, LocalDateTime.now())
    }

    fun orderItem(table: Table, item: MenuItem) {
        table.addOrder(item)
    }

    fun cancelItem(table: Table, item: MenuItem) {
        table.removeOrder(item)
    }

    fun calculateTableBill(table: Table): BigDecimal {
        return table.calculateBillAmount()
    }
}
