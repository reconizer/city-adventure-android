package pl.reconizer.cityadventure.presentation.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseKey(override val arguments: Bundle) : Key {



    override fun newFragment(): Fragment = createFragment().also { fragment ->
        fragment.arguments = arguments
    }

    protected abstract fun createFragment(): Fragment

    override val fragmentTag: String
        get() = toString()
}