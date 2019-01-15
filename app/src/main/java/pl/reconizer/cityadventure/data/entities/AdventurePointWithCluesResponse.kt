package pl.reconizer.cityadventure.data.entities

import com.google.gson.annotations.SerializedName

data class AdventurePointWithCluesResponse(
        @SerializedName("point_id") val id: String,
        @SerializedName("discovery_timestamp") val discoveryTimestamp: Long,
        val clues: List<ClueResponse>
)