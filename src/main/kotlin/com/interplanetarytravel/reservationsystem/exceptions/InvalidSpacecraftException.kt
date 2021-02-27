package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class InvalidSpacecraftException(
        status: HttpStatus = HttpStatus.BAD_REQUEST,
        message: String = "Invalid spacecraft")
    : CustomException(status, message) {
}