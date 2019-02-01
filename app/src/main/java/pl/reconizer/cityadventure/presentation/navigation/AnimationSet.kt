package pl.reconizer.cityadventure.presentation.navigation

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes

data class AnimationSet(
        @AnimatorRes @AnimRes val enter: Int,
        @AnimatorRes @AnimRes val exit: Int,
        @AnimatorRes @AnimRes val popEnter: Int,
        @AnimatorRes @AnimRes val popExit: Int
)