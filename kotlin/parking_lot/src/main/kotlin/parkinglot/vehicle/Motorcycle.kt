package parkinglot.vehicle

data class Motorcycle(override val licensePlate: String) : Vehicle {
    override val size: VehicleSize = VehicleSize.SMALL
}
