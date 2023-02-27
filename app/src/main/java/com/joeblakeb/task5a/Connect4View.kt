package com.joeblakeb.task5a

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Connect4View : View {
    private val colCount get() = 7
    private val rowCount get() = 6

    private var gridLeft: Float = 0f
    private var gridTop: Float = 0f
    private var gridRight: Float = 0f
    private var gridBottom: Float = 0f

    private var circleDiameter: Float = 0f
    private var circleSpacing: Float = 0f
    private var circleSpacingRatio: Float = 0.2f

    private val gridPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0xFF00304A.toInt()
    }

    private val noPlayerPaint: Paint= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        recalculateDimensions(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(gridLeft, gridTop, gridRight, gridBottom, gridPaint)

        val radius = circleDiameter / 2f

        for (row in 0 until rowCount) {
            val cy = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * row) + radius

            for (col in 0 until colCount) {
                val paint = noPlayerPaint
                val cx = gridLeft + circleSpacing + ((circleDiameter + circleSpacing) * col) + radius

                canvas?.drawCircle(cx, cy, radius, paint)
            }
        }
    }

    private fun recalculateDimensions(w: Int = width, h: Int = height) {
        val diameterX = w/(colCount + (colCount+1)*circleSpacingRatio)
        val diameterY = h/(rowCount + (rowCount+1)*circleSpacingRatio)
        circleDiameter = minOf(diameterX, diameterY)
        circleSpacing = circleDiameter*circleSpacingRatio

        val gridWidth = colCount * (circleDiameter+circleSpacing) + circleSpacing
        val gridHeight = rowCount * (circleDiameter+circleSpacing) + circleSpacing

        gridLeft = (w - gridWidth)/2
        gridRight = gridLeft + gridWidth
        gridTop = (h - gridHeight)/2
        gridBottom = gridTop + gridHeight
    }
}