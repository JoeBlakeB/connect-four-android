package com.joeblakeb.connectfour

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.joeblakeb.lib.ConnectFourGameInterface
import com.joeblakeb.logic.ConnectFourGameLogic

class GridView : View {

    private var gameLogic: ConnectFourGameLogic = ConnectFourGameLogic()
        set(value) {
            field = value
            recalculateDimensions()
            invalidate()
        }

    private val gameChangeListener: ConnectFourGameInterface.GameChangeListener = ConnectFourGameInterface.GameChangeListener {
        postInvalidate()
    }

    private val colCount:Int get() = gameLogic.columns
    private val rowCount:Int get() = gameLogic.rows

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

    private val playerOnePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0xFFF21B3F.toInt()
    }

    private val playerTwoPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0xFFEDE72C.toInt()
    }

    private val noPlayerPaint: Paint= Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    init {
        gameLogic.addGameChangeListener(gameChangeListener)
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
            val cy = gridTop + circleSpacing + ((circleDiameter + circleSpacing) * (rowCount - row - 1)) + radius

            for (col in 0 until colCount) {
                val paint = when (gameLogic.getToken(col, row)) {
                    1 -> playerOnePaint
                    2 -> playerTwoPaint
                    else -> noPlayerPaint
                }

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

    private val gestureDetector = GestureDetectorCompat(context, object:
        GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean = true

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val columnTouched = ((e.x - gridLeft - circleSpacing * 0.5f) / (circleSpacing + circleDiameter)).toInt()

            return if (columnTouched in 0 until gameLogic.columns) {
                gameLogic.playToken(columnTouched)
                invalidate()
                true
            } else {
                false
            }
        }
    })

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }
}