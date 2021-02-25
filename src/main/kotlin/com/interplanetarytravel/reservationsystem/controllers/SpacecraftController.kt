package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.SpacecraftDto
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.utils.toSpacecraftDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/spacecraft")
class SpacecraftController {

    @GetMapping("")
    fun listSpacecraft(): ResponseEntity<List<SpacecraftDto>> {
        return ResponseEntity(Spacecraft.values().map { it.toSpacecraftDto() }, HttpStatus.OK)
    }

    @GetMapping("{id}")
    fun spacecraft(@PathVariable id: String): ResponseEntity<SpacecraftDto> {
        return if (Spacecraft.values().any { it.name == id.toUpperCase() }) {
            ResponseEntity(Spacecraft.valueOf(id.toUpperCase()).toSpacecraftDto(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}