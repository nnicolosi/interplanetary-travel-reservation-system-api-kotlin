package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class InvalidDateOfBirthException(
        status: HttpStatus = HttpStatus.BAD_REQUEST,
        message: String = "Invalid date of birth")
    : CustomException(status, message) {
}