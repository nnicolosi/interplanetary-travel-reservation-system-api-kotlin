package com.interplanetarytravel.reservationsystem.dtos

import com.interplanetarytravel.reservationsystem.enums.Destination

data class DestinationDto(val id: Destination, val name: String, val description: String)
