package com.interplanetarytravel.reservationsystem.utils

import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.exceptions.DestinationNotFoundException
import com.interplanetarytravel.reservationsystem.exceptions.SpacecraftNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EnumUtilitiesTest {

    @Nested
    inner class GetValidDestinationOrNull {

        @Test
        fun `returns null when null is provided as method parameter`() {
            val result = getValidDestinationOrNull(null)

            result shouldBe null
        }

        @Test
        fun `throws DestinationNotFoundException when matching enum is not found`() {
            shouldThrow<DestinationNotFoundException> {
                getValidDestinationOrNull("foo")
            }
        }

        @Test
        fun `returns matching enum when valid enum is specified`() {
            val result = getValidDestinationOrNull("ganymede")

            result shouldBe Destination.GANYMEDE
        }
    }

    @Nested
    inner class GetValidSpacecraftOrNull {

        @Test
        fun `returns null when null is provided as method parameter`() {
            val result = getValidSpacecraftOrNull(null)

            result shouldBe null
        }

        @Test
        fun `throws SpacecraftNotFoundException when matching enum is not found`() {
            shouldThrow<SpacecraftNotFoundException> {
                getValidSpacecraftOrNull("foo")
            }
        }

        @Test
        fun `returns matching enum when valid enum is specified`() {
            val result = getValidSpacecraftOrNull("uns_001")

            result shouldBe Spacecraft.UNS_001
        }
    }
}