package pl.reconizer.cityadventure.domain.entities

enum class ClueType {
    TEXT,
    IMAGE,
    AUDIO,
    VIDEO;

    companion object {
        fun fromName(name: String): ClueType {
            val clueType = values().find { it.name.toLowerCase() == name }
            if (clueType == null) {
                throw IllegalArgumentException("Unknown clue type")
            } else {
                return clueType
            }

        }
    }

}