package movie_ticket.location

class Layout(
    private val rows: Int,
    private val columns: Int
) {
    private val seatsByNumber: MutableMap<String, Seat> = mutableMapOf()
    private val seatsByPosition: MutableMap<Int, MutableMap<Int, Seat>> = mutableMapOf()

    init {
        initializeLayout()
    }

    private fun initializeLayout() {
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                val seatNumber = "$i-$j"
                addSeat(seatNumber, i, j, Seat(seatNumber, null))
            }
        }
    }

    fun addSeat(seatNumber: String, row: Int, column: Int, seat: Seat) {
        seatsByNumber[seatNumber] = seat
        seatsByPosition.computeIfAbsent(row) { mutableMapOf() }[column] = seat
    }

    fun getSeatByNumber(seatNumber: String): Seat? = seatsByNumber[seatNumber]

    fun getSeatByPosition(row: Int, column: Int): Seat? = seatsByPosition[row]?.get(column)

    fun getAllSeats(): List<Seat> = seatsByNumber.values.toList()
}
