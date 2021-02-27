package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class InvalidLaunchpadException(
        status: HttpStatus = HttpStatus.BAD_REQUEST,
        message: String = "Invalid launchpad")
    : CustomException(status, message) {
}