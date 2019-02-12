package pl.reconizer.cityadventure.domain.entities

import com.google.gson.annotations.SerializedName

data class PuzzleResponse(
        @SerializedName("completed") val isCompleted: Boolean,
        @SerializedName("last_point") val isLastPoint: Boolean,
        @SerializedName("answer_type") val puzzleTypeName: String?
) {

    val puzzleType: PuzzleType?
        get() {
            return if (puzzleTypeName == null) {
                null
            } else {
                PuzzleType.fromName(puzzleTypeName)
            }
        }

}