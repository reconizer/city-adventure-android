package pl.reconizer.unfold.presentation.search.adventures

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.domain.entities.DifficultyLevel

@Parcelize
data class AdventureFilters(
        var isRangeActive: Boolean = false,
        var rangeValue: Float = 0f,
        var isDifficultyLevelActive: Boolean = false,
        var difficultyLevel: DifficultyLevel? = null
) : Parcelable