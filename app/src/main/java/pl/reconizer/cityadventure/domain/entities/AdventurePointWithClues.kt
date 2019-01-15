package pl.reconizer.cityadventure.domain.entities

import java.util.*

data class AdventurePointWithClues(
        val id: String,
        val discoveryDate: Date,
        val clues: List<Clue>
)