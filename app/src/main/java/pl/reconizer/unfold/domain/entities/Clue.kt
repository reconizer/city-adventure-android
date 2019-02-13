package pl.reconizer.unfold.domain.entities

data class Clue(
    val id: String,
    val originalResourceUrl: String?,
    val type: ClueType,
    val description: String?,
    val videoResources: List<VideoResource>?
)