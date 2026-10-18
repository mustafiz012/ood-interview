package movie_ticket.location

class Cinema(
    val name: String,
    val location: String
) {
    val rooms: MutableList<Room> = mutableListOf()

    fun addRoom(room: Room) {
        rooms.add(room)
    }
}
