package pl.reconizer.cityadventure.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdventurePoint(
    val id: String,
    override val position: Position,
    @SerializedName("completed") val isCompleted: Boolean
): Parcelable, IPositionable