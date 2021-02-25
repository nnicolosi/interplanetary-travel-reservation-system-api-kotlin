package com.interplanetarytravel.reservationsystem.enums

enum class Spacecraft(
    val description: String,
    val designation: String,
    val capacity: Int,
    val destinations: List<Destination>
) {
    // Lunar Fleet
    UNSS_001("United Nations Starship, Lunar Fleet", "UNSS 001", 100, listOf(Destination.LUNA)),
    UNSS_002("United Nations Starship, Lunar Fleet", "UNSS 001", 100, listOf(Destination.LUNA)),
    UNSS_003("United Nations Starship, Lunar Fleet", "UNSS 001", 100, listOf(Destination.LUNA)),

    // Martian Fleet
    UNSS_004("United Nations Starship, Martian Fleet", "UNSS 001", 60, listOf(Destination.LUNA, Destination.MARS)),
    UNSS_005("United Nations Starship, Martian Fleet", "UNSS 001", 60, listOf(Destination.LUNA, Destination.MARS)),
    UNSS_006("United Nations Starship, Martian Fleet", "UNSS 001", 60, listOf(Destination.LUNA, Destination.MARS)),
    UNSS_007("United Nations Starship, Martian Fleet", "UNSS 001", 60, listOf(Destination.LUNA, Destination.MARS)),

    // Outer System Fleet
    UNSS_008("United Nations Starship, Outer System Fleet", "UNSS 001", 20, Destination.values().toList()),
    UNSS_009("United Nations Starship, Outer System Fleet", "UNSS 001", 20, Destination.values().toList()),
    UNSS_010("United Nations Starship, Outer System Fleet", "UNSS 001", 20, Destination.values().toList())
}