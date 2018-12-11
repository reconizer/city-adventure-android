package pl.reconizer.cityadventure.domain.entities

data class Clue(
    val id: String,
    val originalResourceUrl: String?,
    val type: ClueType,
    val description: String,
    val pointId: String
)