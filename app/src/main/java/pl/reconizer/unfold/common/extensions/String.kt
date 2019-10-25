package pl.reconizer.unfold.common.extensions

import android.content.Context
import pl.reconizer.unfold.domain.entities.DifficultyLevel

fun prettyTimeStringRange(minLength: Long?, maxLength: Long?): String {
    return if (minLength == null) {
        "? ? ?"
    } else {
        if (maxLength == null) {
            minLength.toPrettyTimeStringFromSeconds()
        } else {
            "${minLength.toPrettyTimeStringFromSeconds()} - ${maxLength.toPrettyTimeStringFromSeconds()}"
        }
    }
}

fun stringifyDifficultyLevel(context: Context, difficultyLevel: DifficultyLevel?): String {
    return if (difficultyLevel == null) {
        "? ? ?"
    } else {
        context.resources.getStringByNameBang(
                context,
                "difficulty_level_${difficultyLevel.name.toLowerCase()}"
        )
    }
}