package com.interplanetarytravel.reservationsystem.utils

import com.interplanetarytravel.reservationsystem.dtos.*
import com.interplanetarytravel.reservationsystem.entities.Passenger
import com.interplanetarytravel.reservationsystem.entities.Voyage
import com.interplanetarytravel.reservationsystem.enums.Destination
import com.interplanetarytravel.reservationsystem.enums.Launchpad
import com.interplanetarytravel.reservationsystem.enums.Spacecraft
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ExtensionMethodsTest {

    @Nested
    inner class ToTitleCase {

        @Test
        fun `should return empty string when empty string is provided`() {
            val string = ""
            val result = string.toTitleCase()

            result shouldBe string
        }

        @Test
        fun `should return title cased string when string is provided`() {
            val string = "title case"
            val result = string.toTitleCase()

            result shouldBe "Title Case"
        }
    }

    @Nested
    inner class IsSameDayAs {

        @Test
        fun `should return true for same date`() {
            val date = LocalDate.of(2020, 2, 5)
            val otherDate = LocalDate.of(2020, 2, 5)

            val result = date.isSameDayAs(otherDate)

            result shouldBe true
        }

        @Test
        fun `should return false for different date`() {
            val date = LocalDate.of(2020, 2, 5)
            val otherDate = LocalDate.of(2020, 2, 6)

            val result = date.isSameDayAs(otherDate)

            result shouldBe false
        }
    }

    @Nested
    inner class IsAfterToday {

        @Test
        fun `should return true for tomorrow`() {
            val date = LocalDate.now().plusDays(1)

            val result = date.isAfterToday()

            result shouldBe true
        }

        @Test
        fun `should return false for today`() {
            val date = LocalDate.now()

            val result = date.isAfterToday()

            result shouldBe false
        }

        @Test
        fun `should return false for yesterday`() {
            val date = LocalDate.now().minusDays(1)

            val result = date.isAfterToday()

            result shouldBe false
        }
    }

    @Nested
    inner class IsBeforeToday {

        @Test
        fun `should return false for tomorrow`() {
            val date = LocalDate.now().plusDays(1)

            val result = date.isBeforeToday()

            result shouldBe false
        }

        @Test
        fun `should return false for today`() {
            val date = LocalDate.now()

            val result = date.isBeforeToday()

            result shouldBe false
        }

        @Test
        fun `should return true for yesterday`() {
            val date = LocalDate.now().minusDays(1)

            val result = date.isBeforeToday()

            result shouldBe true
        }
    }

    @Nested
    inner class ToDestinationDto {

        @Test
        fun `should map destination to destination dto`() {
            val destinationDto = DestinationDto(
                Destination.GANYMEDE,
                "Ganymede",
                Destination.GANYMEDE.description,
                Destination.GANYMEDE.diameter,
                Destination.GANYMEDE.gravity,
                Destination.GANYMEDE.orbit,
                Destination.GANYMEDE.rotation,
                Destination.GANYMEDE.day
            )

            val result = Destination.GANYMEDE.toDestinationDto()

            result shouldBe destinationDto
        }
    }

    @Nested
    inner class ToSpacecraftDto {

        @Test
        fun `should map spacecraft to spacecraft dto`() {
            val spacecraftDto = SpacecraftDto(
                Spacecraft.UNS_001,
                Spacecraft.UNS_001.description,
                Spacecraft.UNS_001.designation,
                Spacecraft.UNS_001.capacity,
                Spacecraft.UNS_001.destinations.map { it.name }
            )

            val result = Spacecraft.UNS_001.toSpacecraftDto()

            result shouldBe spacecraftDto
        }
    }

    @Nested
    inner class ToLaunchpadDto {

        @Test
        fun `should map launchpad to launchpad dto`() {
            val launchpadDto = LaunchpadDto(
                Launchpad.LP_001,
                Launchpad.LP_001.description,
                Launchpad.LP_001.designation
            )

            val result = Launchpad.LP_001.toLaunchpadDto()

            result shouldBe launchpadDto
        }
    }

    @Nested
    inner class ToVoyageDto {

        @Test
        fun `should map voyage to voyage dto`() {
            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            val result = voyage.toVoyageDto()

            result.id shouldBe voyage.id
            result.spacecraft shouldBe instanceOf<SpacecraftDto>()
            result.launchpad shouldBe instanceOf<LaunchpadDto>()
            result.destination shouldBe instanceOf<DestinationDto>()
            result.departure shouldBe voyage.departure
        }
    }

    @Nested
    inner class ToVoyage {

        @Test
        fun `should map voyage create dto to voyage`() {
            val dto = VoyageCreateDto(
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                LocalDate.now().plusDays(1)
            )

            val result = dto.toVoyage()

            result.id shouldBe 0L
            result.spacecraft shouldBe dto.spacecraft
            result.launchpad shouldBe dto.launchpad
            result.destination shouldBe dto.destination
            result.departure shouldBe dto.departure
            result.manifest shouldBe emptyList()
        }
    }

    @Nested
    inner class MapUpdatesToVoyage {

        @Test
        fun `should map updates from voyage update dto to voyage`() {
            val dto = VoyageUpdateDto(
                100L,
                Spacecraft.UNS_002,
                Launchpad.LP_002,
                LocalDate.now().plusDays(2)
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_001,
                Launchpad.LP_001,
                Destination.LUNA,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            val result = dto.mapUpdatesToVoyage(voyage)

            result.id shouldBe voyage.id
            result.spacecraft shouldBe dto.spacecraft
            result.launchpad shouldBe dto.launchpad
            result.destination shouldBe voyage.destination
            result.departure shouldBe dto.departure
            result.manifest shouldBe voyage.manifest
        }
    }

    @Nested
    inner class ToPassengerDto {

        @Test
        fun `should map passenger to passenger dto`() {
            val passenger = Passenger(
                200L,
                "First",
                "Last",
                LocalDate.of(2000, 1, 1)
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            passenger.voyage = voyage

            val dto = PassengerDto(
                200L,
                "First",
                "Last",
                LocalDate.of(2000, 1, 1),
                voyage.toVoyageDto()
            )

            val result = passenger.toPassengerDto()

            result shouldBe dto
        }
    }

    @Nested
    inner class ToPassengerNameDto {

        @Test
        fun `should map passenger to passenger name dto`() {
            val passenger = Passenger(
                200L,
                "First",
                "Last",
                LocalDate.of(2000, 1, 1)
            )

            val voyage = Voyage(
                100L,
                Spacecraft.UNS_010,
                Launchpad.LP_001,
                Destination.GANYMEDE,
                LocalDate.now().plusDays(1),
                manifest = listOf()
            )

            passenger.voyage = voyage

            val dto = PassengerNameDto(
                200L,
                "First",
                "Last"
            )

            val result = passenger.toPassengerNameDto()

            result shouldBe dto
        }
    }

    @Nested
    inner class ToPassenger {

        @Test
        fun `should map passenger create dto to passenger`() {
            val dto = PassengerCreateDto(
                "First",
                "Last",
                LocalDate.of(2000, 1, 1),
                100L
            )

            val passenger = Passenger(
                0,
                "First",
                "Last",
                LocalDate.of(2000, 1, 1)
            )

            val result = dto.toPassenger()

            result shouldBe passenger
        }
    }
}