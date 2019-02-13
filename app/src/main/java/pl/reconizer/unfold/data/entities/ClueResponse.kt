package pl.reconizer.unfold.data.entities

import com.google.gson.annotations.SerializedName

data class ClueResponse(
    val id: String,
    @SerializedName("original_asset_url") val originalResourceUrl: String?,
    val type: String,
    val description: String?,
    @SerializedName("conversion_urls") val videoResources: List<VideoResourceResponse>?
)