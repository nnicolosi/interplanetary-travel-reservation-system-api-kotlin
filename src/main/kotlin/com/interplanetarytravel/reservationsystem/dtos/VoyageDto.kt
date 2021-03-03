package com.interplanetarytravel.reservationsystem.dtos

import java.time.LocalDate

data class VoyageDto(
        val id: Long,
        val spacecraft: SpacecraftDto,
        val launchpad: LaunchpadDto,
        val destination: DestinationDto,
        val departure: LocalDate
)
