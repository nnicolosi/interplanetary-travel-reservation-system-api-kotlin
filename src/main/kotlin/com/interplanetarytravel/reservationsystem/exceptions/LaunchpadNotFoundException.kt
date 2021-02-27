package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class LaunchpadNotFoundException(
        status: HttpStatus = HttpStatus.NOT_FOUND,
        message: String = "Launchpad not found")
    : CustomException(status, message) {
}