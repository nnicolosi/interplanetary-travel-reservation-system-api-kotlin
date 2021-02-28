package com.interplanetarytravel.reservationsystem.dtos

import com.interplanetarytravel.reservationsystem.enums.Destination

data class DestinationDto(
        val id: Destination,
        val name: String,
        val description: String,
        val diameter: String,
        val gravity: String,
        val orbit: String,
        val rotation: String,
        val day: String
)
