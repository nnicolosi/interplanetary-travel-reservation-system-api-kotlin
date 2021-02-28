package com.interplanetarytravel.reservationsystem.services

import com.interplanetarytravel.reservationsystem.entities.Voyage
import com.interplanetarytravel.reservationsystem.repositories.IVoyageRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class VoyageService(private val voyageRepository: IVoyageRepository) {

    fun findAll(): List<Voyage> = voyageRepository.findAll()

    fun findById(id: Long): Optional<Voyage> = voyageRepository.findById(id)

    fun save(voyage: Voyage): Voyage = voyageRepository.save(voyage)

    fun cancel(id: Long) = voyageRepository.deleteById(id)
}