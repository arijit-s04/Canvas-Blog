package com.example.arijit.canvasblog.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

private const val PENCIL_STROKE_WIDTH = 10f
private const val ERASER_STROKE_WIDTH = 60f

class Board @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mPath: Path? = null
    private val mDrawnPaths = mutableListOf<Brush>()
    private val mPaint: Paint = Paint()
    private var mX = 0f
    private var mY = 0f

    fun setPen() {
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = PENCIL_STROKE_WIDTH
    }

    fun setEraser() {
        mPaint.color = Color.WHITE
        mPaint.strokeWidth = ERASER_STROKE_WIDTH
    }

    private fun initialize() {
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND

        setPen()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initialize()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            it.save()
            drawPathsOnCanvas(it)
            it.restore()
        }
    }

    private fun drawPathsOnCanvas(canvas: Canvas) {
        for(brush in mDrawnPaths) {
            canvas.drawPath(brush.path, brush.paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event == null) return false
        val x = event.x
        val y = event.y

        when (event.action){
            MotionEvent.ACTION_DOWN -> touchDown(x, y)
            MotionEvent.ACTION_MOVE -> touchMove(x, y)
            MotionEvent.ACTION_UP -> touchUp(x, y)
        }
        invalidate()
        return true
    }

    private fun touchDown(x: Float, y: Float) {
        mPath = Path()
        mDrawnPaths.add(Brush(mPath!!, Paint(mPaint)))
        mPath?.apply {
            reset()
            moveTo(x, y)
        }
        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        mPath?.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
        mX = x
        mY = y
    }

    private fun touchUp(x: Float, y: Float) {
        mPath?.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
        mPath = null
    }
}

class Brush(val path: Path, val paint: Paint) {}