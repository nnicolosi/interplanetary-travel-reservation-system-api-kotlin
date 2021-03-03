package com.interplanetarytravel.reservationsystem.services

import com.interplanetarytravel.reservationsystem.entities.Voyage
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.exceptions.SpacecraftNotFoundException
import com.interplanetarytravel.reservationsystem.utils.isAfterToday
import com.interplanetarytravel.reservationsystem.utils.isSameDayAs
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class AvailabilityService(private val voyageService: VoyageService) {

    fun getAvailableSpacecraft(destination: Destination? = null): List<Spacecraft> {
        // get spacecraft that are not already reserved for a voyage
        val voyages = voyageService.findAll()
        val availableSpacecraft = Spacecraft.values().filter { spacecraft -> voyages.none { it.spacecraft == spacecraft } }

        // if destination was specified, filter for spacecraft that are certified for that destination
        return if (destination != null) {
            availableSpacecraft.filter { spacecraft -> spacecraft.destinations.any { it == destination } }
        } else {
            availableSpacecraft
        }
    }

    fun getAvailableDestinations(spacecraft: Spacecraft?): List<Destination> {
        // get spacecraft that are not already reserved for a voyage
        val availableSpacecraft = getAvailableSpacecraft()

        return if (spacecraft != null) {
            // if spacecraft was specified, make sure it is available
            if (availableSpacecraft.contains(spacecraft)) {
                spacecraft.destinations
            } else {
                throw SpacecraftNotFoundException(HttpStatus.BAD_REQUEST, "Spacecraft is not available")
            }
        } else {
            availableSpacecraft.flatMap { it.destinations }.distinct()
        }
    }

    fun getAvailableLaunchpads(date: LocalDate): List<Launchpad> {
        // get the list of launchpads in use on the specified date
        val unavailableLaunchpads = voyageService.findAll()
                .filter { it.departure.isSameDayAs(date) }
                .map { it.launchpad }

        // return the launchpads not in use on the specified date
        return Launchpad.values().filter { !unavailableLaunchpads.contains(it) }
    }

    fun getAvailableVoyages(destination: Destination? = null): List<Voyage> {
        // get the list of voyages with capacity remaining and departing in the future
        val voyages = voyageService.findAll()
                .filter { it.departure.isAfterToday() }
                .filter { it.manifest.size < it.spacecraft.capacity }

        // if destination was specified, filter for voyages with that destination
        return if (destination != null) {
            voyages.filter { it.destination == destination }
        } else {
            voyages
        }
    }
}