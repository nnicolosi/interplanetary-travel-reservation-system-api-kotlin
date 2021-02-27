package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.LaunchpadDto
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.exceptions.LaunchpadNotFoundException
import com.interplanetarytravel.reservationsystem.utils.toLaunchpadDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("/launchpad")
class LaunchpadController {

    @GetMapping("")
    fun listLaunchpads(): ResponseEntity<List<LaunchpadDto>> {
        return ResponseEntity(Launchpad.values().map { it.toLaunchpadDto() }, HttpStatus.OK)
    }

    @GetMapping("{id}")
    fun launchpad(@PathVariable id: String): ResponseEntity<LaunchpadDto> {
        return if (Launchpad.values().any { it.name == id.toUpperCase() }) {
            ResponseEntity(Launchpad.valueOf(id.toUpperCase()).toLaunchpadDto(), HttpStatus.OK)
        } else {
            throw LaunchpadNotFoundException()
        }
    }
}