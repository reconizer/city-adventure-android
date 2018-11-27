package pl.reconizer.cityadventure.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Adventure(
        @SerializedName("adenture_id") val adventureId: String,
        val completed: Boolean,
        val started: Boolean,
        @SerializedName("paid") val purchasable: Boolean,
        val purchased: Boolean,
        @SerializedName("start_point_id") val startPointId: String,
        val position: Position
): Parcelable