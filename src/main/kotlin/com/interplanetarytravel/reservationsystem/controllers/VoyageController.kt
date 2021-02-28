package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.*
import com.interplanetarytravel.reservationsystem.exceptions.VoyageNotFoundException
import com.interplanetarytravel.reservationsystem.services.VoyageService
import com.interplanetarytravel.reservationsystem.services.VoyageValidationService
import com.interplanetarytravel.reservationsystem.utils.mapUpdatesToVoyage
import com.interplanetarytravel.reservationsystem.utils.toPassengerNameDto
import com.interplanetarytravel.reservationsystem.utils.toVoyage
import com.interplanetarytravel.reservationsystem.utils.toVoyageDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/voyage")
class VoyageController(
        private val voyageService: VoyageService,
        private val voyageValidationService: VoyageValidationService) {

    @GetMapping("")
    fun listVoyages(): ResponseEntity<List<VoyageDto>> {
        return ResponseEntity(voyageService.findAll().map { it.toVoyageDto() }, HttpStatus.OK)
    }

    @GetMapping("{id}")
    fun voyage(@PathVariable id: Long): ResponseEntity<VoyageDto> {
        val voyage = voyageService.findById(id).orElseThrow { VoyageNotFoundException() }

        return ResponseEntity(voyage.toVoyageDto(), HttpStatus.OK)
    }

    @GetMapping("{id}/manifest")
    fun manifest(@PathVariable id: Long): ResponseEntity<List<PassengerNameDto>> {
        val voyage = voyageService.findById(id).orElseThrow { VoyageNotFoundException() }
        val manifest = voyage.manifest.sortedWith(compareBy({ it.lastName }, { it.firstName }))

        return ResponseEntity(manifest.map { it.toPassengerNameDto() }, HttpStatus.OK)
    }

    @PostMapping("")
    fun create(@RequestBody dto: VoyageCreateDto): ResponseEntity<VoyageDto> {
        voyageValidationService.validateVoyageCreateDto(dto)

        val voyage = voyageService.save(dto.toVoyage())

        return ResponseEntity(voyage.toVoyageDto(), HttpStatus.CREATED)
    }

    @PutMapping("")
    fun update(@RequestBody dto: VoyageUpdateDto): ResponseEntity<VoyageDto> {
        voyageValidationService.validateVoyageUpdateDto(dto)

        val voyage = voyageService.findById(dto.id).get()
        val updated = voyageService.save(dto.mapUpdatesToVoyage(voyage))

        return ResponseEntity(updated.toVoyageDto(), HttpStatus.OK)
    }

    @DeleteMapping("{id}")
    fun cancel(@PathVariable id: Long): ResponseEntity<Unit> {
        voyageValidationService.validateVoyageCancellation(id)

        voyageService.cancel(id)

        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}