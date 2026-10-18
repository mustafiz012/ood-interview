package restaurant.table

import restaurant.menu.MenuItem
import restaurant.reservation.OrderItem
import restaurant.reservation.Reservation
import java.math.BigDecimal
import java.time.LocalDateTime

class Table(
    val tableId: Int,
    val capacity: Int
) {
    private val reservations: MutableMap<LocalDateTime, Reservation> = mutableMapOf()
    private val orderedItems: MutableMap<MenuItem, MutableList<OrderItem>> = mutableMapOf()

    fun calculateBillAmount(): BigDecimal {
        return orderedItems.values.flatten()
            .map { it.item.price }
            .fold(BigDecimal.ZERO, BigDecimal::add)
    }

    fun addOrder(item: MenuItem, quantity: Int) {
        for (i in 0 until quantity) {
            addOrder(item)
        }
    }

    fun addOrder(item: MenuItem) {
        val list = orderedItems.computeIfAbsent(item) { mutableListOf() }
        list.add(OrderItem(item))
    }

    fun removeOrder(item: MenuItem) {
        val list = orderedItems[item]
        if (list != null && list.isNotEmpty()) {
            list.removeAt(0)
            if (list.isEmpty()) {
                orderedItems.remove(item)
            }
        }
    }

    fun isAvailableAt(reservationTime: LocalDateTime): Boolean {
        return !reservations.containsKey(reservationTime)
    }

    fun addReservation(reservation: Reservation) {
        reservations[reservation.time] = reservation
    }

    fun removeReservation(reservationTime: LocalDateTime) {
        reservations.remove(reservationTime)
    }

    val currentPartySize: Int
        get() = reservations.values.sumOf { it.partySize }

    fun getOrderedItems(): Map<MenuItem, List<OrderItem>> = orderedItems

    override fun toString(): String = "Table #$tableId (Capacity: $capacity)"
}
