package pl.reconizer.unfold.domain.entities

import java.lang.IllegalArgumentException

enum class DifficultyLevel(val value: Int) {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    companion object {
        fun fromInt(value: Int): DifficultyLevel {
            return when(value) {
                1 -> EASY
                2 -> MEDIUM
                3 -> HARD
                else -> throw IllegalArgumentException("Values can range between 1 and 3")
            }
        }
    }
}