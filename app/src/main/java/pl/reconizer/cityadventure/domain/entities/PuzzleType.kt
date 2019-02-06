package pl.reconizer.cityadventure.domain.entities

enum class PuzzleType {
    PASSWORD,
    LETTER_LOCK_4;

    companion object {

        fun fromName(name: String): PuzzleType {
            return values().find { it.name.toLowerCase() == name } ?: PASSWORD
        }

    }
}