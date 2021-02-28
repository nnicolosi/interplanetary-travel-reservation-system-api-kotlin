package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.PassengerCreateDto
import com.interplanetarytravel.reservationsystem.dtos.PassengerDto
import com.interplanetarytravel.reservationsystem.exceptions.PassengerNotFoundException
import com.interplanetarytravel.reservationsystem.exceptions.VoyageNotFoundException
import com.interplanetarytravel.reservationsystem.services.PassengerService
import com.interplanetarytravel.reservationsystem.services.PassengerValidationService
import com.interplanetarytravel.reservationsystem.services.VoyageService
import com.interplanetarytravel.reservationsystem.utils.toPassenger
import com.interplanetarytravel.reservationsystem.utils.toPassengerDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/passenger")
class PassengerController(
        private val passengerService: PassengerService,
        private val passengerValidationService: PassengerValidationService,
        private val voyageService: VoyageService) {

    @GetMapping("{id}")
    fun passenger(@PathVariable id: Long): ResponseEntity<PassengerDto> {
        val passenger = passengerService.findById(id).orElseThrow { PassengerNotFoundException() }

        return ResponseEntity(passenger.toPassengerDto(), HttpStatus.OK)
    }

    @PostMapping("")
    fun bookPassage(@RequestBody dto: PassengerCreateDto): ResponseEntity<PassengerDto> {
        passengerValidationService.validatePassengerCreateDto(dto)
        var passenger = dto.toPassenger()
        passenger.voyage = voyageService.findById(dto.voyageId).orElseThrow { VoyageNotFoundException(HttpStatus.BAD_REQUEST) }

        passenger = passengerService.save(passenger)

        return ResponseEntity(passenger.toPassengerDto(), HttpStatus.CREATED)
    }

    @DeleteMapping("{id}")
    fun cancel(@PathVariable id: Long): ResponseEntity<Unit> {
        passengerValidationService.validatePassengerCancellation(id)

        passengerService.cancel(id)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}