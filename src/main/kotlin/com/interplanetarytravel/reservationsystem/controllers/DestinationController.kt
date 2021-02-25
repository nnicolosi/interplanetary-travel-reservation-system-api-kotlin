package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.utils.toDestinationDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/destination")
class DestinationController {

    @GetMapping("")
    fun listDestinations() = Destination.values().map { it.toDestinationDto() }

    @GetMapping("{id}")
    fun destination(@PathVariable id: String) = Destination.valueOf(id.toUpperCase()).toDestinationDto()
}