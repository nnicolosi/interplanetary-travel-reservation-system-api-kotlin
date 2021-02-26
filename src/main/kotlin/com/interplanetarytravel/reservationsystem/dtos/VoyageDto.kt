package com.interplanetarytravel.reservationsystem.dtos

import java.util.*

data class VoyageDto(
        val id: Long,
        val spacecraft: SpacecraftDto,
        val launchpad: LaunchpadDto,
        val destination: DestinationDto,
        val departure: Date
)
