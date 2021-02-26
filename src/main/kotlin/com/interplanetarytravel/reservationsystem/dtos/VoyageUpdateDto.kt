package com.interplanetarytravel.reservationsystem.dtos

import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import java.util.*

data class VoyageUpdateDto(
        val id: Long,
        val spacecraft: Spacecraft,
        val destination: Destination,
        val departure: Date
)
