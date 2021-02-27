package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class DestinationNotFoundException(
        status: HttpStatus = HttpStatus.NOT_FOUND,
        message: String = "Destination not found")
    : CustomException(status, message) {
}