package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.DestinationDto
import com.interplanetarytravel.reservationsystem.dtos.LaunchpadDto
import com.interplanetarytravel.reservationsystem.dtos.SpacecraftDto
import com.interplanetarytravel.reservationsystem.dtos.VoyageDto
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.services.AvailabilityService
import com.interplanetarytravel.reservationsystem.utils.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/availability")
class AvailabilityController(val availabilityService: AvailabilityService) {

    @RequestMapping("ships")
    fun ships(@RequestParam(required = false) destination: String?): ResponseEntity<List<SpacecraftDto>> {
        val spacecraft = availabilityService.getAvailableSpacecraft(getValidDestinationOrNull(destination))

        return ResponseEntity(spacecraft.map { it.toSpacecraftDto() }, HttpStatus.OK)
    }

    @RequestMapping("destinations")
    fun destinations(@RequestParam(required = false) spacecraft: String?): ResponseEntity<List<DestinationDto>> {
        val destinations = availabilityService.getAvailableDestinations(getValidSpacecraftOrNull(spacecraft))

        return ResponseEntity(destinations.map { it.toDestinationDto() }, HttpStatus.OK)
    }

    @RequestMapping("launchpads")
    fun launchpads(@RequestParam(required = true) date: Date): ResponseEntity<List<LaunchpadDto>> {
        val launchpads = availabilityService.getAvailableLaunchpads(date)

        return ResponseEntity(launchpads.map { it.toLaunchpadDto() }, HttpStatus.OK)
    }

    @RequestMapping("voyages")
    fun voyages(@RequestParam(required = false) destination: String?): ResponseEntity<List<VoyageDto>> {
        val voyages = availabilityService.getAvailableVoyages(getValidDestinationOrNull(destination))

        return ResponseEntity(voyages.map { it.toVoyageDto() }, HttpStatus.OK)
    }
}