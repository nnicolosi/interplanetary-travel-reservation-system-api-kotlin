package com.interplanetarytravel.reservationsystem.entities

import java.time.LocalDate
import javax.persistence.*

@Entity
data class Passenger(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate
) {

    @ManyToOne
    lateinit var voyage: Voyage
}

