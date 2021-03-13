package com.interplanetarytravel.reservationsystem.services

import com.interplanetarytravel.reservationsystem.dtos.VoyageCreateDto
import com.interplanetarytravel.reservationsystem.dtos.VoyageUpdateDto
import com.interplanetarytravel.reservationsystem.exceptions.*
import com.interplanetarytravel.reservationsystem.utils.isAfterToday
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class VoyageValidationService(
    private val availabilityService: AvailabilityService,
    private val voyageService: VoyageService
) {

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

        // spacecraft must be available if it is being updated
        if (dto.spacecraft != voyage.spacecraft && !availabilityService.getAvailableSpacecraft().contains(dto.spacecraft)) {
            throw InvalidSpacecraftException(message = "Spacecraft is unavailable")
        }

        // spacecraft must be certified for the destination
        if (!availabilityService.getAvailableSpacecraft(voyage.destination).contains(dto.spacecraft)) {
            throw InvalidSpacecraftException(message = "Spacecraft is uncertified for the destination")
        }
    }

    fun validateVoyageCancellation(id: Long) {
        val voyage = voyageService.findById(id).orElseThrow { VoyageNotFoundException(HttpStatus.BAD_REQUEST) }

        // voyage must be for a future date
        if (!voyage.departure.isAfterToday()) {
            throw InvalidCancellationRequestException(message = "Voyage has already departed")
        }

        // voyage must not have an empty manifest
        if (voyage.manifest.isNotEmpty()) {
            throw InvalidCancellationRequestException(message = "Voyage has a non-empty manifest")
        }
    }
}