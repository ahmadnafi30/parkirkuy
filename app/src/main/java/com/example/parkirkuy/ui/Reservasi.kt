package com.example.parkirkuy.ui

import java.io.Serializable

data class Reservasi(
    val tanggal: String,
    val lokasi: String,
    val status: String,
    val foto: Int
) : Serializable

