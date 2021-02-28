package com.interplanetarytravel.reservationsystem.dtos

import java.util.*

data class PassengerCreateDto(
        val firstName: String,
        val lastName: String,
        val dateOfBirth: Date,
        val voyageId: Long
)
