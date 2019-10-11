package pl.reconizer.unfold.common.extensions

import androidx.fragment.app.FragmentManager

fun FragmentManager.isFragmentOnStack(tag: String): Boolean {
    return findFragmentByTag(tag) != null
}