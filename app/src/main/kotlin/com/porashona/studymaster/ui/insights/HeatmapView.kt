package com.porashona.studymaster.ui.insights

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Compact contribution-style productivity heatmap.
 *
 * Renders the last [weeks] weeks as a grid of 7 rows × [weeks] columns. Each
 * cell is coloured by intensity: hand in [0, 1] where 0 = no study and 1 = the
 * user's daily peak.
 */
class HeatmapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val cellPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()
    private var weeks: Int = 13
    private var intensities: FloatArray = FloatArray(weeks * 7)
    private val gap = context.resources.displayMetrics.density * 3f
    private val corner = context.resources.displayMetrics.density * 3f

    private val baseColor by lazy {
        resolveColor(com.google.android.material.R.attr.colorPrimary, Color.parseColor("#6750A4"))
    }
    private val emptyColor by lazy {
        resolveColor(com.google.android.material.R.attr.colorSurfaceVariant, Color.parseColor("#E7E0EC"))
    }

    fun submit(values: FloatArray, weeks: Int = 13) {
        this.weeks = weeks
        this.intensities = values.copyOf(weeks * 7)
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val cellW = (w - gap * (weeks - 1)) / weeks
        val h = (cellW * 7 + gap * 6).toInt() + paddingTop + paddingBottom
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        val cellSize = (width - gap * (weeks - 1)) / weeks
        for (col in 0 until weeks) {
            for (row in 0 until 7) {
                val index = col * 7 + row
                val intensity = intensities.getOrNull(index) ?: 0f
                cellPaint.color = lerpColor(emptyColor, baseColor, intensity.coerceIn(0f, 1f))
                val left = col * (cellSize + gap)
                val top = row * (cellSize + gap) + paddingTop
                rect.set(left, top, left + cellSize, top + cellSize)
                canvas.drawRoundRect(rect, corner, corner, cellPaint)
            }
        }
    }

    private fun lerpColor(start: Int, end: Int, t: Float): Int {
        val sa = Color.alpha(start); val sr = Color.red(start); val sg = Color.green(start); val sb = Color.blue(start)
        val ea = Color.alpha(end); val er = Color.red(end); val eg = Color.green(end); val eb = Color.blue(end)
        return Color.argb(
            (sa + (ea - sa) * t).toInt(),
            (sr + (er - sr) * t).toInt(),
            (sg + (eg - sg) * t).toInt(),
            (sb + (eb - sb) * t).toInt(),
        )
    }

    private fun resolveColor(attr: Int, fallback: Int): Int {
        val tv = android.util.TypedValue()
        return if (context.theme.resolveAttribute(attr, tv, true)) tv.data else fallback
    }
}
