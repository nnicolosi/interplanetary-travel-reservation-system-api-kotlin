package com.interplanetarytravel.reservationsystem.utils

fun String.toSentenceCase(): String {
    return if (this.isNotEmpty()) {
        this[0].toUpperCase() + this.substring(1)
    } else {
        this
    }
}

fun String.toTitleCase(): String {
    return if (this.isNotEmpty()) {
        this.toLowerCase()
            .splitToSequence(" ")
            .map { it[0].toUpperCase() + it.substring(1) }
            .joinToString(" ")
    } else {
        this
    }
}
