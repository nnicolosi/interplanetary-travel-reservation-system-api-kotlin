package com.interplanetarytravel.reservationsystem.dtos

import java.time.LocalDate

data class PassengerDto(
        val id: Long,
        val firstName: String,
        val lastName: String,
        val dateOfBirth: LocalDate,
        val voyage: VoyageDto
)
