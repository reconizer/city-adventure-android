package pl.reconizer.cityadventure.domain.entities

import com.google.gson.annotations.SerializedName

data class PuzzleAnswerForm(
        val position: Position,
        @SerializedName("adventure_id") val adventureId: String,
        @SerializedName("answer_text") val answer: String? = null,
        @SerializedName("answer_type") val answerTypeName: String? = null
)