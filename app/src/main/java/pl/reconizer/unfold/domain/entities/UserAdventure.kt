package pl.reconizer.unfold.domain.entities

data class UserAdventure(
        val id: String,
        val name: String,
        val rating: Double?,
        val completionTime: Long?,
        val position: Int?,
        val coverImage: String
)