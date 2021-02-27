package com.interplanetarytravel.reservationsystem.dtos

import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import java.util.*

data class VoyageUpdateDto(
        val id: Long,
        val spacecraft: Spacecraft,
        val launchpad: Launchpad,
        val departure: Date
)
