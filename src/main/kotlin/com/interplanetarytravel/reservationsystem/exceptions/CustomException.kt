package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.HttpStatus

open class CustomException(val status: HttpStatus, message: String?) : Exception(message)