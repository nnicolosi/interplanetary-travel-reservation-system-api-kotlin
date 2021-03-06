package com.interplanetarytravel.reservationsystem.controllers

import com.interplanetarytravel.reservationsystem.dtos.PassengerCreateDto
import com.interplanetarytravel.reservationsystem.dtos.PassengerDto
import com.interplanetarytravel.reservationsystem.entities.Passenger
import com.interplanetarytravel.reservationsystem.entities.Voyage
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.exceptions.PassengerNotFoundException
import com.interplanetarytravel.reservationsystem.exceptions.VoyageNotFoundException
import com.interplanetarytravel.reservationsystem.services.PassengerService
import com.interplanetarytravel.reservationsystem.services.PassengerValidationService
import com.interplanetarytravel.reservationsystem.services.VoyageService
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
internal class PassengerControllerTest {

    private val passengerServiceMock: PassengerService = mockk()
    private val passengerValidationServiceMock: PassengerValidationService = mockk()
    private val voyageServiceMock: VoyageService = mockk()

    val passengerController =
        PassengerController(passengerServiceMock, passengerValidationServiceMock, voyageServiceMock)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class GetPassenger {

        @Test
        fun `should return passenger dto if found`() {
            val passenger = Passenger(
                100L,
                "First",
                "Last",
                LocalDate.of(2000, 1, 1)
            )

            val voyage = Voyage(
                200L,
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            passenger.voyage = voyage

            every { passengerServiceMock.findById(100L) } returns Optional.ofNullable(passenger)

            val result = passengerController.passenger(100L)

            result shouldBe instanceOf<ResponseEntity<PassengerDto>>()
            result.body!!.id shouldBe passenger.id
        }

        @Test
        fun `should throw PassengerNotFoundException if passenger is not found`() {
            every { passengerServiceMock.findById(any()) } returns Optional.ofNullable(null)

            shouldThrow<PassengerNotFoundException> { passengerController.passenger(100L) }
        }
    }

    @Nested
    inner class BookPassage {

        @Test
        fun `should return passenger dto`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.of(2000, 1, 1),
                200L
            )

            val passenger = Passenger(
                100L,
                "First",
                "Last",
                LocalDate.of(2000, 1, 1)
            )

            val voyage = Voyage(
                200L,
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            passenger.voyage = voyage

            every { passengerValidationServiceMock.validatePassengerCreateDto(dto) } returns Unit
            every { voyageServiceMock.findById(200L) } returns Optional.ofNullable(voyage)
            every { passengerServiceMock.save(any()) } returns passenger

            val result = passengerController.bookPassage(dto)

            result shouldBe instanceOf<ResponseEntity<PassengerDto>>()
            result.body!!.id shouldBe passenger.id
        }

        @Test
        fun `should throw VoyageNotFoundException if voyage is not found`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.of(2000, 1, 1),
                200L
            )

            every { passengerValidationServiceMock.validatePassengerCreateDto(dto) } returns Unit
            every { voyageServiceMock.findById(200L) } returns Optional.ofNullable(null)

            shouldThrow<VoyageNotFoundException> { passengerController.bookPassage(dto) }
        }
    }
}