package restaurant

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import restaurant.menu.Menu
import restaurant.menu.MenuItem
import restaurant.reservation.Reservation
import restaurant.table.Layout
import restaurant.table.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class RestaurantTest {
    @Test
    fun testRestaurantReservation() {
        println("\n=== Testing Restaurant Reservation System ===")

        println("\n--- Setting Up Restaurant Menu ---")
        val menu = Menu()
        menu.addItem(
            MenuItem("Burger", "a burger", BigDecimal.valueOf(10.0), MenuItem.Category.MAIN)
        )
        println("✓ Added menu item: Burger ($10.00) - Main course")

        println("\n--- Creating Restaurant Layout ---")
        val layout = Layout(listOf(4, 4, 6, 6, 10))
        println("✓ Created table layout with 5 tables:")
        println("  - Table 1: 4 seats")
        println("  - Table 2: 4 seats")
        println("  - Table 3: 6 seats")
        println("  - Table 4: 6 seats")
        println("  - Table 5: 10 seats")

        println("\n--- Initializing Restaurant ---")
        val testRestaurant = Restaurant("BurgerShack", menu, layout)
        println("✓ Restaurant 'BurgerShack' initialized with menu and layout")

        println("\n--- Finding Available Time Slots ---")
        val reservationTime = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.HOURS)
        val possibleReservation =
            testRestaurant.findAvailableTimeSlots(reservationTime, reservationTime.plusHours(4), 6)
        println(
            "✓ Searching for available 6-seat tables between " +
                    reservationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    " and " + reservationTime.plusHours(4).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        )
        assertEquals(5, possibleReservation.size)
        println("✓ Found " + possibleReservation.size + " available time slots")

        val selectedTime = possibleReservation[2]
        assertEquals(selectedTime, reservationTime.plusHours(2))
        println("✓ Selected reservation time: " + selectedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))

        println("\n--- Creating Scheduled Reservation ---")
        val reservation: Reservation = testRestaurant.createScheduledReservation("TestParty", 6, selectedTime)
        println("✓ Created reservation for 'TestParty' (6 people) at " + selectedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
        assertEquals("TestParty", reservation.partyName)
        assertEquals(6, reservation.partySize)
        assertEquals(selectedTime, reservation.time)
        assertEquals(6, reservation.assignedTable.capacity)
        println("✓ Reservation details verified:")
        println("  - Party name: " + reservation.partyName)
        println("  - Party size: " + reservation.partySize + " people")
        println("  - Reserved time: " + reservation.time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
        println("  - Assigned table capacity: " + reservation.assignedTable.capacity + " seats")

        println("\n--- Verifying Table Booking ---")
        assertFalse(reservation.assignedTable.isAvailableAt(selectedTime))
        assertTrue(testRestaurant.reservationManager.getReservations().contains(reservation))
        println("✓ Table booking verification passed:")
        println("  - Table is no longer available at reserved time")
        println("  - Reservation is properly stored in reservation manager")
        println("=== Restaurant Reservation Test Completed Successfully ===\n")
    }

    @Test
    fun testRestaurantWalkIn() {
        println("\n=== Testing Restaurant Walk-In System ===")

        println("\n--- Setting Up Restaurant Menu ---")
        val menu = Menu()
        menu.addItem(
            MenuItem("Burger", "a burger", BigDecimal.valueOf(10.0), MenuItem.Category.MAIN)
        )
        println("✓ Added menu item: Burger ($10.00) - Main course")

        println("\n--- Creating Restaurant Layout ---")
        val layout = Layout(listOf(4, 4, 6, 6, 10))
        println("✓ Created table layout with 5 tables (4, 4, 6, 6, 10 seats)")

        println("\n--- Initializing Restaurant ---")
        val testRestaurant = Restaurant("BurgerShack", menu, layout)
        println("✓ Restaurant 'BurgerShack' initialized")

        println("\n--- Processing Walk-In Reservation ---")
        val walkIn: Reservation = testRestaurant.createWalkInReservation("WalkIn", 4)
        println("✓ Created walk-in reservation for 'WalkIn' party (4 people)")
        val table: Table = walkIn.assignedTable
        assertEquals(4, table.capacity)
        assertEquals(4, table.currentPartySize)
        println("✓ Walk-in assignment verified:")
        println("  - Assigned table capacity: " + table.capacity + " seats")
        println("  - Current party size: " + table.currentPartySize + " people")

        println("\n--- Processing Order ---")
        table.addOrder(menu.getItem("Burger")!!, 4)
        println("✓ Added order: 4 Burgers ($10.00 each)")
        assertEquals(BigDecimal.valueOf(40.0), table.calculateBillAmount())
        println("✓ Bill calculation verified: $" + table.calculateBillAmount())
        println("  - 4 Burgers × $10.00 = $40.00")
        println("=== Restaurant Walk-In Test Completed Successfully ===\n")
    }
}
