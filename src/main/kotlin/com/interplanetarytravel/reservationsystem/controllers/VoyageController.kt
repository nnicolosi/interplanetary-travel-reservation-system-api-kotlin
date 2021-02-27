package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.VoyageCreateDto
import com.interplanetarytravel.reservationsystem.dtos.VoyageDto
import com.interplanetarytravel.reservationsystem.dtos.VoyageUpdateDto
import com.interplanetarytravel.reservationsystem.exceptions.VoyageNotFoundException
import com.interplanetarytravel.reservationsystem.services.VoyageService
import com.interplanetarytravel.reservationsystem.utils.toVoyage
import com.interplanetarytravel.reservationsystem.utils.toVoyageDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/voyage")
class VoyageController(val voyageService: VoyageService) {

    @GetMapping("")
    fun listVoyages(): ResponseEntity<List<VoyageDto>> {
        return ResponseEntity(voyageService.findAll().map { it.toVoyageDto() }, HttpStatus.OK)
    }

    @GetMapping("{id}")
    fun voyage(@PathVariable id: Long): ResponseEntity<VoyageDto> {
        val voyage = voyageService.findById(id).orElseThrow { VoyageNotFoundException() }

        return ResponseEntity(voyage.toVoyageDto(), HttpStatus.OK)
    }

    @PostMapping("")
    fun create(@RequestBody dto: VoyageCreateDto): ResponseEntity<VoyageDto> {
        val voyage = voyageService.save(dto.toVoyage())

        return ResponseEntity(voyage.toVoyageDto(), HttpStatus.CREATED)
    }

    @PutMapping("")
    fun update(@RequestBody dto: VoyageUpdateDto): ResponseEntity<VoyageDto> {
        voyageService.findById(dto.id).orElseThrow { VoyageNotFoundException() }

        val voyage = voyageService.save(dto.toVoyage())

        return ResponseEntity(voyage.toVoyageDto(), HttpStatus.OK)
    }

    @DeleteMapping("{id}")
    fun cancel(@PathVariable id: Long): ResponseEntity<Unit> {
        voyageService.findById(id).orElseThrow { VoyageNotFoundException() }

        voyageService.cancel(id)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}