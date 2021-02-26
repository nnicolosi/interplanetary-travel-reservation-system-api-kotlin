package com.interplanetarytravel.reservationsystem.entities

import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import java.util.*
import javax.persistence.*

@Entity
data class Voyage(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @Enumerated(EnumType.STRING)
        val spacecraft: Spacecraft,
        @Enumerated(EnumType.STRING)
        val destination: Destination,
        val departure: Date)

