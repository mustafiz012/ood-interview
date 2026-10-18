package parkinglot.spot

import parkinglot.vehicle.Vehicle
import parkinglot.vehicle.VehicleSize

class ParkingManager(
    private val availableSpots: Map<VehicleSize, MutableList<ParkingSpot>>
) {
    private val vehicleToSpotMap: MutableMap<Vehicle, ParkingSpot> = mutableMapOf()

    fun findSpotForVehicle(vehicle: Vehicle): ParkingSpot? {
        val vehicleSize = vehicle.size
        for (size in VehicleSize.values()) {
            if (size.ordinal >= vehicleSize.ordinal) {
                val spots = availableSpots[size] ?: continue
                for (spot in spots) {
                    if (spot.isAvailable) {
                        return spot
                    }
                }
            }
        }
        return null
    }

    fun parkVehicle(vehicle: Vehicle): ParkingSpot? {
        val spot = findSpotForVehicle(vehicle)
        if (spot != null) {
            spot.occupy(vehicle)
            vehicleToSpotMap[vehicle] = spot
            availableSpots[spot.size]?.remove(spot)
            return spot
        }
        return null
    }

    fun unparkVehicle(vehicle: Vehicle) {
        val spot = vehicleToSpotMap.remove(vehicle)
        if (spot != null) {
            spot.vacate()
            availableSpots[spot.size]?.add(spot)
        }
    }

    fun findVehicleSpot(vehicle: Vehicle): ParkingSpot? {
        return vehicleToSpotMap[vehicle]
    }
}
