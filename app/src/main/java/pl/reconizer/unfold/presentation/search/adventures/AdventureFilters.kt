package pl.reconizer.unfold.presentation.search.adventures

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.domain.entities.DifficultyLevel

/**
 * Represents filtering options for adventures
 * @property range Value between [0..1] which represents point between minRange and maxRange
 */
@Parcelize
data class AdventureFilters(
        val minRange: Float,
        val maxRange: Float,
        val isRangeActive: Boolean = false,
        val range: Float = 0f,
        val isDifficultyLevelActive: Boolean = false,
        val difficultyLevel: DifficultyLevel? = null,
        val name: String = ""
) : Parcelable {

    val calculatedRangeValue: Float
        get() = (maxRange - minRange) * range + minRange

    val activeFiltersCount: Int
        get() {
            var counter = 0
            if (isRangeActive) counter++
            if (isDifficultyLevelActive) counter++
            return counter
        }

}