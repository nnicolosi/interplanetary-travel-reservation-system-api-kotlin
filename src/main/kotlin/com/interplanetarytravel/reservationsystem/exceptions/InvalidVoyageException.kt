package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class InvalidVoyageException(
        status: HttpStatus = HttpStatus.BAD_REQUEST,
        message: String = "Invalid voyage")
    : CustomException(status, message) {
}