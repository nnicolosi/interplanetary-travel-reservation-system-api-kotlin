package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.SpacecraftDto
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/spacecraft")
class SpacecraftController {

    @GetMapping("")
    fun spacecraft(): List<SpacecraftDto> = Spacecraft.values().map {
        SpacecraftDto(it, it.description, it.designation, it.capacity, it.destinations)
    }
}