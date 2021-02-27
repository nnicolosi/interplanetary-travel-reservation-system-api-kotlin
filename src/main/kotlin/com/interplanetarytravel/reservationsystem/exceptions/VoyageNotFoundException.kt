package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class VoyageNotFoundException(
        status: HttpStatus = HttpStatus.NOT_FOUND,
        message: String = "Voyage not found")
    : CustomException(status, message) {
}