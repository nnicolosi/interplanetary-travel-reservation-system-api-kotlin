# Interplanetary Travel Reservation System API


## Description ##
This repository contains a web service API written in Kotlin and leveraging the Spring Boot framework.  It uses an H2 database, which can be configured for either the in-memory (default) or the file-based mode of operation. 

The project represents a contrived business case, that of an interplanetary travel provider requiring a system for scheduling voyages and booking passage for individual customers.


## Domain Model ##
There are only a handful of entities in the domain model, each one described below.

### Destination ###
Destinations represent the planetary bodies (planets and moons) that are available for voyages.  Implemented as an enumeration to simplify the implementation, which can be justified due to the fact that they are not expected to change frequently.

#### Available Destinations: ####
- Callisto
- Europa
- Ganymede
- Io
- Luna
- Mars
- Titan

### Spacecraft ###
Spacecraft represent the vehicles in the company's fleet that are available for voyages.  Different spacecraft are certified for different destinations and may have different passenger capacity.  Implemented as an enumeration to simplify the implementation, which can be justified due to the fact that they are not expected to change frequently.

#### Available Spacecraft: ####
- UNS-001 (capacity of 100, certified for Luna only)
- UNS-002 (capacity of 100, certified for Luna only)
- UNS-003 (capacity of 100, certified for Luna only)
- UNS-004 (capacity of 60, certified for Luna and Mars only)
- UNS-005 (capacity of 60, certified for Luna and Mars only)
- UNS-006 (capacity of 60, certified for Luna and Mars only)
- UNS-007 (capacity of 60, certified for Luna and Mars only)
- UNS-008 (capacity of 20, certified for all destinations)
- UNS-009 (capacity of 20, certified for all destinations)
- UNS-010 (capacity of 20, certified for all destinations)

### Launchpad ###
Launchpads are the origin point for any voyage. Implemented as an enumeration to simplify the implementation, which can be justified due to the fact that they are not expected to change frequently.

#### Available Launchpads ####
- Launch Pad #1
- Launch Pad #2
- Launch Pad #3

### Voyage ###
Voyages are the central domain entity for the system.  They are composed of a spacecraft, a destination, a launchpad, a departure date, and a manifest (of passengers).  The system doesn't currently account for return trips, so a voyage effectively "consumes" spacecraft. 

### Passenger ###
Passengers are added to the manifest of a voyage.  In a more sophisticated system, there would be "customers" that exist independent of voyages, and are associated with one or more voyages via a relation entity such as "passage" or "reservation".  Perhaps in a future iteration of the system this will be done, but for now this was a convenient way to limit scope.

## API Endpoints ##
[TODO]

### Destination ###
[TODO]

### Spacecraft ###
[TODO]

### Launchpad ###
[TODO]

### Voyage ###
[TODO]

### Passenger ###
[TODO]

### Availability ###
[TODO]

