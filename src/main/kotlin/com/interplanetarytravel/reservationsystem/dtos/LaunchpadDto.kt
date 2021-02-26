package com.interplanetarytravel.reservationsystem.dtos

import com.interplanetarytravel.reservationsystem.enums.Launchpad

data class LaunchpadDto(
        val id: Launchpad,
        val description: String,
        val designation: String
)
