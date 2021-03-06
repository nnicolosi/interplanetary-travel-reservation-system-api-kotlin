package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.PassengerNameDto
import com.interplanetarytravel.reservationsystem.dtos.VoyageDto
import com.interplanetarytravel.reservationsystem.entities.Passenger
import com.interplanetarytravel.reservationsystem.entities.Voyage
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.exceptions.VoyageNotFoundException
import com.interplanetarytravel.reservationsystem.services.VoyageService
import com.interplanetarytravel.reservationsystem.services.VoyageValidationService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.ResponseEntity
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class VoyageControllerTest {

    private val voyageServiceMock: VoyageService = mockk()
    private val voyageValidationServiceMock: VoyageValidationService = mockk()

    val voyController = VoyageController(voyageServiceMock, voyageValidationServiceMock)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class GetVoyage {
        @Test
        fun `should return voyage dto if found`() {
            val voyage = Voyage(
                200L,
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            every { voyageServiceMock.findById(200L) } returns Optional.ofNullable(voyage)

            val result = voyController.voyage(200L)

            result shouldBe instanceOf<ResponseEntity<VoyageDto>>()
            result.body!!.id shouldBe voyage.id
        }

        @Test
        fun `should throw VoyageNotFoundException if voyage is not found`() {
            every { voyageServiceMock.findById(any()) } returns Optional.ofNullable(null)

            shouldThrow<VoyageNotFoundException> { voyController.voyage(200L) }
        }
    }

    @Nested
    inner class GetManifest {
        @Test
        fun `should return list of passsenger dtos if voyage is found`() {
            val passenger1 = Passenger(
                101L,
                "First",
                "First",
                LocalDate.of(2000, 1, 1)
            )

            val passenger2 = Passenger(
                102L,
                "Second",
                "Second",
                LocalDate.of(2000, 1, 1)
            )

            val voyage = Voyage(
                200L,
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                LocalDate.now().plusDays(1),
                listOf(passenger1, passenger2)
            )

            every { voyageServiceMock.findById(200L) } returns Optional.ofNullable(voyage)

            val result = voyController.manifest(200L)

            result shouldBe instanceOf<ResponseEntity<List<PassengerNameDto>>>()
            result.body!!.size shouldBe 2
        }

        @Test
        fun `should throw VoyageNotFoundException if voyage is not found`() {
            every { voyageServiceMock.findById(any()) } returns Optional.ofNullable(null)

            shouldThrow<VoyageNotFoundException> { voyController.manifest(200L) }
        }
    }
}