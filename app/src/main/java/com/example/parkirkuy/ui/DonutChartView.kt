package com.example.parkirkuy.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class DonutChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN // Default color for text
        textSize = 50f
        textAlign = Paint.Align.CENTER
    }
    private val rect = RectF()
    private var progressAngle = 0f // Angle for arc
    private var percentage = 0 // Percentage to display

    fun setChartData(current: Int, max: Int) {
        if (max > 0) {
            percentage = (current / max.toFloat() * 100).toInt()
            progressAngle = (percentage / 100f) * 360f
        } else {
            percentage = 0
            progressAngle = 0f
        }
        invalidate() // Trigger redraw
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Define dimensions
        val strokeWidth = 40f
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width / 2f) - strokeWidth

        paint.strokeWidth = strokeWidth

        // Draw background circle
        paint.color = Color.LTGRAY
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Draw progress arc
        paint.color = Color.GREEN // Set arc color to green
        rect.set(
            strokeWidth, strokeWidth,
            width - strokeWidth, height - strokeWidth
        )
        canvas.drawArc(rect, -90f, progressAngle, false, paint)

        // Draw percentage text
        canvas.drawText("$percentage%", centerX, centerY + textPaint.textSize / 4, textPaint)
    }
}
