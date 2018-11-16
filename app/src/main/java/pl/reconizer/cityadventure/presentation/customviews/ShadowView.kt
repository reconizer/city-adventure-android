package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.graphics.*
import android.renderscript.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import pl.reconizer.cityadventure.R

class ShadowView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var rs: RenderScript
    private lateinit var blurScript: ScriptIntrinsicBlur
    private lateinit var colorMatrixScript: ScriptIntrinsicColorMatrix
    private var shadowBitmap: Bitmap? = null

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
                if (shadowBitmap == null) {
                    shadowBitmap = Bitmap.createBitmap(newRect.width(), newRect.height(), Bitmap.Config.ARGB_8888)
                    generateShadow()
                }
                it.drawBitmap(shadowBitmap, newRect.left.toFloat(), newRect.top.toFloat(), null)
            }
        }
    }

    override fun onAttachedToWindow() {
        if (!isInEditMode) {
            rs = RenderScript.create(context)
            val element = Element.U8_4(rs)
            blurScript = ScriptIntrinsicBlur.create(rs, element)
            colorMatrixScript = ScriptIntrinsicColorMatrix.create(rs)
        }
        super.onAttachedToWindow()
    }

    private fun generateShadow() {
        val shadowPaint = Paint().apply {
            color = shadowColor
            style = Paint.Style.FILL
        }
        val shadowRect = Rect(radius, radius, shadowBitmap!!.width - radius, shadowBitmap!!.height - radius)
        val mainCanvas = Canvas(shadowBitmap)

        mainCanvas.drawRect(shadowRect, shadowPaint)
        val allocationIn = Allocation.createFromBitmap(rs, shadowBitmap)
        val allocationOut = Allocation.createTyped(rs, allocationIn.type)

        blurScript.setRadius(radius.toFloat())

        blurScript.setInput(allocationIn)
        blurScript.forEach(allocationOut)

        allocationOut.copyTo(shadowBitmap)

        allocationIn.destroy()
        allocationOut.destroy()
    }

}