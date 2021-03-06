package com.interplanetarytravel.reservationsystem.services

import com.interplanetarytravel.reservationsystem.entities.Passenger
import com.interplanetarytravel.reservationsystem.entities.Voyage
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import com.interplanetarytravel.reservationsystem.exceptions.SpacecraftNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AvailabilityServiceTest {

    private val voyageServiceMock: VoyageService = mockk()

    val availabilityService = AvailabilityService(voyageServiceMock)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class GetAvailableSpacecraft() {

        @Test
        fun `should return spacecraft not associated with a voyage`() {
            val voyage = Voyage(
                100L,
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            every { voyageServiceMock.findAll() } returns listOf(voyage)

            val result = availabilityService.getAvailableSpacecraft()

            result shouldBe listOf(
                Spacecraft.UNS_002,
                Spacecraft.UNS_003,
                Spacecraft.UNS_004,
                Spacecraft.UNS_005,
                Spacecraft.UNS_006,
                Spacecraft.UNS_007,
                Spacecraft.UNS_008,
                Spacecraft.UNS_009,
                Spacecraft.UNS_010
            )
        }

        @Test
        fun `should return spacecraft not associated with a voyage and certified for destination`() {
            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            every { voyageServiceMock.findAll() } returns listOf(voyage)

            val result = availabilityService.getAvailableSpacecraft(Destination.GANYMEDE)

            result shouldBe listOf(
                Spacecraft.UNS_008,
                Spacecraft.UNS_009
            )
        }
    }

    @Nested
    inner class GetAvailableDestinations() {

        @Test
        fun `should return certified destinations of spacecraft not associated with a voyage`() {
            val voyage1 = Voyage(
                101L,
                Spacecraft.UNS_008,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            val voyage2 = Voyage(
                102L,
                Spacecraft.UNS_009,
                Launchpad.LP_002,
                Destination.CALLISTO,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            val voyage3 = Voyage(
                103L,
                Spacecraft.UNS_010,
                Launchpad.LP_003,
                Destination.TITAN,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            every { voyageServiceMock.findAll() } returns listOf(voyage1, voyage2, voyage3)

            val result = availabilityService.getAvailableDestinations()

            result shouldBe listOf(
                Destination.LUNA,
                Destination.MARS
            )
        }

        @Test
        fun `should return certified destinations of a specific spacecraft`() {
            val voyage = Voyage(
                100L,
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            every { voyageServiceMock.findAll() } returns listOf(voyage)

            val result = availabilityService.getAvailableDestinations(Spacecraft.UNS_005)

            result shouldBe listOf(
                Destination.LUNA,
                Destination.MARS
            )
        }

        @Test
        fun `should throw SpacecraftNotFoundException when specific spacecraft is not available`() {
            val voyage = Voyage(
                100L,
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                LocalDate.now().plusDays(1),
                emptyList()
            )

            every { voyageServiceMock.findAll() } returns listOf(voyage)

            shouldThrow<SpacecraftNotFoundException> { availabilityService.getAvailableDestinations(Spacecraft.UNS_001) }
        }
    }

    @Nested
    inner class GetAvailableLaunchpads() {

        @Test
        fun `should return launchpads not already associated with a voyage departing on the specified date`() {
            val tomorrow = LocalDate.now().plusDays(1)

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                tomorrow,
                emptyList()
            )

            every { voyageServiceMock.findAll() } returns listOf(voyage)

            val result = availabilityService.getAvailableLaunchpads(tomorrow)

            result shouldBe listOf(
                Launchpad.LP_002,
                Launchpad.LP_003
            )
        }
    }

    @Nested
    inner class GetAvailableVoyages() {

        @Test
        fun `should return voyages departing on a future date and having remaining capacity`() {
            val voyage1 = Voyage(
                101L,
                Spacecraft.UNS_004,
                Launchpad.LP_001,
                Destination.MARS,
                LocalDate.now().minusDays(1),
                emptyList()
            )

            val voyage2 = Voyage(
                102L,
                Spacecraft.UNS_005,
                Launchpad.LP_002,
                Destination.MARS,
                LocalDate.now(),
                emptyList()
            )

            val manifest = mutableListOf<Passenger>()

            for (passenger in 1..Spacecraft.UNS_006.capacity) {
                manifest.add(Passenger(passenger.toLong(), "First", "Last", LocalDate.of(2000, 1, 1)))
            }

            val voyage3 = Voyage(
                103L,
                Spacecraft.UNS_006,
                Launchpad.LP_003,
                Destination.MARS,
                LocalDate.now().plusDays(1),
                manifest // full
            )

            val voyage4 = Voyage(
                104L,
                Spacecraft.UNS_007,
                Launchpad.LP_003,
                Destination.MARS,
                LocalDate.now().plusDays(2),
                emptyList()
            )

            every { voyageServiceMock.findAll() } returns listOf(voyage1, voyage2, voyage3, voyage4)

            val result = availabilityService.getAvailableVoyages()

            result shouldBe listOf(voyage4)
        }

        @Test
        fun `should return voyages departing on a future date, having remaining capacity, and bound for the specified destination`() {
            val voyage1 = Voyage(
                101L,
                Spacecraft.UNS_004,
                Launchpad.LP_001,
                Destination.MARS,
                LocalDate.now().minusDays(1),
                emptyList()
            )

            val voyage2 = Voyage(
                102L,
                Spacecraft.UNS_005,
                Launchpad.LP_002,
                Destination.MARS,
                LocalDate.now(),
                emptyList()
            )

            val manifest = mutableListOf<Passenger>()

            for (passenger in 1..Spacecraft.UNS_006.capacity) {
                manifest.add(Passenger(passenger.toLong(), "First", "Last", LocalDate.of(2000, 1, 1)))
            }

            val voyage3 = Voyage(
                103L,
                Spacecraft.UNS_006,
                Launchpad.LP_003,
                Destination.MARS,
                LocalDate.now().plusDays(1),
                manifest // full
            )

            val voyage4 = Voyage(
                104L,
                Spacecraft.UNS_007,
                Launchpad.LP_003,
                Destination.MARS,
                LocalDate.now().plusDays(2),
                emptyList()
            )

            val voyage5 = Voyage(
                105L,
                Spacecraft.UNS_001,
                Launchpad.LP_003,
                Destination.LUNA,
                LocalDate.now().plusDays(3),
                emptyList()
            )

            every { voyageServiceMock.findAll() } returns listOf(voyage1, voyage2, voyage3, voyage4, voyage5)

            val result = availabilityService.getAvailableVoyages(Destination.MARS)

            result shouldBe listOf(voyage4)
        }
    }
}