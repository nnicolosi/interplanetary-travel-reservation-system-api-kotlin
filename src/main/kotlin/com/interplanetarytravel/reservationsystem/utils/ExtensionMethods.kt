package com.interplanetarytravel.reservationsystem.utils

import com.interplanetarytravel.reservationsystem.dtos.DestinationDto
import com.interplanetarytravel.reservationsystem.dtos.SpacecraftDto
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Spacecraft

fun String.toSentenceCase(): String {
    return if (this.isNotEmpty()) {
        this[0].toUpperCase() + this.substring(1)
    } else {
        this
    }
}

fun String.toTitleCase(): String {
    return if (this.isNotEmpty()) {
        this.toLowerCase()
            .splitToSequence(" ")
            .map { it[0].toUpperCase() + it.substring(1) }
            .joinToString(" ")
    } else {
        this
    }
}

fun Destination.toDestinationDto(): DestinationDto {
    return DestinationDto(
        this,
        this.name.toTitleCase(),
        this.description)
}

fun Spacecraft.toSpacecraftDto(): SpacecraftDto {
    return SpacecraftDto(
        this,
        this.description,
        this.designation,
        this.capacity,
        this.destinations.map { it.toDestinationDto() })
}
