package com.example.myrecipesdoffemontdjegherif.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class NutriScoreView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var score: Int = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokeWidth = 4f
    private val textSizeSp = 16f

    fun setScore(value: Int) {
        score = value.coerceIn(0, 100)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val percent = score / 100f

        val left = paddingLeft.toFloat()
        val top = paddingTop.toFloat()
        val right = width - paddingRight.toFloat()
        val bottom = height - paddingBottom.toFloat()

        // Couleur selon le score
        val fillColor = when (score) {
            in 0..30 -> Color.RED
            in 31..69 -> Color.YELLOW
            else -> Color.GREEN
        }

        // Remplissage
        paint.style = Paint.Style.FILL
        paint.color = fillColor
        canvas.drawRect(left, top, left + (right - left) * percent, bottom, paint)

        // Contour
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = strokeWidth
        canvas.drawRect(left, top, right, bottom, paint)

        // % affiché à droite
        paint.style = Paint.Style.FILL
        paint.color = Color.DKGRAY
        paint.textSize = textSizeSp * resources.displayMetrics.scaledDensity

        paint.textAlign = Paint.Align.RIGHT

        // Mesurer la hauteur du texte
        val textHeight = paint.fontMetrics.descent - paint.fontMetrics.ascent
        val textOffset = (height + textHeight) / 2f - paint.fontMetrics.descent

        canvas.drawText("$score%", right - 8f, textOffset, paint)

    }
}
