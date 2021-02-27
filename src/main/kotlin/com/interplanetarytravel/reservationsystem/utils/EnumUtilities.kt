package com.interplanetarytravel.reservationsystem.utils

import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.exceptions.DestinationNotFoundException
import com.interplanetarytravel.reservationsystem.exceptions.SpacecraftNotFoundException
import org.springframework.http.HttpStatus

fun getValidDestinationOrNull(name: String?): Destination? {
    if (name != null) {
        if (Destination.values().any { it.name == name.toUpperCase() }) {
            return Destination.valueOf(name.toUpperCase())
        } else {
            throw DestinationNotFoundException(HttpStatus.BAD_REQUEST)
        }
    }

    return null
}

fun getValidSpacecraftOrNull(name: String?): Spacecraft? {
    if (name != null) {
        if (Spacecraft.values().any { it.name == name.toUpperCase() }) {
            return Spacecraft.valueOf(name.toUpperCase())
        } else {
            throw SpacecraftNotFoundException(HttpStatus.BAD_REQUEST)
        }
    }

    return null
}
