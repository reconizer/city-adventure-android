package pl.reconizer.unfold.domain.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RankingEntry(
        @SerializedName("user_id") val userId: String,
        val position: Int,
        val nick: String,
        @SerializedName("completion_time") val completionTime: Long,
        @SerializedName("avatar_url") val avatarUrl: String?
): Parcelable