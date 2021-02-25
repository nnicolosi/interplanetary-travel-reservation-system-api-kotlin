package com.interplanetarytravel.reservationsystem.dtos

import com.interplanetarytravel.reservationsystem.enums.Spacecraft

data class SpacecraftDto(
    val id: Spacecraft,
    val description: String,
    val designation: String,
    val capacity: Int,
    val destinations: List<String>
)
