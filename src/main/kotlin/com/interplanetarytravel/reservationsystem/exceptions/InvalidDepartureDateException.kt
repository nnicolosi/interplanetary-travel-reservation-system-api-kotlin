package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class InvalidDepartureDateException(
        status: HttpStatus = HttpStatus.BAD_REQUEST,
        message: String = "Invalid departure date")
    : CustomException(status, message) {
}