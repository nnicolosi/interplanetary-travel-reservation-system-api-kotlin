package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

class InvalidCancellationRequestException(
        status: HttpStatus = HttpStatus.BAD_REQUEST,
        message: String = "Invalid cancellation request")
    : CustomException(status, message)
