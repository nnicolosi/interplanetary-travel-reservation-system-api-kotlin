package com.interplanetarytravel.reservationsystem.dtos

import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import java.util.*

data class VoyageCreateDto(
        val spacecraft: Spacecraft,
        val destination: Destination,
        val departure: Date
)
