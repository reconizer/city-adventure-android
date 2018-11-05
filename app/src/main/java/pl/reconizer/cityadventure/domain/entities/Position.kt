package pl.reconizer.cityadventure.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Position(
        val lat: Double,
        val lng: Double
): Parcelable