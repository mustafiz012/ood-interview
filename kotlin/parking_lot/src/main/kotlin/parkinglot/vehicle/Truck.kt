package parkinglot.vehicle

data class Truck(override val licensePlate: String) : Vehicle {
    override val size: VehicleSize = VehicleSize.LARGE
}
