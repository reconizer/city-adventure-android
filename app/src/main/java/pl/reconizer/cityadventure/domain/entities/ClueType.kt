package pl.reconizer.cityadventure.domain.entities

enum class ClueType (val value: Int) {
    TEXT(0),
    IMAGE(1),
    AUDIO(2),
    VIDEO(3);

    companion object {
        fun fromName(name: String): ClueType {
            val clueType = values().find { it.name.toLowerCase() == name }
            if (clueType == null) {
                throw IllegalArgumentException("Unknown clue type")
            } else {
                return clueType
            }

        }

        fun fromInt(value: Int): ClueType {
            return when(value) {
                0 -> TEXT
                1 -> IMAGE
                2 -> AUDIO
                3 -> VIDEO
                else -> throw IllegalArgumentException("Unknown clue type")
            }
        }
    }

}