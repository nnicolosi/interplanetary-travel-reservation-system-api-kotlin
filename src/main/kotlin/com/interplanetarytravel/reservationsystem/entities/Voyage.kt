package com.interplanetarytravel.reservationsystem.entities

import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import java.time.LocalDate
import javax.persistence.*

@Entity
data class Voyage(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Enumerated(EnumType.STRING)
    val spacecraft: Spacecraft,
    @Enumerated(EnumType.STRING)
    val launchpad: Launchpad,
    @Enumerated(EnumType.STRING)
    val destination: Destination,
    val departure: LocalDate,
    @OneToMany(mappedBy = "voyage")
    var manifest: List<Passenger>
)


