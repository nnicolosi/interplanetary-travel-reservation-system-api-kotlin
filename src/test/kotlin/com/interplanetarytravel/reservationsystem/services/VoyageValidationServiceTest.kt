package com.interplanetarytravel.reservationsystem.services

import com.interplanetarytravel.reservationsystem.dtos.VoyageCreateDto
import com.interplanetarytravel.reservationsystem.dtos.VoyageUpdateDto
import com.interplanetarytravel.reservationsystem.entities.Passenger
import com.interplanetarytravel.reservationsystem.entities.Voyage
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.exceptions.*
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
internal class VoyageValidationServiceTest {

    private val availabilityService: AvailabilityService = mockk()
    private val voyageService: VoyageService = mockk()

    val voyageValidationService = VoyageValidationService(availabilityService, voyageService)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class ValidateVoyageCreateDto {

        @Test
        fun `should not throw for valid input`() {
            val dto = VoyageCreateDto(
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1)
            )

            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(dto.destination) } returns Spacecraft.values().asList()

            shouldNotThrowAny { voyageValidationService.validateVoyageCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidDepartureDateException for input containing past departure date`() {
            val dto = VoyageCreateDto(
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().minusMonths(1)
            )

            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(dto.destination) } returns Spacecraft.values().asList()

            shouldThrow<InvalidDepartureDateException> { voyageValidationService.validateVoyageCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidDepartureDateException for input containing departure date of today`() {
            val dto = VoyageCreateDto(
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now()
            )

            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(dto.destination) } returns Spacecraft.values().asList()

            shouldThrow<InvalidDepartureDateException> { voyageValidationService.validateVoyageCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidLaunchpadException for input containing unavailable launchpad`() {
            val dto = VoyageCreateDto(
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1)
            )

            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns listOf(Launchpad.LP_002, Launchpad.LP_003)
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(dto.destination) } returns Spacecraft.values().asList()

            shouldThrow<InvalidLaunchpadException> { voyageValidationService.validateVoyageCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidSpacecraftException for input containing unavailable spacecraft`() {
            val dto = VoyageCreateDto(
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1)
            )

            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns listOf(Spacecraft.UNS_001, Spacecraft.UNS_002)
            every { availabilityService.getAvailableSpacecraft(dto.destination) } returns Spacecraft.values().asList()

            shouldThrow<InvalidSpacecraftException> { voyageValidationService.validateVoyageCreateDto(dto) }
        }

        @Test
        fun `should throw InvalidSpacecraftException for input containing unavailable spacecraft per destination`() {
            val dto = VoyageCreateDto(
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1)
            )

            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(dto.destination) } returns listOf(Spacecraft.UNS_008, Spacecraft.UNS_009)

            shouldThrow<InvalidSpacecraftException> { voyageValidationService.validateVoyageCreateDto(dto) }
        }
    }

    @Nested
    inner class ValidateVoyageUpdateDto {

        @Test
        fun `should not throw for valid input`() {
            val dto = VoyageUpdateDto(
                100L,
                Spacecraft.UNS_009,
                Launchpad.LP_002,
                LocalDate.now().plusDays(2)
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageService.findById(dto.id) } returns Optional.ofNullable(voyage)
            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(voyage.destination) } returns Spacecraft.values().asList()

            shouldNotThrowAny { voyageValidationService.validateVoyageUpdateDto(dto) }
        }

        @Test
        fun `should throw VoyageNotFoundException for non-existent voyage`() {
            val dto = VoyageUpdateDto(
                200L,
                Spacecraft.UNS_009,
                Launchpad.LP_002,
                LocalDate.now().plusDays(2)
            )

            every { voyageService.findById(dto.id) } returns Optional.ofNullable(null)

            shouldThrow<VoyageNotFoundException> { voyageValidationService.validateVoyageUpdateDto(dto) }
        }

        @Test
        fun `should throw InvalidDepartureDateException for input containing past departure date`() {
            val dto = VoyageUpdateDto(
                100L,
                Spacecraft.UNS_009,
                Launchpad.LP_002,
                LocalDate.now().minusDays(1)
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageService.findById(dto.id) } returns Optional.ofNullable(voyage)
            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(voyage.destination) } returns Spacecraft.values().asList()

            shouldThrow<InvalidDepartureDateException> { voyageValidationService.validateVoyageUpdateDto(dto) }
        }

        @Test
        fun `should throw InvalidDepartureDateException for input containing departure date of today`() {
            val dto = VoyageUpdateDto(
                100L,
                Spacecraft.UNS_009,
                Launchpad.LP_002,
                LocalDate.now()
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageService.findById(dto.id) } returns Optional.ofNullable(voyage)
            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(voyage.destination) } returns Spacecraft.values().asList()

            shouldThrow<InvalidDepartureDateException> { voyageValidationService.validateVoyageUpdateDto(dto) }
        }

        @Test
        fun `should throw InvalidLaunchpadException for input containing unavailable launchpad`() {
            val dto = VoyageUpdateDto(
                100L,
                Spacecraft.UNS_009,
                Launchpad.LP_002,
                LocalDate.now().plusDays(2)
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageService.findById(dto.id) } returns Optional.ofNullable(voyage)
            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns listOf(Launchpad.LP_003)
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(voyage.destination) } returns Spacecraft.values().asList()

            shouldThrow<InvalidLaunchpadException> { voyageValidationService.validateVoyageUpdateDto(dto) }
        }

        @Test
        fun `should throw InvalidSpacecraftException for input containing unavailable spacecraft`() {
            val dto = VoyageUpdateDto(
                100L,
                Spacecraft.UNS_009,
                Launchpad.LP_002,
                LocalDate.now().plusDays(2)
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageService.findById(dto.id) } returns Optional.ofNullable(voyage)
            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns listOf(Spacecraft.UNS_001, Spacecraft.UNS_002)
            every { availabilityService.getAvailableSpacecraft(voyage.destination) } returns Spacecraft.values().asList()

            shouldThrow<InvalidSpacecraftException> { voyageValidationService.validateVoyageUpdateDto(dto) }
        }

        @Test
        fun `should throw InvalidSpacecraftException for input containing unavailable spacecraft per destination`() {
            val dto = VoyageUpdateDto(
                100L,
                Spacecraft.UNS_009,
                Launchpad.LP_002,
                LocalDate.now().plusDays(2)
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageService.findById(dto.id) } returns Optional.ofNullable(voyage)
            every { availabilityService.getAvailableLaunchpads(dto.departure) } returns Launchpad.values().asList()
            every { availabilityService.getAvailableSpacecraft() } returns Spacecraft.values().asList()
            every { availabilityService.getAvailableSpacecraft(voyage.destination) } returns listOf(Spacecraft.UNS_008)

            shouldThrow<InvalidSpacecraftException> { voyageValidationService.validateVoyageUpdateDto(dto) }
        }
    }

    @Nested
    inner class ValidateVoyageCancellation {

        @Test
        fun `should not throw for valid input`() {
            val id = 100L
            val voyage = Voyage(
                id,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            every { voyageService.findById(id) } returns Optional.ofNullable(voyage)

            shouldNotThrowAny { voyageValidationService.validateVoyageCancellation(id) }
        }

        @Test
        fun `should throw VoyageNotFoundException for input specifying non-existent voyage`() {
            val id = 100L

            every { voyageService.findById(id) } returns Optional.ofNullable(null)

            shouldThrow<VoyageNotFoundException> { voyageValidationService.validateVoyageCancellation(id) }
        }

        @Test
        fun `should throw InvalidCancellationRequestException for voyage with departure date in the past`() {
            val id = 100L
            val voyage = Voyage(
                id,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().minusDays(1),
                manifest = listOf()
            )

            every { voyageService.findById(id) } returns Optional.ofNullable(voyage)

            shouldThrow<InvalidCancellationRequestException> { voyageValidationService.validateVoyageCancellation(id) }
        }

        @Test
        fun `should throw InvalidCancellationRequestException for voyage with departure date of today`() {
            val id = 100L
            val voyage = Voyage(
                id,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now(),
                manifest = listOf()
            )

            every { voyageService.findById(id) } returns Optional.ofNullable(voyage)

            shouldThrow<InvalidCancellationRequestException> { voyageValidationService.validateVoyageCancellation(id) }
        }

        @Test
        fun `should throw InvalidCancellationRequestException for voyage with passengers on its manifest`() {
            val id = 100L
            val voyage = Voyage(
                id,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf(Passenger(1000L, "First", "Last", LocalDate.of(2000, 1, 1)))
            )

            every { voyageService.findById(id) } returns Optional.ofNullable(voyage)

            shouldThrow<InvalidCancellationRequestException> { voyageValidationService.validateVoyageCancellation(id) }
        }
    }
}