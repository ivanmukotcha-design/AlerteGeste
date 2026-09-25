package com.butembo.alertgeste.domain

/** Prepare parts before any transmission, without requiring access to SIM identifiers. */
object SmsText {
    fun prepare(message: String, platformDivider: (String) -> List<String>): ArrayList<String> {
        require(message.isNotEmpty()) { "Le message SMS est vide." }
        return try {
            ArrayList(platformDivider(message))
        } catch (_: SecurityException) {
            // Some Android/carrier configurations read GID1 while dividing Unicode SMS.
            // Only retry this local preparation step, never retry an SMS transmission.
            conservativeSplit(message)
        }
    }

    private fun asciiSeptets(char: Char): Int = when {
        char in "\u000c^{}\\[~]|" -> 2
        char == '\n' || char == '\r' || (char in ' '..'~' && char != '\u0060') -> 1
        else -> 0
    }

    private fun conservativeSplit(message: String): ArrayList<String> {
        // ASCII is safe for both GSM and legacy CDMA. Treat all other text as
        // Unicode conservatively, even when a national/GSM alphabet could save space.
        val ascii = message.all { asciiSeptets(it) > 0 }
        val size = if (ascii) message.sumOf { asciiSeptets(it) } else message.length
        if (size <= (if (ascii) 160 else 70)) return arrayListOf(message)

        // 153 septets with a concatenation header. Unicode uses 66 UTF-16 units
        // (rather than 67) to leave room for legacy non-EMS carrier page numbering.
        val limit = if (ascii) 153 else 66
        val parts = arrayListOf<String>()
        var start = 0
        while (start < message.length) {
            var end = start
            var used = 0
            while (end < message.length) {
                val width = if (ascii) 1 else Character.charCount(Character.codePointAt(message, end))
                val cost = if (ascii) asciiSeptets(message[end]) else width
                if (used + cost > limit) break
                used += cost
                end += width
            }
            parts.add(message.substring(start, end))
            start = end
        }
        return parts
    }
}
