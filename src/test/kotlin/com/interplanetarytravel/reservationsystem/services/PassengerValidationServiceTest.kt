package com.interplanetarytravel.reservationsystem.services

import com.interplanetarytravel.reservationsystem.dtos.PassengerCreateDto
import com.interplanetarytravel.reservationsystem.entities.Passenger
import com.interplanetarytravel.reservationsystem.entities.Voyage
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.exceptions.InvalidDateOfBirthException
import com.interplanetarytravel.reservationsystem.exceptions.InvalidVoyageException
import com.interplanetarytravel.reservationsystem.exceptions.PassengerNotFoundException
import com.interplanetarytravel.reservationsystem.exceptions.VoyageNotFoundException
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PassengerValidationServiceTest {

    private val passengerServiceMock: PassengerService = mockk()
    private val voyageServiceMock: VoyageService = mockk()

    val passengerValidationService = PassengerValidationService(passengerServiceMock, voyageServiceMock)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class ValidatePassengerCreateDto {

        @Test
        fun `should not throw for valid input`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.of(2000, 1, 1),
                100L
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageServiceMock.findById(dto.voyageId) } returns Optional.ofNullable(voyage)

            shouldNotThrowAny { passengerValidationService.validatePassengerCreateDto(dto) }
        }

        @Test
        fun `should throw VoyageNotFoundException for input specifying non-existent voyage`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.of(2000, 1, 1),
                100L
            )

            every { voyageServiceMock.findById(dto.voyageId) } returns Optional.ofNullable(null)

            shouldThrow<VoyageNotFoundException> { passengerValidationService.validatePassengerCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidDateOfBirthException for passenger with date of birth in the future`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.now().plusDays(1),
                100L
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageServiceMock.findById(dto.voyageId) } returns Optional.ofNullable(voyage)

            shouldThrow<InvalidDateOfBirthException> { passengerValidationService.validatePassengerCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidDateOfBirthException for passenger with date of birth of today`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.now(),
                100L
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageServiceMock.findById(dto.voyageId) } returns Optional.ofNullable(voyage)

            shouldThrow<InvalidDateOfBirthException> { passengerValidationService.validatePassengerCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidVoyageException for voyage with departure date in the past`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.of(2000, 1, 1),
                100L
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().minusDays(1),
                manifest = listOf()
            )

            every { voyageServiceMock.findById(dto.voyageId) } returns Optional.ofNullable(voyage)

            shouldThrow<InvalidVoyageException> { passengerValidationService.validatePassengerCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidVoyageException for voyage with departure date of today`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.of(2000, 1, 1),
                100L
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now(),
                manifest = listOf()
            )

            every { voyageServiceMock.findById(dto.voyageId) } returns Optional.ofNullable(voyage)

            shouldThrow<InvalidVoyageException> { passengerValidationService.validatePassengerCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidVoyageException for voyage at max capacity`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.of(2000, 1, 1),
                100L
            )

            val manifest = mutableListOf<Passenger>()

            for (passenger in 1..Spacecraft.UNS_010.capacity) {
                manifest.add(Passenger(passenger.toLong(), "First", "Last", LocalDate.of(2000, 1, passenger)))
            }

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now(),
                manifest = manifest
            )

            every { voyageServiceMock.findById(dto.voyageId) } returns Optional.ofNullable(voyage)

            shouldThrow<InvalidVoyageException> { passengerValidationService.validatePassengerCreateDto(dto) }
        }
    }

    @Nested
    inner class ValidatePassengerCancellation {
        @Test
        fun `should not throw for valid input`() {
            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )
            val passenger = Passenger(
                1000L,
                "First",
                "Last",
                LocalDate.of(2000, 1, 1)
            )
            passenger.voyage = voyage

            every { passengerServiceMock.findById(passenger.id) } returns Optional.ofNullable(passenger)

            shouldNotThrowAny { passengerValidationService.validatePassengerCancellation(passenger.id) }
        }

        @Test
        fun `should throw PassengerNotFoundException when non-existent passenger is specified`() {
            val id = 1000L

            every { passengerServiceMock.findById(id) } returns Optional.ofNullable(null)

            shouldThrow<PassengerNotFoundException> { passengerValidationService.validatePassengerCancellation(id) }
        }

        @Test
        fun `should throw InvalidVoyageException for voyage with departure date in the past`() {
            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().minusDays(1),
                manifest = listOf()
            )
            val passenger = Passenger(
                1000L,
                "First",
                "Last",
                LocalDate.of(2000, 1, 1)
            )
            passenger.voyage = voyage

            every { passengerServiceMock.findById(passenger.id) } returns Optional.ofNullable(passenger)

            shouldThrow<InvalidVoyageException> { passengerValidationService.validatePassengerCancellation(passenger.id) }
        }

        @Test
        fun `should throw InvalidVoyageException for voyage with departure date of today`() {
            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now(),
                manifest = listOf()
            )
            val passenger = Passenger(
                1000L,
                "First",
                "Last",
                LocalDate.of(2000, 1, 1)
            )
            passenger.voyage = voyage

            every { passengerServiceMock.findById(passenger.id) } returns Optional.ofNullable(passenger)

            shouldThrow<InvalidVoyageException> { passengerValidationService.validatePassengerCancellation(passenger.id) }
        }
    }
}