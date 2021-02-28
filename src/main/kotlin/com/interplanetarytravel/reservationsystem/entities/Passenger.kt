package com.interplanetarytravel.reservationsystem.entities

import java.util.*
import javax.persistence.*

@Entity
data class Passenger(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val firstName: String,
        val lastName: String,
        val dateOfBirth: Date) {

    @ManyToOne
    lateinit var voyage: Voyage
}

