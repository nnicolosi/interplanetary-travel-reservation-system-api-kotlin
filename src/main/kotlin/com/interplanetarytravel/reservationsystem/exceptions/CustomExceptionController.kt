package com.interplanetarytravel.reservationsystem.exceptions

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class CustomExceptionController : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(exception: CustomException, request: WebRequest): ResponseEntity<CustomExceptionDetails> {
        return ResponseEntity.status(exception.status).body(CustomExceptionDetails(exception.message, Date()))
    }
}