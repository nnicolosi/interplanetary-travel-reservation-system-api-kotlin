package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.LaunchpadDto
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.exceptions.LaunchpadNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import org.springframework.http.ResponseEntity

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LaunchpadControllerTest {

    val launchpadController = LaunchpadController()

    @Nested
    inner class GetLaunchpad {

        @Test
        fun `should return requested launchpad`() {
            val result = launchpadController.launchpad("lp_001")

            result shouldBe instanceOf<ResponseEntity<LaunchpadDto>>()

            result.body!!.id shouldBe Launchpad.LP_001
        }

        @Test
        fun `should throw LaunchpadNotFoundException for unknown launchpad`() {
            shouldThrow<LaunchpadNotFoundException> { launchpadController.launchpad("lp_100") }
        }
    }
}