package com.interplanetarytravel.reservationsystem.dtos

import java.time.LocalDate

data class PassengerCreateDto(
        val firstName: String,
        val lastName: String,
        val dateOfBirth: LocalDate,
        val voyageId: Long
)
