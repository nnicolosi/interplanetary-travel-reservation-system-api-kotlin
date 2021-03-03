package com.interplanetarytravel.reservationsystem.utils

import com.interplanetarytravel.reservationsystem.dtos.*
import com.interplanetarytravel.reservationsystem.entities.Passenger
import com.interplanetarytravel.reservationsystem.entities.Voyage
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import java.time.LocalDate

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

fun LocalDate.isSameDayAs(date: LocalDate): Boolean {
    return this.compareTo(date) == 0
}

fun LocalDate.isAfterToday(): Boolean {
    return this.isAfter(LocalDate.now())
}

fun LocalDate.isBeforeToday(): Boolean {
    return this.isBefore(LocalDate.now())
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

fun Launchpad.toLaunchpadDto(): LaunchpadDto {
    return LaunchpadDto(
        this,
        this.description,
        this.designation
    )
}

fun Voyage.toVoyageDto(): VoyageDto {
    return VoyageDto(
        this.id,
        this.spacecraft.toSpacecraftDto(),
        this.launchpad.toLaunchpadDto(),
        this.destination.toDestinationDto(),
        this.departure
    )
}

fun VoyageCreateDto.toVoyage(): Voyage {
    return Voyage(
        0,
        this.spacecraft,
        this.launchpad,
        this.destination,
        this.departure,
        mutableListOf()
    )
}

fun VoyageUpdateDto.mapUpdatesToVoyage(voyage: Voyage): Voyage {
    return Voyage(
        this.id,
        this.spacecraft,
        this.launchpad,
        voyage.destination,
        this.departure,
        voyage.manifest
    )
}

fun Passenger.toPassengerDto(): PassengerDto {
    return PassengerDto(
        this.id,
        this.firstName,
        this.lastName,
        this.dateOfBirth,
        this.voyage.toVoyageDto()
    )
}

fun Passenger.toPassengerNameDto(): PassengerNameDto {
    return PassengerNameDto(
        this.id,
        this.firstName,
        this.lastName
    )
}

fun PassengerCreateDto.toPassenger(): Passenger {
    return Passenger(
        0,
        this.firstName,
        this.lastName,
        this.dateOfBirth,
    )
}
