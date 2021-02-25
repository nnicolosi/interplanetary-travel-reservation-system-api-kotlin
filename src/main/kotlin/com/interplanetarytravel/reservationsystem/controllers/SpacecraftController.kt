package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.SpacecraftDto
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.utils.toSpacecraftDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/spacecraft")
class SpacecraftController {

    @GetMapping("")
    fun listSpacecraft(): List<SpacecraftDto> = Spacecraft.values().map { it.toSpacecraftDto() }

    @GetMapping("{id}")
    fun spacecraft(@PathVariable id: String) = Spacecraft.valueOf(id.toUpperCase()).toSpacecraftDto()
}