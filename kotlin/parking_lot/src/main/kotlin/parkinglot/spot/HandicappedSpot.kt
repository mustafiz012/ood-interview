package parkinglot.spot

import parkinglot.vehicle.Vehicle
import parkinglot.vehicle.VehicleSize

class HandicappedSpot(override val spotNumber: Int) : ParkingSpot {
    private var vehicle: Vehicle? = null

    override val isAvailable: Boolean
        get() = vehicle == null

    override fun occupy(vehicle: Vehicle) {
        if (isAvailable) {
            this.vehicle = vehicle
        }
    }

    override fun vacate() {
        this.vehicle = null
    }

    override val size: VehicleSize = VehicleSize.MEDIUM
}
