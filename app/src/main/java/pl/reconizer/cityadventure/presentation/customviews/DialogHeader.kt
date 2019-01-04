package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.dialog_header.view.*
import pl.reconizer.cityadventure.R

class DialogHeader @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var text: String?
        get() = headerTextView.text.toString()
        set(value) {
            headerTextView.text = value
        }

    var closeButtonClickListener: (() -> Unit)? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.dialog_header, this, true)
        closeButton.setOnClickListener { closeButtonClickListener?.invoke() }
    }
}