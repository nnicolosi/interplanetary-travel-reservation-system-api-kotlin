package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.DestinationDto
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.exceptions.DestinationNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.springframework.http.ResponseEntity

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DestinationControllerTest {

    val destinationController = DestinationController()

    @Nested
    inner class GetDestination {

        @Test
        fun `should return requested destination`() {
            val result = destinationController.destination("ganymede")

            result shouldBe instanceOf<ResponseEntity<DestinationDto>>()

            result.body!!.id shouldBe Destination.GANYMEDE
        }

        @Test
        fun `should throw DestinationNotFoundException for unknown destination`() {
            shouldThrow<DestinationNotFoundException> { destinationController.destination("pluto") }
        }
    }
}