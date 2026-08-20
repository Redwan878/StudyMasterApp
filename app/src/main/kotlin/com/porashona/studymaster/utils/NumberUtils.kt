package com.porashona.studymaster.utils

/** Simple utility object for number formatting helpers. */
object NumberUtils {
    // Convert a non‑negative Int to its Bangla digit representation.
    fun intToBengaliDigits(i: Int): String = i.toString().map {
        if (it.isDigit()) '০'.plus(i - '০'.code) else it
    }.joinToString("")

    // Convert a non‑negative Long to its Bangla digit representation.
    fun longToBengaliDigits(l: Long): String = l.toString().map {
        if (it.isDigit()) '০'.plus(it - '০'.code) else it
    }.joinToString("")

    // Convert a non‑negative Double/Float to its Bangla digit representation
    // with the specified decimal places.
    fun doubleToBengaliDigits(d: Double, decimalPlaces: Int = 1): String {
        val formatted = String.format("%.*f", decimalPlaces, d)
        return formatted.map {
            if (it.isDigit()) '০'.plus(it - '০'.code) else it
        }.joinToString("")
    }
}