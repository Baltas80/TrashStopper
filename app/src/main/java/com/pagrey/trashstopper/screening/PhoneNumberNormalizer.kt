package com.pagrey.trashstopper.screening

object PhoneNumberNormalizer {
    fun normalize(number: String?): String {
        val raw = number.orEmpty().trim()
        if (raw.isBlank()) return ""
        return buildString {
            raw.forEachIndexed { index, char ->
                when {
                    char.isDigit() -> append(char)
                    char == '+' && index == 0 -> append(char)
                }
            }
        }
    }
}
