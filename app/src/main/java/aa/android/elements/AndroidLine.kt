package aa.android.elements

import aa.engine.helpers.Position
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class AndroidLine {
    private var paint = Paint()

    init {
        paint.setColor(Color.CYAN);
        paint.style = Paint.Style.FILL;
    }

    public fun setColor(paint: Paint) {
        this.paint = paint;
    }

    fun draw(canvas: Canvas, p1: Position, p2: Position) {
        canvas.drawLine(
            p1.getX(),
            p1.getY(),
            p2.getX(),
            p2.getY(),
            paint
        )
    }

}