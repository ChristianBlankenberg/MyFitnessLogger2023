package com.example.myfitnesslogger.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random

class StepsPieView(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet) {
    private var percents: List<Float> = arrayListOf<Float>()
    private var paint: Paint? = Paint()
    private var colors : MutableList<Int> = mutableListOf<Int>()

    var Percents: List<Float>
        get() = percents
        set(value: List<Float>) {
            percents = value
            this.invalidate()
        }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawAngles(canvas)
    }

    private fun drawAngles(canvas: Canvas) {
        var currentAngle = 0.0f

        for(index in 0..percents.count()-1) {
            var angle = percents[index] * 3.6f

            this.drawPie(canvas, currentAngle, angle, getColor(index), index.toString())
            currentAngle += angle
        }
    }

    private fun getColor(index: Int): Int {
        while (index >= colors.count())
        {
            colors.add(Random.nextInt())
        }

        return colors[index]
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

    private fun drawPie(canvas: Canvas, startAngle : Float, angle : Float, fillColor : Int, value : String) {
        val x = width / 2.0
        val y = height / 2.0
        val radius = width / 2 * 1.0

        paint!!.style = Paint.Style.FILL
        paint!!.color = fillColor
        canvas.drawArc(
            (x - radius).toFloat(),
            (y - radius).toFloat(),
            (x + radius).toFloat(),
            (y + radius).toFloat(),
            -90 + startAngle,
            angle,
            true,
            paint!!
        )

        if (angle > 17) {
            val textAngle = -90 + startAngle + angle / 2.0
            val textX = Math.cos(textAngle / 180.0 * Math.PI) * radius
            val textY = Math.sin(textAngle / 180.0 * Math.PI) * radius
            paint!!.setTextSize(30f)

            paint!!.color = Color.WHITE
            canvas.drawText(value, (x + textX/ 2.0).toFloat(), (y + textY / 2.0).toFloat(), paint!!)

            paint!!.color = Color.BLACK
            canvas.drawText(value, (x + textX/ 1.5).toFloat(), (y + textY / 1.5).toFloat(), paint!!)
        }
    }
}