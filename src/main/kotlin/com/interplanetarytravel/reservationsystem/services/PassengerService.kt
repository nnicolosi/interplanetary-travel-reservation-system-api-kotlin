package com.interplanetarytravel.reservationsystem.services

import com.interplanetarytravel.reservationsystem.entities.Passenger
import com.interplanetarytravel.reservationsystem.repositories.IPassengerRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PassengerService(private val passengerRepository: IPassengerRepository) {

    fun findAll(): List<Passenger> = passengerRepository.findAll()

    fun findById(id: Long): Optional<Passenger> = passengerRepository.findById(id)

    fun save(passenger: Passenger): Passenger = passengerRepository.save(passenger)

    fun cancel(id: Long) = passengerRepository.deleteById(id)
}