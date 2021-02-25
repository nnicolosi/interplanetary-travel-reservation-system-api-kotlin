package com.interplanetarytravel.reservationsystem.enums

enum class Spacecraft(
    val description: String,
    val designation: String,
    val capacity: Int,
    val destinations: List<Destination>
) {
    // Lunar Fleet
    UNS_001("United Nations Starship, Lunar Fleet", "UNS 001", 100, listOf(Destination.LUNA)),
    UNS_002("United Nations Starship, Lunar Fleet", "UNS 001", 100, listOf(Destination.LUNA)),
    UNS_003("United Nations Starship, Lunar Fleet", "UNS 001", 100, listOf(Destination.LUNA)),

    // Martian Fleet
    UNS_004("United Nations Starship, Martian Fleet", "UNS 001", 60, listOf(Destination.LUNA, Destination.MARS)),
    UNS_005("United Nations Starship, Martian Fleet", "UNS 001", 60, listOf(Destination.LUNA, Destination.MARS)),
    UNS_006("United Nations Starship, Martian Fleet", "UNS 001", 60, listOf(Destination.LUNA, Destination.MARS)),
    UNS_007("United Nations Starship, Martian Fleet", "UNS 001", 60, listOf(Destination.LUNA, Destination.MARS)),

    // Outer System Fleet
    UNS_008("United Nations Starship, Outer System Fleet", "UNS 001", 20, Destination.values().toList()),
    UNS_009("United Nations Starship, Outer System Fleet", "UNS 001", 20, Destination.values().toList()),
    UNS_010("United Nations Starship, Outer System Fleet", "UNS 001", 20, Destination.values().toList())
}