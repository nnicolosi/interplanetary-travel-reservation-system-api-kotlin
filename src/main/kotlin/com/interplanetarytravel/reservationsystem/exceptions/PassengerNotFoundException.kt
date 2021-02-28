package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class PassengerNotFoundException(
        status: HttpStatus = HttpStatus.NOT_FOUND,
        message: String = "Passenger not found")
    : CustomException(status, message) {
}