package com.interplanetarytravel.reservationsystem.services

import com.interplanetarytravel.reservationsystem.dtos.VoyageCreateDto
import com.interplanetarytravel.reservationsystem.dtos.VoyageUpdateDto
import com.interplanetarytravel.reservationsystem.exceptions.InvalidDepartureDateException
import com.interplanetarytravel.reservationsystem.exceptions.InvalidLaunchpadException
import com.interplanetarytravel.reservationsystem.exceptions.InvalidSpacecraftException
import com.interplanetarytravel.reservationsystem.exceptions.VoyageNotFoundException
import com.interplanetarytravel.reservationsystem.utils.isAfterToday
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class VoyageValidationService(val availabilityService: AvailabilityService, val voyageService: VoyageService) {

    fun validateVoyageCreateDto(dto: VoyageCreateDto) {

        // departure date must be in the future
        if (!dto.departure.isAfterToday()) {
            throw InvalidDepartureDateException()
        }

        // launchpad must be available on the departure date
        if (!availabilityService.getAvailableLaunchpads(dto.departure).contains(dto.launchpad)) {
            throw InvalidLaunchpadException()
        }

        // spacecraft must be available
        if (!availabilityService.getAvailableSpacecraft().contains(dto.spacecraft)) {
            throw InvalidSpacecraftException(message = "Spacecraft is unavailable")
        }

        // spacecraft must be certified for the destination
        if (!availabilityService.getAvailableSpacecraft(dto.destination).contains(dto.spacecraft)) {
            throw InvalidSpacecraftException(message = "Spacecraft is not certified for the destination")
        }

    }

    fun validateVoyageUpdateDto(dto: VoyageUpdateDto) {
        val voyage = voyageService.findById(dto.id).orElseThrow { VoyageNotFoundException(HttpStatus.BAD_REQUEST) }

        // departure date must be in the future
        if (!dto.departure.isAfterToday()) {
            throw InvalidDepartureDateException()
        }

        // launchpad must be available on the departure date
        if (!availabilityService.getAvailableLaunchpads(dto.departure).contains(dto.launchpad)) {
            throw InvalidLaunchpadException()
        }

        // spacecraft must be available
        if (!availabilityService.getAvailableSpacecraft().contains(dto.spacecraft)) {
            throw InvalidSpacecraftException(message = "Spacecraft is unavailable")
        }

        // spacecraft must be certified for the destination
        if (!availabilityService.getAvailableSpacecraft(voyage.destination).contains(dto.spacecraft)) {
            throw InvalidSpacecraftException(message = "Spacecraft is uncertified for the destination")
        }
    }
}