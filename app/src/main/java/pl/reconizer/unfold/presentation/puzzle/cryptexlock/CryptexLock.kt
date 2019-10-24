package pl.reconizer.unfold.presentation.puzzle.cryptexlock

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import kankan.wheel.widget.WheelView
import kankan.wheel.widget.adapters.ArrayWheelAdapter
import kotlinx.android.synthetic.main.view_cryptex_lock.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.performOneShotVibration

class CryptexLock @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var numberOfCylinders: Int = 0
        set(value) {
            field = value
            initView()
        }

    val valuesStack: List<String>
        get() = items.map { ALPHABET[it.currentItem].toLowerCase() }

    private val items: MutableList<WheelView> = mutableListOf()

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_cryptex_lock, this, true)
    }

    private fun initView() {
        cylindersContainer.removeAllViews()
        items.clear()
        for (i in 0 until numberOfCylinders) {
            val wheel = createItem(i, numberOfCylinders)
            items.add(wheel)
            cylindersContainer.addView(wheel)
        }
    }

    private fun createItem(itemIndex: Int, count: Int): WheelView {
        return WheelView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            ).apply {
                if (count > 1 && itemIndex + 1 != count) {
                    setMargins(
                            0, 0, resources.getDimensionPixelOffset(R.dimen.space_1x), 0
                    )
                }
            }
            viewAdapter = ArrayWheelAdapter<String>(context, ALPHABET).apply {
                itemResource = R.layout.view_cryptex_lock_item
                itemTextResource = R.id.textItem
            }
            isCyclic = true
        }.apply {
            addChangingListener { _, _, _ -> onWheelChangedListener() }
        }
    }

    private fun onWheelChangedListener() {
        context?.performOneShotVibration(20)
    }

    companion object {
        private val ALPHABET = arrayOf(
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        )
    }

}