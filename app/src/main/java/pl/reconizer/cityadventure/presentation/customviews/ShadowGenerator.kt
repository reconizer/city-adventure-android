package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class ShadowGenerator @Inject constructor(
        private val context: Context,
        @Named("background") private val backgroundScheduler: Scheduler,
        @Named("main") private val mainScheduler: Scheduler
) {

    private var generatorSubscriber: Disposable? = null
    private val rs by lazy { RenderScript.create(context) }
    private val blurScript by lazy { ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)) }

    fun generateShadowAsync(width: Int, height: Int, shadowColor: Int, radius: Int, callback: ((generatedShadow: Bitmap) -> Unit)? = null) {
        generatorSubscriber = Single.timer(300L, TimeUnit.MILLISECONDS, backgroundScheduler).flatMap {
            Log.d("ShadowGenerator", "GENERATE SHADOW: ${Thread.currentThread().name}")
            Single.just(generateShadow(width, height, shadowColor, radius))
        }
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe { shadow -> callback?.invoke(shadow) }
    }

    fun generateShadow(width: Int, height: Int, shadowColor: Int, radius: Int): Bitmap {
        val shadowBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val shadowPaint = Paint().apply {
            color = shadowColor
            style = Paint.Style.FILL
        }
        val shadowRect = Rect(radius, radius, shadowBitmap.width - radius, shadowBitmap.height - radius)
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
        return shadowBitmap
    }

    fun dispose() {
        generatorSubscriber?.dispose()
    }
}