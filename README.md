[![.github/workflows/pipeline.yml](https://github.com/nnicolosi/interplanetary-travel-reservation-system-api-kotlin/actions/workflows/pipeline.yml/badge.svg)](https://github.com/nnicolosi/interplanetary-travel-reservation-system-api-kotlin/actions/workflows/pipeline.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=nnicolosi_interplanetary-travel-reservation-system-api-kotlin&metric=alert_status)](https://sonarcloud.io/dashboard?id=nnicolosi_interplanetary-travel-reservation-system-api-kotlin)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=nnicolosi_interplanetary-travel-reservation-system-api-kotlin&metric=ncloc)](https://sonarcloud.io/dashboard?id=nnicolosi_interplanetary-travel-reservation-system-api-kotlin)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=nnicolosi_interplanetary-travel-reservation-system-api-kotlin&metric=coverage)](https://sonarcloud.io/dashboard?id=nnicolosi_interplanetary-travel-reservation-system-api-kotlin)

# Interplanetary Travel Reservation System API

![ganymede.png](src/main/resources/ganymede.png)

<br>

## Description ##

This repository contains a Gradle project for a web service API written in Kotlin and leveraging the Spring Boot framework. It uses an H2 database, which can be configured for either the in-memory (default) or the file-based mode of operation.

The project represents a contrived business case, that of an interplanetary travel provider requiring a system for
scheduling voyages and booking passage for individual space travellers.

<br>

## Domain Model ##

There are only a handful of entities in the domain model, each one described below.

### Destination ###

Destinations represent the planetary bodies (planets and moons) that are available for voyages. They are implemented as an
enumeration to simplify the implementation. This approach seems justified due to the fact that they are not expected to change
frequently.

> <details>
>     <summary>Available Destinations</summary>
>
>     - Callisto
>     - Europa
>     - Ganymede
>     - Io
>     - Luna
>     - Mars
>     - Titan
> </details>

### Spacecraft ###

Spacecraft represent the vehicles in the company's fleet that are available for voyages. Different spacecraft are
certified for different destinations and may have different passenger capacity. They are implemented as an
enumeration to simplify the implementation. This approach seems justified due to the fact that they are not expected to change
frequently.

> <details>
>     <summary>Available Spacecraft</summary>
>
>     - UNS-001 (capacity of 100, certified for Luna only)
>     - UNS-002 (capacity of 100, certified for Luna only)
>     - UNS-003 (capacity of 100, certified for Luna only)
>     - UNS-004 (capacity of 60, certified for Luna and Mars only)
>     - UNS-005 (capacity of 60, certified for Luna and Mars only)
>     - UNS-006 (capacity of 60, certified for Luna and Mars only)
>     - UNS-007 (capacity of 60, certified for Luna and Mars only)
>     - UNS-008 (capacity of 20, certified for all destinations)
>     - UNS-009 (capacity of 20, certified for all destinations)
>     - UNS-010 (capacity of 20, certified for all destinations)
> </details>

### Launchpad ###

Launchpads are the origin point for any voyage. They are implemented as an
enumeration to simplify the implementation. This approach seems justified due to the fact that they are not expected to change
frequently.

> <details>
>     <summary>Available Launchpads</summary>
> 
>     - Launch Pad #1
>     - Launch Pad #2
>     - Launch Pad #3
> </details>

### Voyage ###

Voyages are the central domain entity for the system. They are composed of a spacecraft, a destination, a launchpad, a
departure date, and a manifest (of passengers). The system doesn't currently account for return trips, so a voyage
effectively "consumes" spacecraft.

### Passenger ###

Passengers are added to the manifest of a voyage. In a more sophisticated system, there would be customers that exist
independent of voyages, and each customer would be associated with one or more voyages via a relation entity such as "passage" or "
reservation". Perhaps in a future iteration of the system this will be done, but for now this was a convenient way to
limit scope.

<br>

## API Endpoints ##

<br>

### Destination ###

---

>`GET: /destination` 

Returns a JSON array containing all destinations

---

>`GET: /destination/{id}`

Returns a single destination specified by`id` (case-insensitive)

JSON Example of a Destination:
 ```json
 {
     "id": "EUROPA",
     "name": "Europa",
     "description": "Slightly smaller than Earth's Moon...",
     "diameter": "3,121 km",
     "gravity": "0.134 g",
     "orbit": "Orbits Jupiter once every 85 hours",
     "rotation": "Tidally locked with Jupiter",
     "day": "85 hours"
 }
 ```

---
<br>

### Spacecraft ###

---

>`GET: /spacecraft`

Returns a JSON array containing all spacecraft

---

>`GET: /spacecraft/{id}`

Returns a single spacecraft specified by`id` (case-insensitive)

JSON Example of a Spacecraft:
```json
{
  "id": "UNS_008",
  "description": "United Nations Starship, Outer System Fleet",
  "designation": "UNS 008",
  "capacity": 20,
  "destinations": [
    "CALLISTO",
    "EUROPA",
    "GANYMEDE",
    "IO",
    "LUNA",
    "MARS",
    "TITAN"
  ]
}
```

---
<br>

### Launchpad ###

---

>`GET: /launchpad` 

Returns a JSON array containing all launchpads

---

>`GET: /launchpad/{id}` 

Returns a single launchpad specified by`id` (case-insensitive)

JSON Example of a Launchpad:
```json
{
  "id": "LP_001",
  "description": "Launch Pad #1",
  "designation": "LP1"
}
```

---
<br>

### Voyage ###

---

>`GET: /voyage`

Returns a JSON array containing all voyages

---

>`GET: /voyage/{id}`

Returns a single voyage specified by`id` 

JSON Example of a Voyage:
```json
{
  "id": 1,
  "spacecraft": {
    "id": "UNS_001",
    "description": "United Nations Starship, Lunar Fleet",
    "designation": "UNS 001",
    "capacity": 100,
    "destinations": [
      "LUNA"
    ]
  },
  "launchpad": {
    "id": "LP_001",
    "description": "Launch Pad #1",
    "designation": "LP1"
  },
  "destination": {
    "id": "LUNA",
    "name": "Luna",
    "description": "Luna is the only moon of Earth...",
    "diameter": "3,475 km",
    "gravity": "0.1654 g",
    "orbit": "Orbits Earth every 708 hours",
    "rotation": "Tidally locked with Earth",
    "day": "708 hours"
  },
  "departure": "2021-03-30T00:00:00.000+00:00"
}
```

---

>`POST: /voyage`

Creates a voyage from the JSON request body

JSON Example of a POST Request Body:
```json
{
  "spacecraft": "UNS_001",
  "launchpad": "LP_001",
  "destination": "LUNA",
  "departure": "2021-03-30"
}
```

Validation Rules:
- The spacecraft must be available (not reserved for another voyage)
- The spacecraft must be certified for the destination
- The launchpad must be available on the departure date
- The departure date must be in the future

---

>`PUT: /voyage` 

Updates the voyage specified by the `id` property in the JSON request body

JSON Example of a PUT Request Body:
```json
{
  "id": 1,
  "spacecraft": "UNS_003",
  "launchpad": "LP_001",
  "departure": "2021-03-30"
}
```

Validation Rules:
- The spacecraft must be available (not reserved for another voyage)
- The spacecraft must be certified for the destination
- The launchpad must be available on the departure date
- The departure date must be in the future

---

>`DELETE: /voyage/{id}`

Cancels (deletes) the voyage specified by`id`

Validation Rules:
- The manifest must be empty (no passengers)
- The departure date must be in the future

---

>`GET: /voyage/{id}/manifest` 

Returns a JSON array containing passengers for the voyage

JSON Example of a Voyage Manifest:
```json
[
  {
    "id": 1,
    "firstName": "Nick",
    "lastName": "Nicolosi"
  },
  {
    "id": 2,
    "firstName": "Nicole",
    "lastName": "Nicolosi"
  }
]
```

---
<br>

### Passenger ###

---

>`GET: /passenger/{id}`

Returns a single passenger specified by`id`

JSON Example of a Passenger:
```json
{
  "id": 1,
  "firstName": "Nick",
  "lastName": "Nicolosi",
  "dateOfBirth": "1969-02-05T00:00:00.000+00:00",
  "voyage": {
    "id": 1,
    "spacecraft": {
      "id": "UNS_001",
      "description": "United Nations Starship, Lunar Fleet",
      "designation": "UNS 001",
      "capacity": 100,
      "destinations": [
        "LUNA"
      ]
    },
    "launchpad": {
      "id": "LP_001",
      "description": "Launch Pad #1",
      "designation": "LP1"
    },
    "destination": {
      "id": "LUNA",
      "name": "Luna",
      "description": "Luna is the only moon of Earth, and the fifth largest moon in the Solar System. It is one-quarter the diameter of Earth, making it the largest natural satellite in the Solar System relative to the size of its planet.",
      "diameter": "3,475 km",
      "gravity": "0.1654 g",
      "orbit": "Orbits Earth every 708 hours",
      "rotation": "Tidally locked with Earth",
      "day": "708 hours"
    },
    "departure": "2021-03-30T00:00:00.000+00:00"
  }
}
```

---

>`POST: /passenger` 

Books passage based on the JSON request body

JSON Example of a POST Request Body:
```json
{
  "voyageId": 1,
  "firstName": "Nick",
  "lastName": "Nicolosi",
  "dateOfBirth": "1969-02-05"
}
```

Validation Rules:
- The passenger date of birth must be in the past
- The departure date must be in the future
- The spacecraft must have capacity remaining

---

>`DELETE: /passenger/{id}` 

Cancels passage for the passenger specified by `id`

Validation Rules:
- The departure date must be in the future

---
<br>

### Availability ###

---

>`GET: /availability/spacecraft?destination={destinationId}`

Returns a JSON array of available spacecraft, optionally for the given destination

Availability Rules:
- The spacecraft must be available (not reserved for another voyage)
- If a destination is specified, the spacecraft must be certified for the destination

---

>`GET: /availability/destinations?spacecraft={spacecraftId}` 

Returns a JSON array of available destinations, optionally for the given spacecraft

Availability Rules:
- An available spacecraft must be certified for the destination
- If a spacecraft is specified, that spacecraft must be available and certified for the destination

---

>`GET: /availability/launchpads?date={dateString}` 

Returns a JSON array of available launchpads on the given date (date is required)

Availability Rules:
- The launchpad must not be reserved for another voyage's departure date

---

>`GET: /availability/voyages?destination={destinationId}` 

Returns a JSON array of available voyages, optionally for the give destination

Availability Rules:
- The voyage must have a departure date in the future
- The spacecraft reserved for the voyage must have capacity remaining
- If a destination is specified, the voyage must have the same destination

---
<br>

## Running the Unit Tests ##

IntelliJ has many affordances for running unit tests.  To run the unit tests from the command line, however, enter the following command:

`./gradlew clean test`

<br>

## Running the API ##

### Running with Gradle ###
When running from within IntelliJ, simply run ReservationSystemApplication as you would any Spring Boot application. 
When running from the command line, navigate to the project root directory and enter the following command:

`./gradlew bootRun`

With either method, the API should start right up and begin listening on `http://localhost:8080`

### Running with Docker ###
If you want to run the application in a Docker container (and asssuming you have a Docker host to run the container), start by creating an executable jar.  Navigate to the project root directory and enter the following command:

`./gradlew bootJar`

This should create an executable jar in the `/build/libs` directory.  Next you can build a Docker image by entering the following command:

`docker build . -t interplanetary`

After the image has been created, you can run the container (exposing the application on port 8080) by entering the following command:

`docker run -p 8080:8080 interplanetary`

The container should now be running and accessible at `http://localhost:8080`

### Testing the Endpoints ###

Testing the various endpoints of the API can be done with the Postman transaction collection (format v2.1) included in this repository.  Import the collection into your Postman client and use the transactions to perform any of the available API operations.

