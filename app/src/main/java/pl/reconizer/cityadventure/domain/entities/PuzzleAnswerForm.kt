package pl.reconizer.cityadventure.domain.entities

import com.google.gson.annotations.SerializedName

data class PuzzleAnswerForm(
        @SerializedName("adventure_id") val adventureId: String,
        @SerializedName("point_id") val pointId: String,
        val position: Position,
        @SerializedName("answer_text") val answer: String?,
        @SerializedName("answer_type") val answerTypeName: String?
)