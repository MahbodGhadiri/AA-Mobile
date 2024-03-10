package aa.android.elements

import aa.engine.elements.SmallBall
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class AndroidSmallBall() : SmallBall() {

    private var left: Float = 0F;
    private var top: Float = 0F;
    private var right: Float = 0F;
    private var bottom: Float = 0F;
    private val rectF: RectF = RectF(left, top, right, bottom);
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    init {
        this.calculateNewRectF();
    }

    public fun calculateNewRectF() {
        this.left =
            (this.getPosition().getX() - this.getRadius());
        this.top =
            (this.getPosition().getY() - this.getRadius());
        this.right =
            (this.getPosition().getX() + this.getRadius());
        this.bottom =
            (this.getPosition().getY() + this.getRadius());

        rectF.top = top;
        rectF.left = left;
        rectF.right = right;
        rectF.bottom = bottom;
    }

    public fun getRectF(): RectF {
        return this.rectF;
    }

    public fun getPaint(): Paint {
        return this.paint;
    }

    public fun draw(canvas: Canvas) {
        this.calculateNewRectF();
        canvas.drawOval(rectF, paint);
    }
}