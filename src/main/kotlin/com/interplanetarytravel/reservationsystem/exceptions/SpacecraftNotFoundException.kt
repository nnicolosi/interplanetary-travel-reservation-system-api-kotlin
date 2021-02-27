package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class SpacecraftNotFoundException(
        status: HttpStatus = HttpStatus.NOT_FOUND,
        message: String = "Spacecraft not found")
    : CustomException(status, message) {
}