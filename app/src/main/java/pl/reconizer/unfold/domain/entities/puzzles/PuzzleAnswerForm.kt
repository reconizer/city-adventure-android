package pl.reconizer.unfold.domain.entities.puzzles

import com.google.gson.annotations.SerializedName
import pl.reconizer.unfold.domain.entities.Position

data class PuzzleAnswerForm(
        val position: Position,
        @SerializedName("adventure_id") val adventureId: String,
        @SerializedName("answer_text") val answer: String? = null,
        @SerializedName("answer_type") val answerTypeName: String? = null
)