package com.interplanetarytravel.reservationsystem.repositories

import com.interplanetarytravel.reservationsystem.entities.Passenger
import org.springframework.data.jpa.repository.JpaRepository

interface IPassengerRepository : JpaRepository<Passenger, Long>