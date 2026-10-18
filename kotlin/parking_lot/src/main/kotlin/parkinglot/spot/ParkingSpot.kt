package parkinglot.spot

import parkinglot.vehicle.Vehicle
import parkinglot.vehicle.VehicleSize

interface ParkingSpot {
    val isAvailable: Boolean
    fun occupy(vehicle: Vehicle)
    fun vacate()
    val spotNumber: Int
    val size: VehicleSize
}
