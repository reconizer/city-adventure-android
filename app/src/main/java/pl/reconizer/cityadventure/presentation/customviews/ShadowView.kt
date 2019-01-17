package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import pl.reconizer.cityadventure.R

class ShadowView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var shadowGenerator: ShadowGenerator? = null//ShadowGenerator(context, Schedulers.io(), AndroidSchedulers.mainThread())

    private var shadowBitmap: Bitmap? = null
    private var isShadowPrepared = false

    private var radius: Int = 0

    private var shadowColor: Int = 0
    private var shadowOffsetX: Int = 0
    private var shadowOffsetY: Int = 0

    private var desiredWidth: Int = 0
    private var desiredHeight: Int = 0

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ShadowView)
            radius = typedArray.getDimensionPixelOffset(R.styleable.ShadowView_shadowRadius, 0)
            shadowColor = typedArray.getColor(R.styleable.ShadowView_shadowColor, ContextCompat.getColor(context, R.color.basicShadow))
            shadowOffsetX = typedArray.getDimensionPixelOffset(R.styleable.ShadowView_shadowOffsetX, 0)
            shadowOffsetY = typedArray.getDimensionPixelOffset(R.styleable.ShadowView_shadowOffsetY, 0)
            typedArray.recycle()
        }
        translationX = x + shadowOffsetX
        translationY = y + shadowOffsetY
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        desiredWidth = MeasureSpec.getSize(widthMeasureSpec) + 2 * radius
        desiredHeight = MeasureSpec.getSize(heightMeasureSpec) + 2 * radius
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        scaleX = 1f * desiredWidth / (right - left)
        scaleY = 1f * desiredHeight / (bottom - top)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (!isInEditMode) {
            canvas?.let {
                val newRect = it.clipBounds
                if (!isShadowPrepared && newRect.width() > 0 && newRect.height() > 0) {
                    isShadowPrepared = true
                    shadowGenerator?.generateShadowAsync(
                            newRect.width(),
                            newRect.height(),
                            shadowColor,
                            radius
                    ) {
                        shadowBitmap = it
                        postInvalidate()
                    }
                }
                if (shadowBitmap != null) {
                    it.drawBitmap(shadowBitmap!!, newRect.left.toFloat(), newRect.top.toFloat(), null)
                }
            }
        }
    }

}