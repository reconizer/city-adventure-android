package pl.reconizer.unfold.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Avatar(
        val id: String,
        val url: String
) : Parcelable