package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.DestinationDto
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.exceptions.DestinationNotFoundException
import com.interplanetarytravel.reservationsystem.utils.toDestinationDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/destination")
class DestinationController {

    @GetMapping("")
    fun listDestinations(): ResponseEntity<List<DestinationDto>> {
        return ResponseEntity(Destination.values().map { it.toDestinationDto() }, HttpStatus.OK)
    }

    @GetMapping("{id}")
    fun destination(@PathVariable id: String): ResponseEntity<DestinationDto> {
        return if (Destination.values().any { it.name == id.toUpperCase() }) {
            ResponseEntity(Destination.valueOf(id.toUpperCase()).toDestinationDto(), HttpStatus.OK)
        } else {
            throw DestinationNotFoundException()
        }
    }
}