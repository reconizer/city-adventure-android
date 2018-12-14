package pl.reconizer.cityadventure.domain.entities

import com.google.gson.annotations.SerializedName

data class AdventurePoint(
    val id: String,
    val position: Position,
    @SerializedName("completed") val isCompleted: Boolean
)