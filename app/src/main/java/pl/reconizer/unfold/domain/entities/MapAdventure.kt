package pl.reconizer.unfold.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MapAdventure(
        @SerializedName("adventure_id") val adventureId: String,
        val completed: Boolean,
        val started: Boolean,
        @SerializedName("paid") val purchasable: Boolean,
        val purchased: Boolean,
        @SerializedName("start_point_id") val startPointId: String,
        override val position: Position
): Parcelable, IPositionable