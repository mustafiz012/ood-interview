package elevator.components

import elevator.observer.ElevatorObserver

class HallwayButtonPanel(private val floor: Int) {
    private val observers: MutableList<ElevatorObserver> = mutableListOf()

    fun pressButton(direction: Direction) {
        notifyObservers(direction)
    }

    fun addObserver(observer: ElevatorObserver) {
        observers.add(observer)
    }

    private fun notifyObservers(direction: Direction) {
        for (observer in observers) {
            observer.update(floor, direction)
        }
    }
}
