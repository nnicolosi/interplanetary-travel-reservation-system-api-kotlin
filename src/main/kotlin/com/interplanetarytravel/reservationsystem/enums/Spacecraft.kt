package com.interplanetarytravel.reservationsystem.enums

enum class Spacecraft(
        val description: String,
        val designation: String,
        val capacity: Int,
        val destinations: List<Destination>
) {
    // Lunar Fleet
    UNS_001("United Nations Starship, Lunar Fleet", "UNS 001", 100, listOf(Destination.LUNA)),
    UNS_002("United Nations Starship, Lunar Fleet", "UNS 002", 100, listOf(Destination.LUNA)),
    UNS_003("United Nations Starship, Lunar Fleet", "UNS 003", 100, listOf(Destination.LUNA)),

    // Martian Fleet
    UNS_004("United Nations Starship, Martian Fleet", "UNS 004", 60, listOf(Destination.LUNA, Destination.MARS)),
    UNS_005("United Nations Starship, Martian Fleet", "UNS 005", 60, listOf(Destination.LUNA, Destination.MARS)),
    UNS_006("United Nations Starship, Martian Fleet", "UNS 006", 60, listOf(Destination.LUNA, Destination.MARS)),
    UNS_007("United Nations Starship, Martian Fleet", "UNS 007", 60, listOf(Destination.LUNA, Destination.MARS)),

    // Outer System Fleet
    UNS_008("United Nations Starship, Outer System Fleet", "UNS 008", 20, Destination.values().toList()),
    UNS_009("United Nations Starship, Outer System Fleet", "UNS 009", 20, Destination.values().toList()),
    UNS_010("United Nations Starship, Outer System Fleet", "UNS 010", 20, Destination.values().toList())
}