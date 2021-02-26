package com.interplanetarytravel.reservationsystem.repositories

import com.interplanetarytravel.reservationsystem.entities.Voyage
import org.springframework.data.jpa.repository.JpaRepository

interface IVoyageRepository: JpaRepository<Voyage, Long>