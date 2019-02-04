package pl.reconizer.cityadventure.presentation.authentication

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.res.getStringOrThrow
import kotlinx.android.synthetic.main.view_authentication_input.view.*
import pl.reconizer.cityadventure.R

class AuthenticationInput @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var text: String
        get() { return input.text.toString() }
        set(value) { input.setText(value) }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_authentication_input, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AuthenticationInput)
            text = typedArray.getString(R.styleable.AuthenticationInput_android_text) ?: ""
            labelTextView.text = typedArray.getStringOrThrow(R.styleable.AuthenticationInput_label)
            input.inputType = typedArray.getInt(R.styleable.AuthenticationInput_android_inputType, InputType.TYPE_CLASS_TEXT)
            typedArray.recycle()
        }
    }

}