package pl.reconizer.cityadventure.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdventurePoint(
        val id: String,
        override val position: Position,
        @SerializedName("completed") val isCompleted: Boolean,
        @SerializedName("answer_type") val puzzleTypeName: String?,
        @SerializedName("radius") val accessibilityRadius: Int
): Parcelable, IPositionable {

    val puzzleType: PuzzleType? by lazy {
        if (puzzleTypeName == null) {
            null
        } else {
            PuzzleType.fromName(puzzleTypeName)
        }
    }
}