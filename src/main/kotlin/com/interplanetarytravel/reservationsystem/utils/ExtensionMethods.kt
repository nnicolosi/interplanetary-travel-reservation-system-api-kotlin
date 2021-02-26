package com.interplanetarytravel.reservationsystem.utils

import com.interplanetarytravel.reservationsystem.dtos.*
import com.interplanetarytravel.reservationsystem.entities.Voyage
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
            this.description,
            this.diameter,
            this.gravity,
            this.orbit,
            this.rotation,
            this.day
    )
}

fun Spacecraft.toSpacecraftDto(): SpacecraftDto {
    return SpacecraftDto(
            this,
            this.description,
            this.designation,
            this.capacity,
            this.destinations.map { it.name }
    )
}

fun Voyage.toVoyageDto(): VoyageDto {
    return VoyageDto(
            this.id,
            this.spacecraft.toSpacecraftDto(),
            this.destination.toDestinationDto(),
            this.departure
    )
}

fun VoyageCreateDto.toVoyage(): Voyage {
    return Voyage(
            0,
            this.spacecraft,
            this.destination,
            this.departure
    )
}

fun VoyageUpdateDto.toVoyage(): Voyage {
    return Voyage(
            this.id,
            this.spacecraft,
            this.destination,
            this.departure
    )
}