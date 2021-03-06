package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.SpacecraftDto
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.exceptions.SpacecraftNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.springframework.http.ResponseEntity

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SpacecraftControllerTest {

    val spacecraftController = SpacecraftController()

    @Nested
    inner class GetSpacecraft {

        @Test
        fun `should return requested spacecraft`() {
            val result = spacecraftController.spacecraft("uns_001")

            result shouldBe instanceOf<ResponseEntity<SpacecraftDto>>()

            result.body!!.id shouldBe Spacecraft.UNS_001
        }

        @Test
        fun `should throw SpacecraftNotFoundException for unknown spacecraft`() {
            shouldThrow<SpacecraftNotFoundException> { spacecraftController.spacecraft("uns_100") }
        }
    }
}