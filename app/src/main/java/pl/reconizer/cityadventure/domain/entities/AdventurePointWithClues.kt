package pl.reconizer.cityadventure.domain.entities

import java.text.SimpleDateFormat
import java.util.*

data class AdventurePointWithClues(
        val id: String,
        val discoveryDate: Date,
        val clues: List<Clue>
) {

    val discoveryDateString: String by lazy {
        val simpleDate =  SimpleDateFormat("dd.MM.yyyy HH:mm")
        simpleDate.format(discoveryDate)
    }

}