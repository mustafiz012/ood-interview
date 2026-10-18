package restaurant.table

import java.time.LocalDateTime
import java.util.SortedMap
import java.util.TreeMap

class Layout(tableCapacities: List<Int>) {
    private val tablesById: MutableMap<Int, Table> = mutableMapOf()
    private val tablesByCapacity: SortedMap<Int, MutableSet<Table>> = TreeMap()

    init {
        for ((index, capacity) in tableCapacities.withIndex()) {
            val table = Table(index, capacity)
            tablesById[index] = table
            tablesByCapacity.computeIfAbsent(capacity) { mutableSetOf() }.add(table)
        }
    }

    fun findAvailableTable(partySize: Int, reservationTime: LocalDateTime): Table? {
        for (tables in tablesByCapacity.tailMap(partySize).values) {
            for (table in tables) {
                if (table.isAvailableAt(reservationTime)) {
                    return table
                }
            }
        }
        return null
    }
}
