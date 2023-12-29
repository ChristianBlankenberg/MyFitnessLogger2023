package com.example.myfitnesslogger.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PieView(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {
    private var elapsedAngle: Float = 0.0f

    var ElapsedAngle: Float
        get() = elapsedAngle
        set(value: Float) {
            elapsedAngle  = value
            this.invalidate()
        }

    private var paint: Paint? = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawBackground(canvas)
        drawCompleteTime(canvas)
        drawElapsedTime(canvas)
        drawOverTime(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // requested width and mode
        val reqWidth = MeasureSpec.getSize(widthMeasureSpec)
        val reqWidthMode = MeasureSpec.getMode(widthMeasureSpec)

        // requested height and mode
        val reqHeight = MeasureSpec.getSize(heightMeasureSpec)
        val reqHeightMode = MeasureSpec.getMode(heightMeasureSpec)

        // your choice
        val desiredWidth: Int = 400

        val width = when (reqWidthMode) {
            MeasureSpec.EXACTLY -> reqWidth
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> Math.min(reqWidth, desiredWidth) // AT_MOST condition
        }

        val desiredHeight: Int = width

        val height = when (reqHeightMode) {
            MeasureSpec.EXACTLY -> reqHeight
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> Math.min(reqHeight, desiredHeight) // AT_MOST condition
        }

        // set the width and the height of the view
        setMeasuredDimension(width, height)
    }

    private fun drawOverTime(canvas: Canvas) {
        if (ElapsedAngle > 360.0f)
        {
            val x = width / 2.0
            val y = height / 2.0
            val radius = width / 2 * 0.875

            paint!!.style = Paint.Style.FILL
            paint!!.color = Color.WHITE
            canvas.drawArc((x - radius).toFloat(), (y - radius).toFloat(), (x + radius).toFloat(), (y + radius).toFloat(), -90.0f, (ElapsedAngle - 360.0f), true, paint!!)
        }
    }

    private fun drawElapsedTime(canvas: Canvas) {
        val x = width / 2.0
        val y = height / 2.0
        val radius = width / 2 * 0.80

        paint!!.style = Paint.Style.FILL
        paint!!.color = Color.GRAY
        canvas.drawArc((x - radius).toFloat(), (y - radius).toFloat(), (x + radius).toFloat(), (y + radius).toFloat(), -90.0f, ElapsedAngle, true, paint!!)
    }

    private fun drawCompleteTime(canvas: Canvas) {
        val x = width
        val y = height
        val radius = width / 2 * 0.75

        paint!!.style = Paint.Style.FILL
        paint!!.color = Color.DKGRAY
        canvas.drawCircle((x / 2).toFloat(), (y / 2).toFloat(), radius.toFloat(), paint!!)
    }

    private fun drawBackground(canvas: Canvas) {
        val x = width
        val y = height

        paint!!.style = Paint.Style.FILL
        paint!!.color = Color.WHITE
        canvas.drawPaint(paint!!)
    }


}