package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import pl.reconizer.cityadventure.R

class DialogContentContainer @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Container(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.dialog_content_container, this, true)
    }

}