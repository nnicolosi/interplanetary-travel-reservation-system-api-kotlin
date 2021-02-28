package com.interplanetarytravel.reservationsystem.dtos

import java.util.*

data class PassengerDto(
        val id: Long,
        val firstName: String,
        val lastName: String,
        val dateOfBirth: Date,
        val voyage: VoyageDto
)
