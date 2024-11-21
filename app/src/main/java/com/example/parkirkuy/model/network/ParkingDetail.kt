package com.example.parkirkuy.model.network

data class ParkingDetail(
    val name: String,
    val vicinity: String,
    val latitude: Double,
    val longitude: Double,
    val parkingCapacity: ParkingCapacity
)

data class ParkingCapacity(
    val total: Int,
    val available: Int
)

