package com.interplanetarytravel.reservationsystem.services

import com.interplanetarytravel.reservationsystem.dtos.PassengerCreateDto
import com.interplanetarytravel.reservationsystem.exceptions.InvalidDateOfBirthException
import com.interplanetarytravel.reservationsystem.exceptions.InvalidVoyageException
import com.interplanetarytravel.reservationsystem.exceptions.PassengerNotFoundException
import com.interplanetarytravel.reservationsystem.exceptions.VoyageNotFoundException
import com.interplanetarytravel.reservationsystem.utils.isAfterToday
import com.interplanetarytravel.reservationsystem.utils.isBeforeToday
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class PassengerValidationService(
        private val passengerService: PassengerService,
        private val voyageService: VoyageService) {

    fun validatePassengerCreateDto(dto: PassengerCreateDto) {
        val voyage = voyageService.findById(dto.voyageId).orElseThrow { VoyageNotFoundException(HttpStatus.BAD_REQUEST) }

        // date of birth must be in the past
        if (!dto.dateOfBirth.isBeforeToday()) {
            throw InvalidDateOfBirthException()
        }

        // voyage must have a future departure date
        if (!voyage.departure.isAfterToday()) {
            throw InvalidVoyageException(message = "Voyage has already departed")
        }

        // voyage must have capacity remaining
        if (voyage.manifest.size == voyage.spacecraft.capacity) {
            throw InvalidVoyageException(message = "Voyage is already at capacity")
        }
    }

    fun validatePassengerCancellation(id: Long) {
        val passenger = passengerService.findById(id).orElseThrow { PassengerNotFoundException(HttpStatus.BAD_REQUEST) }

        // voyage must have a future departure date
        if (!passenger.voyage.departure.isAfterToday()) {
            throw InvalidVoyageException(message = "Voyage has already departed")
        }
    }
}