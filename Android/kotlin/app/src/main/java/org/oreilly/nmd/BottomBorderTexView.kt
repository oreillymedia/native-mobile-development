package org.oreilly.nmd

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

class BottomBorderTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : TextView(context, attrs, defStyleAttr, defStyleRes) {

    private val paint = Paint()

    init {
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        // note we are _not_ calling super.onDraw here
        canvas.drawOval(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

}