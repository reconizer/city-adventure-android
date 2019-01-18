package pl.reconizer.cityadventure.domain.entities

import com.google.gson.annotations.SerializedName

data class PuzzleResponse(
        @SerializedName("completed") val isCompleted: Boolean,
        @SerializedName("last_point") val isLastPoint: Boolean
)