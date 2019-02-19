package pl.reconizer.unfold.domain.entities

enum class PuzzleType {
    TEXT,
    NUMBER_LOCK_3,
    NUMBER_LOCK_4,
    NUMBER_LOCK_5,
    NUMBER_LOCK_6,
    CRYPTEX_LOCK_4,
    CRYPTEX_LOCK_5,
    CRYPTEX_LOCK_6,
    CRYPTEX_LOCK_7,
    DIRECTION_LOCK_4,
    DIRECTION_LOCK_6,
    DIRECTION_LOCK_8,
    NUMBER_PUSH_LOCK_3,
    NUMBER_PUSH_LOCK_4,
    NUMBER_PUSH_LOCK_5;

    companion object {

        fun fromName(name: String): PuzzleType {
            return values().find { it.name.toLowerCase() == name } ?: TEXT
        }

    }
}