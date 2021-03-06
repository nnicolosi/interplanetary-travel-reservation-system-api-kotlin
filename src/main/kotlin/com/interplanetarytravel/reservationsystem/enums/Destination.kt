package com.interplanetarytravel.reservationsystem.enums

enum class Destination(
        val description: String,
        val diameter: String,
        val gravity: String,
        val orbit: String,
        val rotation: String,
        val day: String
) {
    CALLISTO(
            "Callisto is the second-largest moon of Jupiter, after Ganymede. It is composed of approximately equal amounts of rock and ices, with the lowest density and surface gravity of Jupiter's major moons.",
            "4,821 km",
            "0.126 g",
            "Orbits Jupiter once every 401 hours",
            "Callisto is tidally locked with Jupiter",
            "401 hours"
    ),
    EUROPA(
            "Slightly smaller than Earth's Moon, Europa is primarily made of silicate rock and has a water-ice crust and probably an iron–nickel core. It has a very thin atmosphere, composed primarily of oxygen. Its surface is striated by cracks and streaks, but craters are relatively few.",
            "3,121 km",
            "0.134 g",
            "Orbits Jupiter once every 85 hours",
            "Europa is tidally locked with Jupiter",
            "85 hours"
    ),
    GANYMEDE(
            "Ganymede is the largest moon of Jupiter and the most massive moon in the Solar System. It is composed of approximately equal amounts of silicate rock and water. It is a fully differentiated body with an iron-rich, liquid core, and an internal ocean that may contain more water than all of Earth's oceans combined. Its surface is composed of two main types of terrain. Dark regions, saturated with impact craters and dated to four billion years ago, cover about a third of it. Lighter regions, crosscut by extensive grooves and ridges and only slightly less ancient, cover the remainder.",
            "5,268 km",
            "0.146 g",
            "Orbits Jupiter once every 171 hours",
            "Ganymede is tidally locked with Jupiter",
            "171 hours"
    ),
    IO(
            "The innermost and third-largest of the moons orbiting Jupiter. With over 400 active volcanoes, Io is the most geologically active object in the Solar System.",
            "3,643 km",
            "0.183 g",
            "Orbits Jupiter once every 42.5 hours",
            "Io is tidally locked with Jupiter",
            "42.5 hours"
    ),
    LUNA(
            "Luna is the only moon of Earth, and the fifth largest moon in the Solar System. It is one-quarter the diameter of Earth, making it the largest natural satellite in the Solar System relative to the size of its planet.",
            "3,475 km",
            "0.1654 g",
            "Orbits Earth every 708 hours",
            "Luna is tidally locked with Earth",
            "708 hours"
    ),
    MARS(
            "Mars is the fourth planet from the Sun, and the second-smallest planet in the Solar System. It is a terrestrial planet with a thin atmosphere, with surface features reminiscent of the impact craters of the Moon and the valleys, deserts and polar ice caps of Earth.",
            "6,792 km",
            "0.3794 g",
            "Orbits the Sun every 687 (Earth) days",
            "24.6 hours",
            "24.6 hours"
    ),
    TITAN(
            "Titan is the largest moon of Saturn, and the second-largest moon in the Solar System. It is primarily composed of ice and rocky material, which is likely differentiated into a rocky core surrounded by various layers of ice, including a crust of ice and a subsurface layer of ammonia-rich liquid water.",
            "5,149 km",
            "0.138 g",
            "Orbits Saturn once every 382 hours",
            "Titan is tidally locked with Saturn",
            "382 hours"
    )
}

