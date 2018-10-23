package pl.reconizer.cityadventure.domain.entities

import com.google.gson.annotations.SerializedName

data class Adventure(
        @SerializedName("adenture_id") val adventureId: String,
        val completed: Boolean,
        val started: Boolean,
        @SerializedName("paid") val purchasable: Boolean,
        @SerializedName("start_point_id") val startPointId: String,
        val position: Position
)