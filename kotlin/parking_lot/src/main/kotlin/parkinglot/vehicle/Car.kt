package parkinglot.vehicle

data class Car(override val licensePlate: String) : Vehicle {
    override val size: VehicleSize = VehicleSize.MEDIUM
}
